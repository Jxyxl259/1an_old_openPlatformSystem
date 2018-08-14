package com.yaic.app.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.auth.dto.ResponseMessage;
import com.yaic.app.auth.dto.domain.OauthServiceLogDto;
import com.yaic.app.auth.dto.domain.OauthTokenDto;
import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.app.auth.dto.domain.OauthUserDto;
import com.yaic.app.auth.service.OauthServiceLogService;
import com.yaic.app.auth.service.OauthTokenService;
import com.yaic.app.auth.service.OauthTransResService;
import com.yaic.app.auth.service.OauthUserService;
import com.yaic.app.auth.util.RSACoder;
import com.yaic.app.auth.util.WildcardMatcher;
import com.yaic.fa.spring.SpringUtils;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.digest.MD5Helper;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpPostKVpairWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.util.CollectionUtil;
import com.yaic.servicelayer.util.ConfigUtil;
import com.yaic.servicelayer.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import net.sf.json.xml.XMLSerializer;


@Component
public class PlatformFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(PlatformFilter.class);
    
    /**内部应用访问URL**/
    String[] passUrl = {"/login**","/common/**","/css/**","/js/**","/fileUpload/**",
            "/","/index","/unauthorized","/company/**","/config/**","/mapping/**","/parameter/**",
            "/parameterType/**","/resource/**","/role/**","/user/**","/userRole/**","/oauth2/**",
            "/oauthResource/**","/auth/**","/logout**","/transRes/**","/job/**","/callback/**","/notice/**","/callthird/**"}; 
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /**
         * 处理过程:
         * 1、判断URL是否为内部应用访问的URL,是直接跳过拦截;
         * 2、判断请求资源是否存在配置,否返回错误信息;
         * 3、判断请求是否需要鉴权;
         *      否:分'带参数情况'与'带参数情况'两种情况;
         * 4、AppId、AppSecret、Sign签名验证;
         * 5、常规鉴权;
         */
        String[] urls = this.generateAllPattenURL((HttpServletRequest) request);

        logger.info("Method:{};CharacterEncoding:{};ContentType:{}",((HttpServletRequest)request).getMethod(), request.getCharacterEncoding(), request.getContentType());
        
        // 1、判断URL是否为内部应用访问的URL,是直接跳过拦截;
        boolean needCheck = true;
        for (String res : passUrl) {
            if (WildcardMatcher.match(urls[0], res, true)) {
                needCheck = false;
                break;
            }
        }
        if (!needCheck) {
            chain.doFilter(request, response);
            return;
        }
        
        // 2、判断请求资源是否存在配置,否返回错误信息;
        String accept = ((HttpServletRequest) request).getHeader("Accept"); // application/json || application/xml
        String urlPart = urls[0].substring(1, urls[0].length());
        OauthTransResService oauthTransResService = SpringUtils.getBean(OauthTransResService.class);
        OauthTransResDto oauthTransResDto = new OauthTransResDto();
        oauthTransResDto.setRequestUrl(urlPart);
        oauthTransResDto.setInvalidFlag(0);
        oauthTransResDto = oauthTransResService.selectOne(oauthTransResDto);
        
        if(oauthTransResDto == null) {
        	OauthTransResDto oauthTransResDto2 = new OauthTransResDto();
         	oauthTransResDto2.setInvalidFlag(0);
         	oauthTransResDto2.setFuzzy(1);
         	List<OauthTransResDto> list = oauthTransResService.select(oauthTransResDto2);
         	OauthTransResDto fuzzyOauthTransResDto = getFuzzyUrl(list,urlPart);
            if (fuzzyOauthTransResDto != null) {
				oauthTransResDto = fuzzyOauthTransResDto;
			} else {
			    logger.error("not exist this resource config info,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.CODE_50001);
			    this.formateErrorMsg(accept, Constants.CODE_50001, Constants.MSG_50001, response);
			    return;
			}
        }
             
        // 3、判断请求是否需要鉴权;
        if (new Integer(0).equals(oauthTransResDto.getIsAuth())) {
            // 带参数情况
            if (new Integer(1).equals(oauthTransResDto.getIsPair())) {
                this.isPairProcess(request, response, oauthTransResDto, urls);
                return;
            } else { // 不带参数情况
                this.nonPairProcess(request, response, oauthTransResDto, urls);
                return;
            }
        }
        
        // 4、AppId、AppSecret、Sign签名验证;
        if ("1".equals(oauthTransResDto.getIsEncrypt())) {
        	if("RSA".equals(oauthTransResDto.getEncryptType().toUpperCase())) {
        		this.encryptProcessForRSA(request, response, oauthTransResDto, urls, accept);
        	}else {
        		this.encryptProcess(request, response, oauthTransResDto, urls, accept);
        	}
            return;
        }
        
        // 5、常规鉴权;
        this.commonOauthProcess(request, response, oauthTransResDto, urls, accept);
        return;
    }
    
    /**
     * 常规鉴权
     * @param request
     * @param response
     * @param oauthTransResDto
     * @param urls
     * @param accept
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void commonOauthProcess(ServletRequest request, ServletResponse response, OauthTransResDto oauthTransResDto, String[] urls, String accept) throws IOException {
        
        String accessToken = ((HttpServletRequest) request).getParameter("access_token");
        String openId = ((HttpServletRequest) request).getParameter("open_id");
        
        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(accessToken)) {
            checkMsg.append("access_token,");
        }
        if (StringUtil.isEmpty(openId)) {
            checkMsg.append("open_id,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.CODE_40006);
            this.formateErrorMsg(accept, Constants.CODE_40006, checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty", response);
            return;
        }

        OauthUserService oauthUserService = SpringUtils.getBean(OauthUserService.class);
        OauthTokenService oauthTokenService = SpringUtils.getBean(OauthTokenService.class);

        OauthTokenDto oauthTokenDto = new OauthTokenDto();
        oauthTokenDto.setToken(accessToken);
        oauthTokenDto.setOpenId(openId);
        oauthTokenDto = oauthTokenService.selectOne(oauthTokenDto);
        if (oauthTokenDto == null) {
            logger.error("invalid access_token or invalid open_id,accessToken:{},openId:{},accept:{}", accessToken, openId, accept);
            this.formateErrorMsg(accept, Constants.CODE_40032, Constants.MSG_40032, response);
            return;
        }

        if (!openId.equals(oauthTokenDto.getOpenId())) {
            logger.error("invalid openId,accessToken:{},openId:{},accept:{},errorCode:{}", accessToken, openId, accept, Constants.CODE_40031);
            this.formateErrorMsg(accept, Constants.CODE_40031, Constants.MSG_40031, response);
            return;
        }

        Date now = new Date();
        if (oauthTokenDto.getExpireTime().before(now)) {
            if(oauthTokenDto.getRefExpireTime().before(now)) {
                logger.error("refresh_token expired,accessToken:{},refresh_token:{},openId:{},accept:{},errorCode:{}", accessToken, oauthTokenDto.getRefreshToken(), openId, accept, Constants.CODE_40005);
                this.formateErrorMsg(accept, Constants.CODE_40005, Constants.MSG_40005, response);
                return;
            }else {
                logger.error("access_token expired,accessToken:{},openId:{},accept:{},errorCode:{}", accessToken, openId, accept, Constants.CODE_40029);
                this.formateErrorMsg(accept, Constants.CODE_40029, Constants.MSG_40029, response);
                return;
            }
        }

        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(oauthTokenDto.getAppId());
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null || oauthUserDto.getInvalidFlag() != 0) {
            logger.error("not find user form access_token,accessToken:{},openId:{},accept:{},errorCode:{}", accessToken, openId, accept, Constants.CODE_40030);
            this.formateErrorMsg(accept, Constants.CODE_40030, Constants.MSG_40030, response);
            return;
        }
        
        OauthTransResDto oauthTransRes = new OauthTransResDto();
        oauthTransRes.setAppId(oauthTokenDto.getAppId());
        oauthTransRes.setInvalidFlag(0);
        List<OauthTransResDto> oauthTransResList = SpringUtils.getBean(OauthTransResService.class).findResourceInfo(oauthTransRes);

        boolean hasPermission = false;

        if(CollectionUtil.isNotEmpty(oauthTransResList)) {
            for (OauthTransResDto transResDto : oauthTransResList) {
                if (transResDto.getRequestUrl().equals(urls[0].substring(1, urls[0].length()))) {
                    hasPermission = true;
                    break;
                }
            }
        }

        if (!hasPermission) {
        	OauthTransResDto oauthTransResDto2 = new OauthTransResDto();
         	oauthTransResDto2.setInvalidFlag(0);
         	oauthTransResDto2.setFuzzy(1);
         	List<OauthTransResDto> list = SpringUtils.getBean(OauthTransResService.class).select(oauthTransResDto2);
         	OauthTransResDto fuzzyOauthTransResDto = getFuzzyUrl(list,urls[0].substring(1, urls[0].length()));
            if (fuzzyOauthTransResDto == null) {
	            logger.error("no permission access this resource,accessToken:{},openId:{},accept:{},errorCode:{}", accessToken, openId, accept, Constants.CODE_50002);
	            this.formateErrorMsg(accept, Constants.CODE_50002, Constants.MSG_50002, response);
	            return;
			}
        }
        
        String encodingType = oauthTransResDto.getEncodingType();
        if(StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }
        
        request.setCharacterEncoding(encodingType);
        List ioList = IOUtils.readLines(request.getInputStream(),encodingType);
        StringBuilder sb = new StringBuilder(1024);
        for (int i = 0; i < ioList.size(); i++) {
            sb.append(ioList.get(i));
        }
        String content = sb.toString();
        String originalContent = content;
        logger.info("------Receive IP:"+ this.getIpAddress((HttpServletRequest) request)+" openId:{}  Interface_info:{}", openId , content);
        if (accept != null && !"*/*".equals(accept) 
                && !"application/json".equals(accept)
                && !"application/xml".equals(accept)) {
            throw new RuntimeException("not support this accept");
        }
        
        String result = "";
        try {
            StringBuffer transUrl = new StringBuffer();
            
            transUrl.append(oauthTransResDto.getTransUrl())
                    .append("?")
                    .append("userId=").append(oauthTransResDto.getUserId())
                    .append("&")
                    .append("orgId=").append(oauthTransResDto.getOrgId())
                    .append("&")
                    .append("agrtCode=").append(oauthTransResDto.getAgrtCode())
                    .append("&")
                    .append("appId=").append(oauthUserDto.getAppId());
            HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
    		httpPostWrapper.setServerUrl(transUrl.toString());
    		httpPostWrapper.setMimeType("text/plain");
    		httpPostWrapper.setRawBody(content);
    		httpPostWrapper.setCharset(encodingType);
            HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
            if (httpResponseWrapper.getStatus()) {
            	result = (String) httpResponseWrapper.getContent();
                if (accept != null && !"*/*".equals(accept) 
                        && !"application/json".equals(accept)
                        && !"application/xml".equals(accept)) {
                    throw new RuntimeException("not support this accept");
                }
                
            } else {
                logger.error("ErrorMessage:{}", httpResponseWrapper.getErrorMessage());
                this.formateErrorMsg(accept, Constants.CODE_FAIL, httpResponseWrapper.getErrorMessage(), response);
                return;
            }
        } catch (IllegalStateException e) {
            try {
                result = "The request is not responding. Please try again later, error:" + e;
                logger.error("The request is not responding. Please try again later, error:{}", e);
                this.formateErrorMsg(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response);
                return;
            } catch (Exception ex) {
                throw new IOException(ex);
            }
        } catch (Exception e) {
            try {
                result = "Request platform connection error, please contact the relevant personnel, error:" + e;
                logger.error("Request platform connection error, please contact the relevant personnel, error:{}", e);
                this.formateErrorMsg(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response);
                return;
            } catch (Exception ex) {
                throw new IOException(ex);
            }
        } finally {
            logger.info("------Return Interface info:{}", result);
            this.dbLog(oauthTokenDto.getAppId(),oauthTokenDto.getToken(), oauthTransResDto.getId(), originalContent, result);
        }
        
        response.setCharacterEncoding(encodingType);
        response.setContentType("text/html; charset=" + encodingType);
        response.getWriter().write(result);
        response.getWriter().flush();
        return;
    }
    
    /**
     * 透传不带参数过程
     * @param request
     * @param response
     * @param oauthTransResDto
     * @param urls
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void nonPairProcess(ServletRequest request, ServletResponse response, OauthTransResDto oauthTransResDto, String[] urls) throws IOException {
        
        String acceptCfg = oauthTransResDto.getAccept();
        if(StringUtil.isEmpty(acceptCfg)) {
            acceptCfg = "text/html";
        }  
        
        String encodingType = oauthTransResDto.getEncodingType();
        if (StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }
        
        String ipAddress = this.getIpAddress((HttpServletRequest) request);
        if(StringUtil.isNotEmpty(oauthTransResDto.getAllowIp())) {
            if(!this.validateIP(ipAddress, oauthTransResDto.getAllowIp()) && !Constants.PUBLIC_ALLOW_IP.equals(oauthTransResDto.getAllowIp().trim())) {
                logger.error("This IP is not allowed to access,Ip:{},requestUrl:{},accept:{},errorCode:{}", ipAddress, urls[0], acceptCfg, Constants.CODE_50003);
                this.formateErrorMsg(acceptCfg, Constants.CODE_50003, Constants.MSG_50003, response);
                return;
            }
        }else {
            logger.error("This IP is not allowed to access,Ip:{},requestUrl:{},accept:{},errorCode:{}", ipAddress, urls[0], acceptCfg, Constants.CODE_50003);
            this.formateErrorMsg(acceptCfg, Constants.CODE_50003, Constants.MSG_50003, response);
            return;
        }
        
        request.setCharacterEncoding(encodingType);
        List ioList = IOUtils.readLines(request.getInputStream(), encodingType);
        StringBuilder sb = new StringBuilder(1024);
        for (int i = 0; i < ioList.size(); i++) {
            sb.append(ioList.get(i));
        }
        String content = sb.toString();
        
        logger.info("------Receive IP:"+ ipAddress +" Interface info:{}", content);

        String result = null;
        try {
            StringBuffer transUrl = new StringBuffer(oauthTransResDto.getTransUrl());
            String transUrlNew = transUrl.toString();
            Enumeration paramNameEnum = ((HttpServletRequest) request).getParameterNames();
            if(paramNameEnum.hasMoreElements()) {
                transUrl.append("?");
                while (paramNameEnum.hasMoreElements()) {
                    String paramName = (String) paramNameEnum.nextElement();
                    String paramValue = request.getParameter(paramName);
                    logger.info(paramName + "=" + paramValue);
                    transUrl.append(paramName).append("=").append(paramValue).append("&");
                }
                transUrlNew = transUrl.substring(0, transUrl.length()-1);
            }
            HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
    		httpPostWrapper.setServerUrl(transUrlNew);
    		httpPostWrapper.setMimeType("text/plain");
    		httpPostWrapper.setRawBody(content);
    		httpPostWrapper.setCharset(encodingType);
            HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
            if (httpResponseWrapper.getStatus()) {
            	result = (String) httpResponseWrapper.getContent();
            }else {
            	result = "system exception";
            	logger.error("ErrorMessage:{}", httpResponseWrapper.getErrorMessage());
            }
            response.setCharacterEncoding(encodingType);
            response.setContentType(acceptCfg + "; charset=" + encodingType);
            response.getWriter().write(result);
            response.getWriter().flush();
            return;
        } catch (Exception e) {
            result = "Request connection error:" + e;
            logger.error("Request connection error:{}", e);
            throw new IOException(e);
        } finally {
            logger.info("------Return Interface info:{}", result);
            this.dbLog("NONE", "NONE", oauthTransResDto.getId(), content, result);
        }
    }
    
    /**
     * 透传带参数过程
     * @param request
     * @param response
     * @param oauthTransResDto
     * @param urls
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void isPairProcess(ServletRequest request, ServletResponse response, OauthTransResDto oauthTransResDto, String[] urls) throws IOException {
        
        String acceptCfg = oauthTransResDto.getAccept();
        if(StringUtil.isEmpty(acceptCfg)) {
            acceptCfg = "text/html";
        }  
        
        String encodingType = oauthTransResDto.getEncodingType();
        if (StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }
        request.setCharacterEncoding(encodingType);
        
        Enumeration paramNameEnum = ((HttpServletRequest) request).getParameterNames();
        Map<String, String> kvPairs = new HashMap<String, String>();
        String ipAddress = this.getIpAddress((HttpServletRequest) request);
        if(StringUtil.isNotEmpty(oauthTransResDto.getAllowIp())) {
            if(!this.validateIP(ipAddress, oauthTransResDto.getAllowIp()) && !Constants.PUBLIC_ALLOW_IP.equals(oauthTransResDto.getAllowIp().trim())) {
                logger.error("This IP is not allowed to access,Ip:{},requestUrl:{},accept:{},errorCode:{}", ipAddress, urls[0], acceptCfg, Constants.CODE_50003);
                this.formateErrorMsg(acceptCfg, Constants.CODE_50003, Constants.MSG_50003, response);
                return;
            }
        }else {
            logger.error("This IP is not allowed to access,Ip:{},requestUrl:{},accept:{},errorCode:{}", ipAddress, urls[0], acceptCfg, Constants.CODE_50003);
            this.formateErrorMsg(acceptCfg, Constants.CODE_50003, Constants.MSG_50003, response);
            return;
        }
        
        logger.info("------Receive IP:{} Interface info:", ipAddress);
        
        StringBuffer content = new StringBuffer();
        while (paramNameEnum.hasMoreElements()) {
            String paramName = (String) paramNameEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            kvPairs.put(paramName, paramValue);
            logger.info("{}={}", paramName, paramValue);
            content.append(paramName).append("=").append(paramValue).append(";");
        }
        String result = null;
        try {
        	HttpPostKVpairWrapper httpPostWrapper = new HttpPostKVpairWrapper();
        	httpPostWrapper.setServerUrl(oauthTransResDto.getTransUrl());
        	httpPostWrapper.setKvpairs(kvPairs);
        	httpPostWrapper.setCharset(encodingType);
            HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
            if (httpResponseWrapper.getStatus()) {
            	result = (String) httpResponseWrapper.getContent();
            }else {
            	result = "system exception";
            	logger.error("ErrorMessage:{}", httpResponseWrapper.getErrorMessage());
            }
            response.setCharacterEncoding(encodingType);
            response.setContentType(acceptCfg + "; charset=" + encodingType);
            response.getWriter().write(result);
            response.getWriter().flush();
            return;
        } catch (Exception e) {
            result = "Request connection error:" + e;
            logger.error("Request connection error:{}", e);
            throw new IOException(e);
        } finally {
            logger.info("------Return Interface info:{}", result);
            this.dbLog("NONE", "NONE", oauthTransResDto.getId(), content.toString(), result);
        }
    }
    
   
    /**
     * AppId、AppSecret、Sign签名验证处理过程
     * @param request
     * @param response
     * @param oauthTransResDto
     * @param urls
     * @param accept
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void encryptProcess(ServletRequest request, ServletResponse response, OauthTransResDto oauthTransResDto, String[] urls, String accept) throws IOException {
        
        if (accept != null && !"*/*".equals(accept) 
                && !"application/json".equals(accept)
                && !"application/xml".equals(accept)) {
            throw new RuntimeException("not support this accept");
        }
        
        String encodingType = oauthTransResDto.getEncodingType();
        if (StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }
        request.setCharacterEncoding(encodingType);

        String appId = ((HttpServletRequest) request).getParameter("app_id");
        String appSecret = ((HttpServletRequest) request).getParameter("app_secret");
        String sign = ((HttpServletRequest) request).getParameter("sign");
        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(appId)) {
            checkMsg.append("app_id,");
        }
        if (StringUtil.isEmpty(appSecret)) {
            checkMsg.append("app_secret,");
        }
        if (StringUtil.isEmpty(sign)) {
            checkMsg.append("sign,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.CODE_40006);
            this.formateErrorMsg(accept, Constants.CODE_40006, checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty", response);
            return;
        }

        OauthUserService oauthUserService = SpringUtils.getBean(OauthUserService.class);
        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(appId);
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null) {
            logger.error("not exist this app_id,appId:{},accept:{}", appId, accept);
            this.formateErrorMsg(accept, Constants.CODE_50004, Constants.MSG_50004, response);
            return;
        }

        if (!appSecret.equals(oauthUserDto.getAppSecret())) {
            logger.error("This appSecret is not correct,appId:{},appSecret:{},accept:{}", appId, appSecret, accept);
            this.formateErrorMsg(accept, Constants.CODE_50005, Constants.MSG_50005, response);
            return;
        }

        OauthTransResDto oauthTransRes = new OauthTransResDto();
        oauthTransRes.setAppId(appId);
        oauthTransRes.setInvalidFlag(0);
        List<OauthTransResDto> oauthTransResList = SpringUtils.getBean(OauthTransResService.class).findResourceInfo(oauthTransRes);

        boolean hasPermission = false;

        if (CollectionUtil.isNotEmpty(oauthTransResList)) {
            for (OauthTransResDto transResDto : oauthTransResList) {
                if (transResDto.getRequestUrl().equals(urls[0].substring(1, urls[0].length()))) {
                    hasPermission = true;
                    break;
                }
            }
        }

        if (!hasPermission) {
        	OauthTransResDto oauthTransResDto2 = new OauthTransResDto();
         	oauthTransResDto2.setInvalidFlag(0);
         	oauthTransResDto2.setFuzzy(1);
         	List<OauthTransResDto> list = SpringUtils.getBean(OauthTransResService.class).select(oauthTransResDto2);
         	OauthTransResDto fuzzyOauthTransResDto = getFuzzyUrl(list,urls[0].substring(1, urls[0].length()));
            if (fuzzyOauthTransResDto == null) {
            	logger.error("no permission access this resource,appId:{},appSecret:{},accept:{},errorCode:{}", appId, appSecret, accept, Constants.CODE_50002);
                this.formateErrorMsg(accept, Constants.CODE_50002, Constants.MSG_50002, response);
                return;
			}
        }

        List ioList = IOUtils.readLines(request.getInputStream(), encodingType);
        StringBuilder sb = new StringBuilder(1024);
        for (int i = 0; i < ioList.size(); i++) {
            sb.append(ioList.get(i));
        }
        String content = sb.toString();
        logger.info("request param: appId:{},requestUrl:{},accept:{},content:{},sign:{}", appId ,urls[0], accept, content,sign);
        if (!sign.equals(this.generateSign(oauthTransResDto, content, encodingType,sign))) {
            logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.MSG_40008);
            this.formateErrorMsg(accept, Constants.CODE_40008, Constants.MSG_40008, response);
            return;
        } else {
            String result = "";
            try {
                StringBuffer transUrl = new StringBuffer();
                
                transUrl.append(oauthTransResDto.getTransUrl())
                        .append("?")
                        .append("userId=").append(oauthTransResDto.getUserId())
                        .append("&")
                        .append("orgId=").append(oauthTransResDto.getOrgId())
                        .append("&")
                        .append("agrtCode=").append(oauthTransResDto.getAgrtCode())
                        .append("&")
                        .append("appId=").append(oauthUserDto.getAppId());
                HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
                httpPostWrapper.setServerUrl(transUrl.toString());
                httpPostWrapper.setRawBody(content);
                httpPostWrapper.setMimeType("text/plain");
                httpPostWrapper.setCharset(encodingType);
                HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
                if (httpResponseWrapper.getStatus()) {
                	result = (String) httpResponseWrapper.getContent();
                    response.setCharacterEncoding(encodingType);
                    response.setContentType("text/html; charset=" + encodingType);
                    response.getWriter().write(result);
                    response.getWriter().flush();
                    return;
                } else {
                    logger.error("ErrorMessage:{}", httpResponseWrapper.getErrorMessage());
                    this.formateErrorMsg(accept, Constants.CODE_FAIL, httpResponseWrapper.getErrorMessage(), response);
                    return;
                }
            } catch (IllegalStateException e) {
                try {
                    result = "The request is not responding. Please try again later, error:" + e;
                    logger.error("The request is not responding. Please try again later, error:{}", e);
                    this.formateErrorMsg(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response);
                    return;
                } catch (Exception ex) {
                    throw new IOException(ex);
                }
            } catch (Exception e) {
                try {
                    result = "Request platform connection error, please contact the relevant personnel, error:" + e;
                    logger.error("Request platform connection error, please contact the relevant personnel, error:{}", e);
                    this.formateErrorMsg(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response);
                    return;
                } catch (Exception ex) {
                    throw new IOException(ex);
                }
            } finally {
                logger.info("------Return Interface info:{}", result);
                this.dbLog(appId, "NONE", oauthTransResDto.getId(), content, result);
            }
        }
    }
    
    
    /**
     * AppId、AppSecret、Sign签名验证处理过程
     * @param request
     * @param response
     * @param oauthTransResDto
     * @param urls
     * @param accept
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void encryptProcessForRSA(ServletRequest request, ServletResponse response, OauthTransResDto oauthTransResDto, String[] urls, String accept) throws IOException {
        
        if (accept != null && !"*/*".equals(accept) 
                && !"application/json".equals(accept)
                && !"application/xml".equals(accept)) {
            throw new RuntimeException("not support this accept");
        }
        String encryptKey = oauthTransResDto.getEncryptKey().trim();
        String encodingType = oauthTransResDto.getEncodingType();
        if (StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }
        request.setCharacterEncoding(encodingType);

        String appId = ((HttpServletRequest) request).getParameter("app_id");
        String appSecret = ((HttpServletRequest) request).getParameter("app_secret");
        String sign = ((HttpServletRequest) request).getParameter("sign");
        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(appId)) {
            checkMsg.append("app_id,");
        }
        if (StringUtil.isEmpty(appSecret)) {
            checkMsg.append("app_secret,");
        }
        if (StringUtil.isEmpty(sign)) {
            checkMsg.append("sign,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.CODE_40006);
            this.formateErrorMsgForRSA(accept, Constants.CODE_40006, checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty", response,encryptKey);
            return;
        }

        OauthUserService oauthUserService = SpringUtils.getBean(OauthUserService.class);
        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(appId);
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null) {
            logger.error("not exist this app_id,appId:{},accept:{}", appId, accept);
            this.formateErrorMsgForRSA(accept, Constants.CODE_50004, Constants.MSG_50004, response,encryptKey);
            return;
        }

        if (!appSecret.equals(oauthUserDto.getAppSecret())) {
            logger.error("This appSecret is not correct,appId:{},appSecret:{},accept:{}", appId, appSecret, accept);
            this.formateErrorMsgForRSA(accept, Constants.CODE_50005, Constants.MSG_50005, response,encryptKey);
            return;
        }

        OauthTransResDto oauthTransRes = new OauthTransResDto();
        oauthTransRes.setAppId(appId);
        oauthTransRes.setInvalidFlag(0);
        List<OauthTransResDto> oauthTransResList = SpringUtils.getBean(OauthTransResService.class).findResourceInfo(oauthTransRes);

        boolean hasPermission = false;

        if (CollectionUtil.isNotEmpty(oauthTransResList)) {
            for (OauthTransResDto transResDto : oauthTransResList) {
                if (transResDto.getRequestUrl().equals(urls[0].substring(1, urls[0].length()))) {
                    hasPermission = true;
                    break;
                }
            }
        }

        if (!hasPermission) {
        	OauthTransResDto oauthTransResDto2 = new OauthTransResDto();
         	oauthTransResDto2.setInvalidFlag(0);
         	oauthTransResDto2.setFuzzy(1);
         	List<OauthTransResDto> list = SpringUtils.getBean(OauthTransResService.class).select(oauthTransResDto2);
         	OauthTransResDto fuzzyOauthTransResDto = getFuzzyUrl(list,urls[0].substring(1, urls[0].length()));
            if (fuzzyOauthTransResDto == null) {
            	logger.error("no permission access this resource,appId:{},appSecret:{},accept:{},errorCode:{}", appId, appSecret, accept, Constants.CODE_50002);
                this.formateErrorMsgForRSA(accept, Constants.CODE_50002, Constants.MSG_50002, response,encryptKey);
                return;
			}
        }

        List ioList = IOUtils.readLines(request.getInputStream(), encodingType);
        StringBuilder sb = new StringBuilder(1024);
        for (int i = 0; i < ioList.size(); i++) {
            sb.append(ioList.get(i));
        }
        String content = sb.toString();
        logger.info("request param: appId:{},requestUrl:{},accept:{},content:{},sign:{}", appId ,urls[0], accept, content,sign);
        if (!sign.equals(this.generateSign(oauthTransResDto, content, encodingType, sign))) {
            logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.MSG_40008);
            this.formateErrorMsgForRSA(accept, Constants.CODE_40008, Constants.MSG_40008, response,encryptKey);
            return;
        } else {//签名通过之后需要对RAS的密文内容使用公钥解密
        	logger.info("sign is success.........................");
        	if("RSA".equals(oauthTransResDto.getEncryptType())) {
        		try {
        			content = RSACoder.decryptByPublicKey(content,oauthTransResDto.getEncryptKey());
        			logger.info("decryptByPublicKe:{}",content);
				} catch (Exception e) {
					logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.MSG_40009);
		            this.formateErrorMsgForRSA(accept, Constants.CODE_40009, Constants.MSG_40009, response,encryptKey);
		            return;
				}
        	}
        	
            String result = "";
            try {
                StringBuffer transUrl = new StringBuffer();
                
                transUrl.append(oauthTransResDto.getTransUrl())
                        .append("?")
                        .append("userId=").append(oauthTransResDto.getUserId())
                        .append("&")
                        .append("orgId=").append(oauthTransResDto.getOrgId())
                        .append("&")
                        .append("agrtCode=").append(oauthTransResDto.getAgrtCode())
                        .append("&")
                        .append("appId=").append(oauthUserDto.getAppId());
                HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
                httpPostWrapper.setServerUrl(transUrl.toString());
                httpPostWrapper.setRawBody(content);
                httpPostWrapper.setMimeType("text/plain");
                httpPostWrapper.setCharset(encodingType);
                HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
                if (httpResponseWrapper.getStatus()) {
                	result = (String) httpResponseWrapper.getContent();
                    if("RSA".equals(oauthTransResDto.getEncryptType())) {
                		try {
                			logger.info("return result:{}",result);
                			result = RSACoder.encryptByPublicKey(result, oauthTransResDto.getEncryptKey());
        				} catch (Exception e) {
        					logger.error("Invalid param,requestUrl:{},accept:{},errorCode:{}", urls[0], accept, Constants.MSG_40009);
        		            this.formateErrorMsgForRSA(accept, Constants.CODE_40009, Constants.MSG_40009, response,encryptKey);
        		            return;
        				}
                	}
                    response.setCharacterEncoding(encodingType);
                    response.setContentType("text/html; charset=" + encodingType);
                    response.getWriter().write(result);
                    response.getWriter().flush();
                    return;
                } else {
                    logger.error("ErrorMessage:{}", httpResponseWrapper.getErrorMessage());
                    this.formateErrorMsgForRSA(accept, Constants.CODE_FAIL, httpResponseWrapper.getErrorMessage(), response,encryptKey);
                    return;
                }
            } catch (IllegalStateException e) {
                try {
                    result = "The request is not responding. Please try again later, error:" + e;
                    logger.error("The request is not responding. Please try again later, error:{}", e);
                    this.formateErrorMsgForRSA(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response,encryptKey);
                    return;
                } catch (Exception ex) {
                    throw new IOException(ex);
                }
            } catch (Exception e) {
                try {
                    result = "Request platform connection error, please contact the relevant personnel, error:" + e;
                    logger.error("Request platform connection error, please contact the relevant personnel, error:{}", e);
                    this.formateErrorMsgForRSA(accept, Constants.CODE_FAIL, "The request is not responding. Please try again later", response,encryptKey);
                    return;
                } catch (Exception ex) {
                    throw new IOException(ex);
                }
            } finally {
                logger.info("------Return Interface info:{}", result);
                this.dbLog(appId, "NONE", oauthTransResDto.getId(), content, result);
            }
        }
    }
    
    /**
     * 生成签名
     * 
     * @param oauthTransResDto
     * @param content
     * @param encodingType
     * @return
     */
    private String generateSign(OauthTransResDto oauthTransResDto, String content, String encodingType,String generateSign) {
    	try {
    		if ("MD5".equals(oauthTransResDto.getEncryptType())) {
                return MD5Helper.sign(oauthTransResDto.getEncryptKey().trim() + content, encodingType);
            }else if("RSA".equals(oauthTransResDto.getEncryptType().toUpperCase())) {//非对称加密
            	if(RSACoder.verify(content, oauthTransResDto.getEncryptKey().trim(), generateSign)) {
            		return generateSign;
            	}else {
            		return "";
            	}
            }
		} catch (Exception e) {
			return "";
		}
        return "";
    }

    /**
     * 输出错误信息
     * 
     * @param accept
     * @param errorCode
     * @param msg
     * @param response
     * @throws Exception
     */
    private void formateErrorMsg(String accept, String errorCode, String msg, ServletResponse response) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setState(Constants.STATE_FAIL);
        responseMessage.setCode(errorCode);
        responseMessage.setMessage(msg);
        if (accept == null || "*/*".equals(accept) || "application/json".equals(accept)) {
            response.setCharacterEncoding(StandardCharset.UTF_8.name());
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(responseMessage));
            response.getWriter().flush();
        } else if ("application/xml".equals(accept)) {
            XMLSerializer xmlSerializer = new XMLSerializer();
            xmlSerializer.setRootName("response-message");
            // xmlSerializer.setElementName("item");
            xmlSerializer.setTypeHintsEnabled(false);
            response.setCharacterEncoding(StandardCharset.UTF_8.name());
            response.setContentType("text/html;charset=UTF-8");
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                @Override
                public boolean apply(Object source, String name, Object value) {
                    if (value == null) {
                        return true;
                    } else if (value instanceof JSONArray && ((JSONArray) value).size() == 0) { // todo...
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            response.getWriter().write(xmlSerializer.write(JSONObject.fromObject(responseMessage, jsonConfig)));
            response.getWriter().flush();
            /** added by mxiangyuan 2017/11/23 for ITPRO-676 错误信息提示日志记录优化   begin **/
        } else if("text/html".equals(accept)) {
            response.setCharacterEncoding(StandardCharset.UTF_8.name());
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(msg==null?"system exception":msg);
            response.getWriter().flush();
            /** added by mxiangyuan 2017/11/23 for ITPRO-676 错误信息提示日志记录优化   end **/
        } else {
            throw new RuntimeException("not support this accept.");
        }
    }
    
    /**
     * 输出错误信息
     * 
     * @param accept
     * @param errorCode
     * @param msg
     * @param response
     * @throws Exception
     */
    private void formateErrorMsgForRSA(String accept, String errorCode, String msg, ServletResponse response,String encryptKey) throws IOException {
    	String returnTxt = "";
    	
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setState(Constants.STATE_FAIL);
        responseMessage.setCode(errorCode);
        responseMessage.setMessage(msg);
        if (accept == null || "*/*".equals(accept) || "application/json".equals(accept)) {
        	returnTxt = JSON.toJSONString(responseMessage);
        } else if ("application/xml".equals(accept)) {
            XMLSerializer xmlSerializer = new XMLSerializer();
            xmlSerializer.setRootName("response-message");
            // xmlSerializer.setElementName("item");
            xmlSerializer.setTypeHintsEnabled(false);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
                @Override
                public boolean apply(Object source, String name, Object value) {
                    if (value == null) {
                        return true;
                    } else if (value instanceof JSONArray && ((JSONArray) value).size() == 0) { // todo...
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            returnTxt = xmlSerializer.write(JSONObject.fromObject(responseMessage, jsonConfig));
        } else if("text/html".equals(accept)) {
        	returnTxt = msg==null?"system exception":msg;
        } else {
            throw new RuntimeException("not support this accept.");
        }
        
        response.setCharacterEncoding(StandardCharset.UTF_8.name());
        response.setContentType("text/html;charset=UTF-8");
        try {
        	logger.info("return interface_info:{}",returnTxt);
        	returnTxt = RSACoder.encryptByPublicKey(returnTxt, encryptKey);
        	logger.info("return encryptByPublicKey:{}",returnTxt);
			response.getWriter().write(returnTxt);
		} catch (Exception e) {//这里没有加密失败一说，如果加密失败代表加密算法逻辑有问题，就不能正常返回。
			logger.error("rsa_publicKey encrypt, error:{}", e);
			response.getWriter().write("");
			e.printStackTrace();
		}
        response.getWriter().flush();
    }
    
    
    private void dbLog(String appId, String token, Integer resourceId, String reqContent, String respContent) {
        if ("Y".equals(ConfigUtil.getValue("db.log.switch"))) {
            try {
                OauthServiceLogDto oauthServiceLogDto = new OauthServiceLogDto();
                oauthServiceLogDto.setAppId(appId);
                oauthServiceLogDto.setToken(token);
                oauthServiceLogDto.setResourceId(resourceId);
                oauthServiceLogDto.setReqContent(reqContent);
                oauthServiceLogDto.setRespContent(respContent);
                oauthServiceLogDto.setCreatedDate(new Date());
                SpringUtils.getBean(OauthServiceLogService.class).insertNotNull(oauthServiceLogDto);
            } catch (Exception ex) {
                logger.error("Write oauth service log error, error:{}", ex);
            }
        }
    }

    private String[] generateAllPattenURL(HttpServletRequest request) {
        String[] url = new String[2];

        StringBuffer reqPath = new StringBuffer();
        reqPath.append("");

        String servletPath = request.getServletPath();
        if (StringUtil.isNotEmpty(servletPath)) {
            reqPath.append(servletPath);
        }
        String pathInfo = request.getPathInfo();
        if (StringUtil.isNotEmpty(pathInfo)) {
            reqPath.append(pathInfo);
        }
        url[0] = reqPath.toString();

        String queryString = request.getQueryString();
        if (StringUtil.isNotEmpty(queryString)) {
            reqPath.append("?" + queryString);
        }
        url[1] = reqPath.toString();

        return url;
    }
    
    /**
     * 获取用户真实IP地址
     * 
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 校验所给IP是否满足给定规则
     * 
     * @param ipStr 待校验IP
     * @param ipPattern IP匹配规则,支持 * 匹配所有和 - 匹配范围,用分号分隔 ; 例如：192.168.1.*;192.168.2.1-128
     * @return
     */
    private boolean validateIP(String ipStr, String ipPattern) {
        if (ipStr == null || ipPattern == null) {
            return false;
        }
        String[] patternList = ipPattern.split(";");
        for (String pattern : patternList) {
            if (passValidate(ipStr, pattern)) {
                return true;
            }
        }
        return false;
    }

    private boolean passValidate(String ipStr, String pattern) {
        String[] ipStrArr = ipStr.split("\\.");
        String[] patternArr = pattern.split("\\.");
        if (ipStrArr.length != 4 || patternArr.length != 4) {
            return false;
        }
        int end = ipStrArr.length;
        if (patternArr[3].contains("-")) {
            end = 3;
            String[] rangeArr = patternArr[3].split("-");
            int from = Integer.parseInt(rangeArr[0]);
            int to = Integer.parseInt(rangeArr[1]);
            int value = Integer.parseInt(ipStrArr[3]);
            if (value < from || value > to) {
                return false;
            }
        }
        for (int i = 0; i < end; i++) {
            if (patternArr[i].equals("*")) {
                continue;
            }
            if (!patternArr[i].equalsIgnoreCase(ipStrArr[i])) {
                return false;
            }
        }
        return true;
    }
     
    private OauthTransResDto getFuzzyUrl(List<OauthTransResDto> list, String urlPart) {
        if (list == null || list.size() == 0) {
            return null;
        }
        for (OauthTransResDto dto : list) {
            if (urlPart.startsWith(dto.getRequestUrl() + "/")) {
                dto.setTransUrl(dto.getTransUrl() + urlPart.substring(dto.getRequestUrl().length()));
                return dto;
            }
        }
        return null;
    }
     
    /**
     * xml2JSON
     * 
     * @param xml
     * @return
     * @throws DocumentException
     * 
     */
    @SuppressWarnings("unused")
    private String convertXMLToJSON(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.clearNamespaces();
        xmlSerializer.setTypeHintsEnabled(false);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if (value == null) {
                    return true;
                } else if (value instanceof JSONArray && ((JSONArray) value).size() == 0) { // todo...
                    return true;
                } else {
                    return false;
                }
            }
        });
        return JSONObject.fromObject(xmlSerializer.read(xml).toString(), jsonConfig).toString();
    }

    /**
     * 将JSON2xml
     * 
     * @param json
     * @param rootName
     * @return
     * 
     */
    @SuppressWarnings("unused")
    private String convertJSONToXml(String jsonStr) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        // 设置根元素名称
        xmlSerializer.setRootName("response-message");
        // xmlSerializer.setElementName("item");
        // 设置类型提示，即是否为元素添加类型 type = "string"
        xmlSerializer.setTypeHintsEnabled(false);
        net.sf.json.JSON json = JSONSerializer.toJSON(jsonStr);
        return xmlSerializer.write(json).replace("\r\n", "").replace("\n", "");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
