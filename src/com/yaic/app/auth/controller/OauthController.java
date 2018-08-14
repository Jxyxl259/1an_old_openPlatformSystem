package com.yaic.app.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaic.app.Constants;
import com.yaic.app.auth.dto.ResponseMessage;
import com.yaic.app.auth.service.OauthService;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/oauth2/")
public class OauthController {
	
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);

    public static final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    private OauthService oauthService;

    @RequestMapping("authorize")
    @ResponseBody
    public ResponseMessage authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String app_id = request.getParameter("app_id");
        String app_secret = request.getParameter("app_secret");
        String code = request.getParameter("code");
        String grant_type = request.getParameter("grant_type");

        ResponseMessage responseMessage = new ResponseMessage();

        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(app_id)) {
            checkMsg.append("app_id,");
        }
        if (StringUtil.isEmpty(app_secret)) {
            checkMsg.append("app_secret,");
        }
        if (StringUtil.isEmpty(code)) {
            checkMsg.append("code,");
        }
        if (StringUtil.isEmpty(grant_type)) {
            checkMsg.append("grant_type,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty");
            responseMessage.setCode(Constants.CODE_40006);
            return responseMessage;
        }
        
        logger.info("authorize_params:app_id:{}", app_id);

        if ("authorization_code".equals(grant_type)) {

            responseMessage = this.getToken(app_id, app_secret);

        } else {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40004);
            responseMessage.setCode(Constants.CODE_40004);
        }

        return responseMessage;
    }
    
    @RequestMapping("token_refresh")
    @ResponseBody
    public ResponseMessage token_refresh(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String app_id = request.getParameter("app_id");
        String app_secret = request.getParameter("app_secret");
        String grant_type = request.getParameter("grant_type");
        String refresh_token = request.getParameter("refresh_token");

        ResponseMessage responseMessage = new ResponseMessage();

        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(app_id)) {
            checkMsg.append("app_id,");
        }
        if (StringUtil.isEmpty(app_secret)) {
            checkMsg.append("app_secret,");
        }
        if (StringUtil.isEmpty(grant_type)) {
            checkMsg.append("grant_type,");
        } 
        if (StringUtil.isEmpty(refresh_token)) {
            checkMsg.append("refresh_token,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty");
            responseMessage.setCode(Constants.CODE_40006);
            return responseMessage;
        }
        
        logger.info("authorize_params:app_id:{},refresh_token:{}", app_id,refresh_token);

        if ("refresh_token".equals(grant_type)) {

            responseMessage = this.refreshToken(app_id, app_secret, refresh_token);

        } else {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40004);
            responseMessage.setCode(Constants.CODE_40004);
        }

        return responseMessage;
    }

    @RequestMapping("token_auth")
    @ResponseBody
    public ResponseMessage token_auth(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String access_token = request.getParameter("access_token");
        String openid = request.getParameter("open_id");
        
        ResponseMessage responseMessage = new ResponseMessage();
        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(access_token)) {
            checkMsg.append("access_token,");
        }
        if (StringUtil.isEmpty(openid)) {
            checkMsg.append("open_id,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty");
            responseMessage.setCode(Constants.CODE_40006);
            return responseMessage;
        }
        
        return this.token_auth(access_token,openid);
    }
    
    @RequestMapping("interface_list")
    @ResponseBody
    public ResponseMessage interface_list(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String access_token = request.getParameter("access_token");
        String openid = request.getParameter("open_id");
        
        ResponseMessage responseMessage = new ResponseMessage();
        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(access_token)) {
            checkMsg.append("access_token,");
        }
        if (StringUtil.isEmpty(openid)) {
            checkMsg.append("open_id,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(checkMsg.substring(0, checkMsg.length() - 1) + " can not be empty");
            responseMessage.setCode(Constants.CODE_40006);
            return responseMessage;
        }
        
        return this.interface_list(access_token,openid);
    }
    
    private ResponseMessage getToken(String app_id, String app_secret) throws Exception {
        return oauthService.getToken(app_id, app_secret);
    }

    private ResponseMessage refreshToken(String app_id, String app_secret, String refresh_token) throws Exception {
        return oauthService.refreshToken(app_id, app_secret, refresh_token);
    }
    
    private ResponseMessage token_auth(String access_token, String openid) throws Exception {
        return oauthService.token_auth(access_token, openid);
    }
    
    private ResponseMessage interface_list(String access_token, String openid) throws Exception {
        return oauthService.interface_list(access_token, openid);
    }
}
