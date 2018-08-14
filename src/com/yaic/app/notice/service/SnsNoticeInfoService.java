/*
 * Created By lujicong (2016-08-31 09:36:55)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.notice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yaic.servicelayer.datetime.DateTime;
import com.yaic.app.auth.dto.domain.OauthUserDto;
import com.yaic.app.auth.service.OauthUserService;
import com.yaic.app.callback.dto.ResultDto;
import com.yaic.app.notice.dao.SnsNoticeInfoDao;
import com.yaic.app.notice.dto.domain.SnsNoticeInfoDto;
import com.yaic.app.provider.StatusCodeProvider;
import com.yaic.fa.service.BaseService;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpGetWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostKVpairWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.util.ArrayUtil;
import com.yaic.servicelayer.util.ConfigUtil;
import com.yaic.servicelayer.util.ObjectUtil;
import com.yaic.servicelayer.util.StringUtil;

@Service
public class SnsNoticeInfoService extends BaseService<SnsNoticeInfoDto> {
    private static final String SUCCESS_CODE = "0000";
    private static final String FAIL_CODE = "9999";
    private static final String SUCCESS_MSG = "SUCCESS";
    private static final String CREATED_USER = "SYSTEM";
    private static final String UPDATED_USER = "SYSTEM";
    /*** 字段之间的分割符 ***/
    public static final String FIELD_SEPARATOR = "_FIELD_SEPARATOR_";
    /** OPEN返回状态码前缀 */
    private static final String STATUSCODE_PREFIX = StatusCodeProvider.SYSTEMNO_INTERFACE_OPEN;


    @Autowired
    private OauthUserService oauthUserService;
    @Autowired
    private SnsNoticeInfoDao snsNoticeInfoDao;
    

    /**
     * 回调业务处理
     * 
     * @param content
     * @return
     */
    public String dealBiz(String content) {

        ResultDto resultDto = new ResultDto();

        SnsNoticeInfoDto snsNoticeInfoDto = null;
        try {
            snsNoticeInfoDto = JSON.parseObject(content, SnsNoticeInfoDto.class);
        } catch (Exception e) {
            resultDto.setMessage("处理失败,请求内容体解析异常");
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        if (snsNoticeInfoDto == null) {
            resultDto.setMessage("处理失败,请求内容体不能为空");
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(snsNoticeInfoDto.getAppId()) && StringUtil.isEmpty(snsNoticeInfoDto.getNoticeUrl())) {
            checkMsg.append("appId、noticeUrl不能同时为空,");
        }
        if (snsNoticeInfoDto.getRetryPolicy() != null && snsNoticeInfoDto.getRetryPolicy() == 0 && 
                StringUtil.isEmpty(snsNoticeInfoDto.getBusinessNo())) {
            checkMsg.append("业务单号不能为空,");
        }
        if (snsNoticeInfoDto.getRetryPolicy() != null && snsNoticeInfoDto.getRetryPolicy() == 0 
                && StringUtil.isEmpty(snsNoticeInfoDto.getDealType())) {
            checkMsg.append("失败重试时处理类型不能为空,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            resultDto.setMessage(checkMsg.substring(0, checkMsg.length() - 1));
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        String requestType = snsNoticeInfoDto.getRequestType();
        if (StringUtil.isEmpty(requestType)) {
            requestType = "POST";
        }
        
        String isPair = snsNoticeInfoDto.getIsPair();
        if(StringUtil.isEmpty(isPair)) {
            isPair = "1";
        }
        
        String encodingType = snsNoticeInfoDto.getEncodingType();
        if (StringUtil.isEmpty(encodingType)) {
            encodingType = StandardCharset.UTF_8.name();
        }

        String requestUrl = snsNoticeInfoDto.getNoticeUrl();
        if (StringUtil.isEmpty(requestUrl)) {
            OauthUserDto oauthUserDto = new OauthUserDto();
            oauthUserDto.setAppId(snsNoticeInfoDto.getAppId());
            oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
            if (oauthUserDto == null) {
                resultDto.setMessage("不存在该APPID");
                resultDto.setCode(FAIL_CODE);
                resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
                return JSON.toJSONString(resultDto);
            } else {
                requestUrl = oauthUserDto.getCallbackUrl();
            }
        }

        if (snsNoticeInfoDto.getRetryPolicy() != null && snsNoticeInfoDto.getRetryPolicy() == 0) { // 失败重试,用于推送通知信息,内部不关注应答
            SnsNoticeInfoDto ckNoticeInfoDto = new SnsNoticeInfoDto();
            ckNoticeInfoDto.setBusinessNo(snsNoticeInfoDto.getBusinessNo());
            ckNoticeInfoDto.setDealType(snsNoticeInfoDto.getDealType());
            ckNoticeInfoDto.setInvalidFlag(0);
            if (this.count(ckNoticeInfoDto) == 0) {
                // 先插入,再请求,最后更新状态
                SnsNoticeInfoDto noticeInfoDto = new SnsNoticeInfoDto();
                ObjectUtil.copyProperties(noticeInfoDto, snsNoticeInfoDto);
                noticeInfoDto.setInvalidFlag(0);
                noticeInfoDto.setCreatedUser(CREATED_USER);
                noticeInfoDto.setCreatedDate(new Date());
                noticeInfoDto.setUpdatedUser(UPDATED_USER);
                noticeInfoDto.setUpdatedDate(new Date());
                noticeInfoDto.setDealStatus(0);
                noticeInfoDto.setDealCount(0);
                noticeInfoDto.setRetryPolicy(0); // 失败重试
                this.insertNotNull(noticeInfoDto);

                HttpResponseWrapper result = this.connectServer(snsNoticeInfoDto.getContent(), requestUrl, requestType, encodingType, isPair);

                if (result.getStatus()) {
                    if (SUCCESS_MSG.equals((String)result.getContent())) {
                        noticeInfoDto.setDealStatus(1);
                        noticeInfoDto.setDealCount(1);
                    } else {
                        noticeInfoDto.setDealStatus(0);
                        noticeInfoDto.setDealCount(1);
                    }
                } else {
                    noticeInfoDto.setDealStatus(0);
                    noticeInfoDto.setDealCount(1);
                }
                this.updateByPrimaryKeyNotNull(noticeInfoDto);
            }
        } else { // 不失败重试,同步,对方返回信息都放入到message中
        	HttpResponseWrapper result = this.connectServer(snsNoticeInfoDto.getContent(), requestUrl, requestType, encodingType, isPair);
            if (result.getStatus()) {
                resultDto.setMessage((String)result.getContent());
                resultDto.setCode(SUCCESS_CODE);
                resultDto.setStatusCode(SUCCESS_CODE);
            } else {
                resultDto.setMessage(result.getErrorMessage());
                resultDto.setCode(FAIL_CODE);
                resultDto.setStatusCode(STATUSCODE_PREFIX + StatusCodeProvider.getCode(result, FAIL_CODE));
            }
            return JSON.toJSONString(resultDto);
        }
        resultDto.setMessage("SUCCESS");
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setStatusCode(SUCCESS_CODE);
        return JSON.toJSONString(resultDto);
    }
    
    
    /**
     * 定时处理没有应答返回成功回调
     */
    public String dealQuartzBiz() {
        
        DateTime startTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        
        SnsNoticeInfoDto snsNoticeInfoDto = new SnsNoticeInfoDto();
        snsNoticeInfoDto.setDealCount(Integer.parseInt(ConfigUtil.getValue("notice.max.deal.count")));
        snsNoticeInfoDto.setLimitCount(Integer.parseInt(ConfigUtil.getValue("notice.limit.count")));
        List<SnsNoticeInfoDto> noticeInfoList = snsNoticeInfoDao.queryNoticeInfo(snsNoticeInfoDto);

        SnsNoticeInfoDto noticeInfoDto = null;
        for (int i = 0; i < noticeInfoList.size(); i++) {
            
            noticeInfoDto = noticeInfoList.get(i);
            
            String requestUrl = noticeInfoDto.getNoticeUrl();
            if (StringUtil.isEmpty(requestUrl)) {
                OauthUserDto oauthUserDto = new OauthUserDto();
                oauthUserDto.setAppId(noticeInfoDto.getAppId());
                oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
                if (oauthUserDto == null) {
                    continue;
                }
                requestUrl = oauthUserDto.getCallbackUrl();
            }
            
            HttpResponseWrapper result = this.connectServer(noticeInfoDto.getContent(), requestUrl, noticeInfoDto.getRequestType(), noticeInfoDto.getEncodingType(), noticeInfoDto.getIsPair());
            if (result.getStatus()) {
                if (SUCCESS_MSG.equals((String)result.getContent())) {
                    noticeInfoDto.setDealStatus(1);
                    noticeInfoDto.setDealCount(noticeInfoDto.getDealCount() + 1);
                } else {
                    noticeInfoDto.setDealStatus(0);
                    noticeInfoDto.setDealCount(noticeInfoDto.getDealCount() + 1);
                }
            } else {
                noticeInfoDto.setDealStatus(0);
                noticeInfoDto.setDealCount(noticeInfoDto.getDealCount() + 1);
            }
            noticeInfoDto.setUpdatedDate(new Date());
            this.updateByPrimaryKeyNotNull(noticeInfoDto);
        }
        
        DateTime endTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        
        return "执行情况=" + startTime + " - " + endTime;
    }

    private HttpResponseWrapper connectServer(String content, String callbackUrl, String requestType, String encodingType, String isPair) {
		HttpResponseWrapper httpResponseWrapper  = null;
		if ("GET".equalsIgnoreCase(requestType)) {
			HttpGetWrapper httpGetWrapper = new HttpGetWrapper();
			httpGetWrapper.setServerUrl(callbackUrl);
			httpGetWrapper.setConnectTimeout(30 * 1000);
			httpGetWrapper.setSocketTimeout(30 * 1000);
			httpResponseWrapper = HttpTransceiver.doGet(httpGetWrapper);
		} else if ("POST".equalsIgnoreCase(requestType)) {
			if ("1".equals(isPair)) {
				HttpPostRawWrapper httpPostRawWrapper = new HttpPostRawWrapper();
				httpPostRawWrapper.setServerUrl(callbackUrl);
				httpPostRawWrapper.setConnectTimeout(30 * 1000);
				httpPostRawWrapper.setSocketTimeout(30 * 1000);
				httpPostRawWrapper.setMimeType("text/plain");
				httpPostRawWrapper.setCharset(encodingType);
				httpPostRawWrapper.setRawBody(content);
				httpResponseWrapper = HttpTransceiver.doPost(httpPostRawWrapper);
			} else {
				String[] fleldArray = content.split(FIELD_SEPARATOR);
				Map<String, String> kvPairs = new HashMap<>();
				if (ArrayUtil.isNotEmpty(fleldArray)) {
					for (String field : fleldArray) {
						if (StringUtil.isNotEmpty(field)) {
							if (field.indexOf("=") == -1) {
								httpResponseWrapper = new HttpResponseWrapper();
								httpResponseWrapper.setStatus(false);
								httpResponseWrapper.setErrorMessage("请求参数有误");
								return httpResponseWrapper;
							}
							String name = field.substring(0, field.indexOf("="));
							String value = field.substring(field.indexOf("=") + 1);
							kvPairs.put(name, value);
						}
					}
				}
				HttpPostKVpairWrapper httpRequestWrapper = new HttpPostKVpairWrapper();
				httpRequestWrapper.setServerUrl(callbackUrl);
				httpRequestWrapper.setKvpairs(kvPairs);
				httpRequestWrapper.setCharset(encodingType);
				httpResponseWrapper = HttpTransceiver.doPost(httpRequestWrapper);
			}
		} else {
			httpResponseWrapper = new HttpResponseWrapper();
			httpResponseWrapper.setStatus(false);
			httpResponseWrapper.setErrorMessage("不支持该请求方式:" + requestType);
		}
		
		return httpResponseWrapper;
    }

}
