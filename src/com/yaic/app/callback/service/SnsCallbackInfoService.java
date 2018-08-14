/*
 * Created By lujicong (2016-04-27 14:03:22)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.callback.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yaic.servicelayer.datetime.DateTime;
import com.yaic.app.auth.dto.domain.OauthUserDto;
import com.yaic.app.auth.service.OauthUserService;
import com.yaic.app.callback.dao.SnsCallbackInfoDao;
import com.yaic.app.callback.dto.ResultDto;
import com.yaic.app.callback.dto.domain.SnsCallbackInfoDto;
import com.yaic.app.provider.StatusCodeProvider;
import com.yaic.fa.service.BaseService;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.util.CollectionUtil;
import com.yaic.servicelayer.util.ConfigUtil;
import com.yaic.servicelayer.util.ObjectUtil;
import com.yaic.servicelayer.util.StringUtil;

@Service
public class SnsCallbackInfoService extends BaseService<SnsCallbackInfoDto> {

    private static final String SUCCESS_CODE = "0000";
    private static final String FAIL_CODE = "9999";
    private static final String SUCCESS_MSG = "SUCCESS";
    private static final String QUDIAN_SUCCESS_CODE = "S0000";
    private static final String CREATED_USER = "SYSTEM";
    private static final String UPDATED_USER = "SYSTEM";
    /** OPEN返回状态码前缀 */
    private static final String STATUSCODE_PREFIX = StatusCodeProvider.SYSTEMNO_INTERFACE_OPEN;

    @Autowired
    private OauthUserService oauthUserService;
    @Autowired
    private SnsCallbackInfoDao snsCallbackInfoDao;

    /**
     * 回调业务处理
     * 
     * @param content
     * @return
     */
    public String dealBiz(String content) {
        ResultDto resultDto = new ResultDto();

        SnsCallbackInfoDto snsCallbackInfoDto = null;
        try {
            snsCallbackInfoDto = JSON.parseObject(content, SnsCallbackInfoDto.class);
        } catch (Exception e) {
            resultDto.setMessage("处理失败,请求内容体解析异常");
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        if (snsCallbackInfoDto == null) {
            resultDto.setMessage("处理失败,请求内容体不能为空");
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        StringBuffer checkMsg = new StringBuffer();
        if (StringUtil.isEmpty(snsCallbackInfoDto.getAppId())) {
            checkMsg.append("appId不能为空,");
        }
        if (StringUtil.isEmpty(snsCallbackInfoDto.getDealType())) {
            checkMsg.append("处理类型不能为空,");
        }
        if (StringUtil.isEmpty(snsCallbackInfoDto.getContent())) {
            checkMsg.append("处理内容不能为空,");
        }
        if (StringUtil.isNotEmpty(checkMsg.toString())) {
            resultDto.setMessage(checkMsg.substring(0, checkMsg.length() - 1));
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }

        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(snsCallbackInfoDto.getAppId());
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null) {
            resultDto.setMessage("不存在该APPID");
            resultDto.setCode(FAIL_CODE);
            resultDto.setStatusCode(STATUSCODE_PREFIX + FAIL_CODE);
            return JSON.toJSONString(resultDto);
        }
        /*added by lshuang 2018/07/11 for ITPRO-1022 支付回调信息优化begin */
        SnsCallbackInfoDto ckCallbackInfo = new SnsCallbackInfoDto();
        ckCallbackInfo.setAppId(snsCallbackInfoDto.getAppId());
        ckCallbackInfo.setBusinessNo(snsCallbackInfoDto.getBusinessNo());
        ckCallbackInfo.setDealType(snsCallbackInfoDto.getDealType());
        if (this.count(ckCallbackInfo) == 0) {
        	
            // 先插入,再请求,最后更新状态
        	SnsCallbackInfoDto callbackInfoDto = new SnsCallbackInfoDto();
        	ObjectUtil.copyProperties(callbackInfoDto, snsCallbackInfoDto);
        	callbackInfoDto.setInvalidFlag(0);
        	callbackInfoDto.setCreatedUser(CREATED_USER);
        	callbackInfoDto.setCreatedDate(new Date());
        	callbackInfoDto.setUpdatedUser(UPDATED_USER);
            callbackInfoDto.setUpdatedDate(new Date());
            callbackInfoDto.setDealStatus(2);
            callbackInfoDto.setDealCount(0);
            this.insertNotNull(callbackInfoDto);
            if (StringUtil.isNotEmpty(oauthUserDto.getCallbackUrl())) {
            	HttpResponseWrapper result = this.connectServer(snsCallbackInfoDto.getContent(), oauthUserDto.getCallbackUrl());
            	if (result.getStatus()) {
            		if (SUCCESS_MSG.equals((String)result.getContent())) {
            			callbackInfoDto.setDealStatus(1);
            			callbackInfoDto.setDealCount(1);
                    } else if (QUDIAN_SUCCESS_CODE.equals(JSON.parseObject((String)result.getContent(), Map.class).get("code"))) {	//趣店回调返回特殊化处理
                    	callbackInfoDto.setDealStatus(1);
                        callbackInfoDto.setDealCount(1);
                    } else {
                        callbackInfoDto.setDealStatus(0);
                        callbackInfoDto.setDealCount(1);
                    }
                } else {
                    callbackInfoDto.setDealStatus(0);
                    callbackInfoDto.setDealCount(1);
                }
                this.updateByPrimaryKeyNotNull(callbackInfoDto);
            }
        }
        /*added by lshuang 2018/07/11 for ITPRO-1022 支付回调信息优化end */
        resultDto.setMessage("SUCCESS");
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setStatusCode(SUCCESS_CODE);
        return JSON.toJSONString(resultDto);
    }
    
    /**
     * 定时处理处理中状态数据
     * @return
     */
    public String dealProcessQuartzBiz() {
        DateTime startTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        
        SnsCallbackInfoDto snsCallbackInfoDto = new SnsCallbackInfoDto();
        snsCallbackInfoDto.setDealCount(Integer.parseInt(ConfigUtil.getValue("callback.max.deal.count")));
        snsCallbackInfoDto.setLimitCount(Integer.parseInt(ConfigUtil.getValue("process.callback.limit.count")));
        List<SnsCallbackInfoDto> callbackInfoList = snsCallbackInfoDao.queryDealProcessData(snsCallbackInfoDto);
        
        if (callbackInfoList != null && callbackInfoList.size() > 0) {
            for (SnsCallbackInfoDto callbackInfoDto : callbackInfoList) {
                if(this.selectByPrimaryKey(callbackInfoDto).getDealStatus() != 2) { // 处理中
                    continue;
                }
                callbackInfoDto.setDealStatus(0); // 重置未处理状态
                callbackInfoDto.setUpdatedDate(new Date());
                this.updateByPrimaryKeyNotNull(callbackInfoDto);
            }
        }
        DateTime endTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        return "执行情况=" + startTime + " - " + endTime;
    }

    /**
     * 定时处理没有应答返回成功回调
     */
    public String dealQuartzBiz() {
        
        DateTime startTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        
        SnsCallbackInfoDto snsCallbackInfoDto = new SnsCallbackInfoDto();
        snsCallbackInfoDto.setDealCount(Integer.parseInt(ConfigUtil.getValue("callback.max.deal.count")));
        snsCallbackInfoDto.setLimitCount(Integer.parseInt(ConfigUtil.getValue("callback.limit.count")));
        List<SnsCallbackInfoDto> callbackInfoList = snsCallbackInfoDao.queryCallbackInfo(snsCallbackInfoDto);
        
        if(CollectionUtil.isNotEmpty(callbackInfoList)) {
            
            // 锁定记录
            SnsCallbackInfoDto updateCallbackInfoDto = null;
            for(Iterator<SnsCallbackInfoDto> it = callbackInfoList.iterator();it.hasNext();){
                SnsCallbackInfoDto tmpCallbackInfoDto = it.next();
                
                updateCallbackInfoDto = new SnsCallbackInfoDto();
                updateCallbackInfoDto.setId(tmpCallbackInfoDto.getId());
                if(this.selectByPrimaryKey(updateCallbackInfoDto).getDealStatus() != 0) {
                    it.remove();
                    continue;
                }
                
                updateCallbackInfoDto.setDealStatus(2); // 处理中
                updateCallbackInfoDto.setUpdatedDate(new Date());
                this.updateByPrimaryKeyNotNull(updateCallbackInfoDto);
            }

            SnsCallbackInfoDto callbackInfoDto = null;
            for (int i = 0; i < callbackInfoList.size(); i++) {
                callbackInfoDto = callbackInfoList.get(i);
                OauthUserDto oauthUserDto = new OauthUserDto();
                oauthUserDto.setAppId(callbackInfoDto.getAppId());
                oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
                if (oauthUserDto == null) {
                    continue;
                }
                
                SnsCallbackInfoDto ckCallbackInfoDto = new SnsCallbackInfoDto();
                ckCallbackInfoDto.setId(callbackInfoDto.getId());
                ckCallbackInfoDto = this.selectByPrimaryKey(ckCallbackInfoDto);
                if(ckCallbackInfoDto.getDealStatus() != 2) { // 非处理中直接跳过
                    continue;
                }
                if(ckCallbackInfoDto.getDealCount() > Integer.parseInt(ConfigUtil.getValue("callback.max.deal.count"))) { // 大于等于最大失败处理次数直接跳过
                    continue;
                }
                
                if (StringUtil.isNotEmpty(oauthUserDto.getCallbackUrl())) {
                	HttpResponseWrapper result = this.connectServer(callbackInfoDto.getContent(), oauthUserDto.getCallbackUrl());
                    if (result.getStatus()) {
                        if (SUCCESS_MSG.equals((String)result.getContent())) {
                            callbackInfoDto.setDealStatus(1);
                            callbackInfoDto.setDealCount(callbackInfoDto.getDealCount() + 1);
                        } else if (QUDIAN_SUCCESS_CODE.equals(JSON.parseObject((String)result.getContent(), Map.class).get("code"))) {	//趣店回调返回特殊化处理
                        	callbackInfoDto.setDealStatus(1);
                            callbackInfoDto.setDealCount(callbackInfoDto.getDealCount() + 1);
                        } else {
                            callbackInfoDto.setDealStatus(0);
                            callbackInfoDto.setDealCount(callbackInfoDto.getDealCount() + 1);
                        }
                    } else {
                        callbackInfoDto.setDealStatus(0);
                        callbackInfoDto.setDealCount(callbackInfoDto.getDealCount() + 1);
                    }
                    callbackInfoDto.setUpdatedDate(new Date());
                    this.updateByPrimaryKeyNotNull(callbackInfoDto);
                }
            }
        }
        
        DateTime endTime = new DateTime(new DateTime(), DateTime.YEAR_TO_MILLISECOND);
        
        return "执行情况=" + startTime + " - " + endTime;
    }

    public HttpResponseWrapper connectServer(final String content, final String callbackUrl) {
		final HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
		httpPostWrapper.setServerUrl(callbackUrl);
		httpPostWrapper.setConnectTimeout(30 * 1000);
		httpPostWrapper.setSocketTimeout(30 * 1000);
		httpPostWrapper.setRawBody(content);
		httpPostWrapper.setMimeType("text/plain");
		return HttpTransceiver.doPost(httpPostWrapper);
    }
}
