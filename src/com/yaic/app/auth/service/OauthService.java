package com.yaic.app.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.Constants;
import com.yaic.app.auth.dto.InterfaceInfoDto;
import com.yaic.app.auth.dto.RespBodyData;
import com.yaic.app.auth.dto.ResponseMessage;
import com.yaic.app.auth.dto.domain.OauthTokenDto;
import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.app.auth.dto.domain.OauthUserDto;
import com.yaic.servicelayer.util.ConfigUtil;

@Service
public class OauthService {

    private static final Logger logger = LoggerFactory.getLogger(OauthService.class);

    private static final String CREATED_USER = "system";
    private static final String UPDATED_USER = "system";

    @Autowired
    private OauthUserService oauthUserService;
    @Autowired
    private OauthTokenService oauthTokenService;
    @Autowired
    private OauthTransResService oauthTransResService;

    public ResponseMessage getToken(String app_id, String app_secret) throws Exception {

        ResponseMessage responseMessage = new ResponseMessage();
        
        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(app_id);
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null) {
            logger.error("not exist this app_id:{}", app_id);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40001);
            responseMessage.setCode(Constants.CODE_40001);
            return responseMessage;
        } else {
            if (new Integer(1).equals(oauthUserDto.getInvalidFlag())) {
                logger.error("app_id invalid:{}", app_id);
                responseMessage.setState(Constants.STATE_FAIL);
                responseMessage.setMessage(Constants.MSG_40001);
                responseMessage.setCode(Constants.CODE_40001);
                return responseMessage;
            }
            if (!app_secret.equals(oauthUserDto.getAppSecret())) {
                logger.error("app_secret invalid,app_id:{},app_secret:{}", app_id, app_secret);
                responseMessage.setState(Constants.STATE_FAIL);
                responseMessage.setMessage(Constants.MSG_40002);
                responseMessage.setCode(Constants.CODE_40002);
                return responseMessage;
            }
        }

        OauthTokenDto oauthTokenDto = new OauthTokenDto();
        oauthTokenDto.setAppId(app_id);
        oauthTokenDto = oauthTokenService.selectOne(oauthTokenDto);

        if (oauthTokenDto == null) {
            String openId = "";
            
            while(true){ 
                openId = UUID.randomUUID().toString().replaceAll("-", "");
                OauthTokenDto ot = new OauthTokenDto();
                ot.setOpenId(openId);
                if(oauthTokenService.count(ot) < 1) {
                    break; 
                }
            } 
            
            DateTime nowTime = new DateTime();

            String accessToken = "";
            while(true){ 
                accessToken = UUID.randomUUID().toString().replaceAll("-", "");
                OauthTokenDto ot = new OauthTokenDto();
                ot.setToken(accessToken);
                if(oauthTokenService.count(ot) < 1) {
                    break; 
                }
            }
            
            DateTime expirationTime = nowTime.plusSeconds(Integer.parseInt(ConfigUtil.getValue("token.expire.time")));

            String refreshToken = "";
            while(true){ 
                refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
                OauthTokenDto ot = new OauthTokenDto();
                ot.setRefreshToken(refreshToken);
                if(oauthTokenService.count(ot) < 1) {
                    break; 
                }
            }
            
            DateTime refreshExpirationTime = nowTime.plusSeconds(Integer.parseInt(ConfigUtil.getValue("refresh.token.expire.time")));
            
            responseMessage.setState(Constants.STATE_SUCCESS);
            responseMessage.setMessage(Constants.MSG_SUCCESS);
            responseMessage.setCode(Constants.CODE_SUCCESS);
            RespBodyData respBodyData = new RespBodyData();
            respBodyData.setAccess_token(accessToken);
            respBodyData.setRefresh_token(refreshToken);
            respBodyData.setExpires_in(Long.valueOf(Seconds.secondsBetween(new DateTime(), expirationTime).getSeconds()));
            respBodyData.setOpen_id(openId);
            responseMessage.setData(respBodyData);

            oauthTokenDto = new OauthTokenDto();
            oauthTokenDto.setAppId(app_id);
            oauthTokenDto.setToken(accessToken);
            oauthTokenDto.setExpireTime(expirationTime.toDate());
            oauthTokenDto.setRefreshToken(refreshToken);
            oauthTokenDto.setRefExpireTime(refreshExpirationTime.toDate());
            oauthTokenDto.setOpenId(openId);
            oauthTokenDto.setInvalidFlag(0);
            oauthTokenDto.setCreatedUser(CREATED_USER);
            oauthTokenDto.setCreatedDate(new Date());
            oauthTokenDto.setUpdatedUser(UPDATED_USER);
            oauthTokenDto.setUpdatedDate(new Date());
            oauthTokenService.insertNotNull(oauthTokenDto);
        } else {
            String accessToken = oauthTokenDto.getToken();
            DateTime expirationTime = new DateTime(oauthTokenDto.getExpireTime());
            String refreshToken = oauthTokenDto.getRefreshToken();
            //DateTime refExpirationTime = new DateTime(oauthTokenDto.getRefExpireTime());
            
            //Long refExpiresIn = Long.valueOf(Seconds.secondsBetween(new DateTime(), refExpirationTime).getSeconds());
            if(oauthTokenDto.getRefExpireTime().before(new Date())) {
                DateTime nowTime = new DateTime();
                
                while(true){ 
                    accessToken = UUID.randomUUID().toString().replaceAll("-", "");
                    OauthTokenDto ot = new OauthTokenDto();
                    ot.setToken(accessToken);
                    if(oauthTokenService.count(ot) < 1) {
                        break; 
                    }
                }
                
                expirationTime = nowTime.plusSeconds(Integer.parseInt(ConfigUtil.getValue("token.expire.time")));
                
                while(true){ 
                    refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
                    OauthTokenDto ot = new OauthTokenDto();
                    ot.setRefreshToken(refreshToken);
                    if(oauthTokenService.count(ot) < 1) {
                        break; 
                    }
                }
                
                DateTime refExpirationTime = nowTime.plusSeconds(Integer.parseInt(ConfigUtil.getValue("refresh.token.expire.time")));
                
                oauthTokenDto.setToken(accessToken);
                oauthTokenDto.setExpireTime(expirationTime.toDate());
                oauthTokenDto.setRefreshToken(refreshToken);
                oauthTokenDto.setRefExpireTime(refExpirationTime.toDate());
                oauthTokenService.updateByPrimaryKeyNotNull(oauthTokenDto);
            }
            

            Long expiresIn = Long.valueOf(Seconds.secondsBetween(new DateTime(), expirationTime).getSeconds());
            
            responseMessage.setState(Constants.STATE_SUCCESS);
            responseMessage.setMessage(Constants.MSG_SUCCESS);
            responseMessage.setCode(Constants.CODE_SUCCESS);
            RespBodyData respBodyData = new RespBodyData();
            respBodyData.setAccess_token(accessToken);
            respBodyData.setRefresh_token(refreshToken);
            respBodyData.setExpires_in(expiresIn < 0 ? 0 : expiresIn);
            respBodyData.setOpen_id(oauthTokenDto.getOpenId());
            responseMessage.setData(respBodyData);
        }

        return responseMessage;
    }

    public ResponseMessage refreshToken(String app_id, String app_secret, String refresh_token) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        
        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(app_id);
        oauthUserDto = oauthUserService.selectByPrimaryKey(oauthUserDto);
        if (oauthUserDto == null) {
            logger.error("not exist this app_id:{}", app_id);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40001);
            responseMessage.setCode(Constants.CODE_40001);
            return responseMessage;
        } else {
            if (new Integer(1).equals(oauthUserDto.getInvalidFlag())) {
                logger.error("app_id invalid:{}", app_id);
                responseMessage.setState(Constants.STATE_FAIL);
                responseMessage.setMessage(Constants.MSG_40001);
                responseMessage.setCode(Constants.CODE_40001);
                return responseMessage;
            }
            if (!app_secret.equals(oauthUserDto.getAppSecret())) {
                logger.error("app_secret invalid:{}", app_id);
                responseMessage.setState(Constants.STATE_FAIL);
                responseMessage.setMessage(Constants.MSG_40002);
                responseMessage.setCode(Constants.CODE_40002);
                return responseMessage;
            }
        }

        OauthTokenDto oauthTokenDto = new OauthTokenDto();
        oauthTokenDto.setAppId(app_id);
        oauthTokenDto = oauthTokenService.selectOne(oauthTokenDto);

        if (oauthTokenDto == null) {
            logger.error("not exist this appId refresh_token,app_id:{},refresh_token:{}", app_id, refresh_token);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40001);
            responseMessage.setCode(Constants.CODE_40001);
            return responseMessage;
        }

        String refreshToken = oauthTokenDto.getRefreshToken();

        if (!refresh_token.equals(refreshToken)) {
            logger.error("error refresh_token,app_id:{},refresh_token:{}", app_id, refresh_token);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40007);
            responseMessage.setCode(Constants.CODE_40007);
            return responseMessage;
        }
        
        //Long refExpiresIn = Long.valueOf(Seconds.secondsBetween(new DateTime(), new DateTime(oauthTokenDto.getRefExpireTime())).getSeconds());
        if(oauthTokenDto.getRefExpireTime().before(new Date())) {
            logger.error("refresh_token expired,app_id:{},refresh_token:{}", app_id, refresh_token);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40005);
            responseMessage.setCode(Constants.CODE_40005);
            return responseMessage;
        }

        String accessToken = "";
        while(true){ 
            accessToken = UUID.randomUUID().toString().replaceAll("-", "");
            OauthTokenDto ot = new OauthTokenDto();
            ot.setToken(accessToken);
            if(oauthTokenService.count(ot) < 1) {
                break; 
            }
        }
        
        DateTime expirationTime = new DateTime().plusSeconds(Integer.parseInt(ConfigUtil.getValue("token.expire.time")));

        Long expiresIn = Long.valueOf(Seconds.secondsBetween(new DateTime(), expirationTime).getSeconds());
        
        responseMessage.setState(Constants.STATE_SUCCESS);
        responseMessage.setMessage(Constants.MSG_SUCCESS);
        responseMessage.setCode(Constants.CODE_SUCCESS);
        RespBodyData respBodyData = new RespBodyData();
        respBodyData.setAccess_token(accessToken);
        respBodyData.setRefresh_token(refreshToken);
        respBodyData.setExpires_in(expiresIn < 0 ? 0 : expiresIn);
        respBodyData.setOpen_id(oauthTokenDto.getOpenId());
        responseMessage.setData(respBodyData);
        
        oauthTokenDto.setToken(accessToken);
        oauthTokenDto.setExpireTime(expirationTime.toDate());
        oauthTokenService.updateByPrimaryKeyNotNull(oauthTokenDto);

        return responseMessage;
    }

    public ResponseMessage token_auth(String access_token, String openid) throws Exception {
        
        ResponseMessage responseMessage = new ResponseMessage();
        
        OauthTokenDto oauthTokenDto = new OauthTokenDto();
        oauthTokenDto.setToken(access_token);
        oauthTokenDto.setOpenId(openid);
        oauthTokenDto = oauthTokenService.selectOne(oauthTokenDto);
        if (oauthTokenDto == null) {
            logger.error("invalid access_token or invalid open_id,accessToken:{},openId:{}", access_token, openid);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40032);
            responseMessage.setCode(Constants.CODE_40032);
            return responseMessage;
        }

        if(oauthTokenDto.getExpireTime().before(new Date())) {
            logger.error("access_token expired:{}", access_token);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40029);
            responseMessage.setCode(Constants.CODE_40029);
            return responseMessage;
        }
        
        if(!openid.equals(oauthTokenDto.getOpenId())) {
            logger.error("invalid openid:{}", openid);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40031);
            responseMessage.setCode(Constants.CODE_40031);
            return responseMessage;
        }
        
        responseMessage.setState(Constants.STATE_SUCCESS);
        responseMessage.setMessage(Constants.MSG_SUCCESS);
        responseMessage.setCode(Constants.CODE_SUCCESS);
        
        return responseMessage;
    }
    
    public ResponseMessage interface_list(String access_token, String openid) throws Exception {
        
        ResponseMessage responseMessage = new ResponseMessage();
        
        OauthTokenDto oauthTokenDto = new OauthTokenDto();
        oauthTokenDto.setToken(access_token);
        oauthTokenDto.setOpenId(openid);
        oauthTokenDto = oauthTokenService.selectOne(oauthTokenDto);
        if (oauthTokenDto == null) {
            logger.error("invalid access_token or invalid open_id,accessToken:{},openId:{}", access_token, openid);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40032);
            responseMessage.setCode(Constants.CODE_40032);
            return responseMessage;
        }

        //Long expiresIn = Long.valueOf(Seconds.secondsBetween(new DateTime(), new DateTime(oauthTokenDto.getExpireTime())).getSeconds());
        if (oauthTokenDto.getExpireTime().before(new Date())) {
            logger.error("access_token expired:{}", access_token);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40029);
            responseMessage.setCode(Constants.CODE_40029);;
            return responseMessage;
        }
        
        if(!openid.equals(oauthTokenDto.getOpenId())) {
            logger.error("invalid openid:{}", openid);
            responseMessage.setState(Constants.STATE_FAIL);
            responseMessage.setMessage(Constants.MSG_40031);
            responseMessage.setCode(Constants.CODE_40031);
            return responseMessage;
        }
        
        OauthTransResDto oauthTransResDto = new OauthTransResDto();
        oauthTransResDto.setAppId(oauthTokenDto.getAppId());
        oauthTransResDto.setInvalidFlag(0);
        List<OauthTransResDto> oauthTransResList = oauthTransResService.findResourceInfo(oauthTransResDto);
        
        List<InterfaceInfoDto> interfaceInfoList = new ArrayList<InterfaceInfoDto>();
        InterfaceInfoDto interfaceInfoDto = null;
        for(OauthTransResDto transResDto : oauthTransResList) {
            interfaceInfoDto = new InterfaceInfoDto();
            interfaceInfoDto.setServiceName(transResDto.getInterfaceName());
            interfaceInfoDto.setServiceContext(transResDto.getRequestUrl());
            interfaceInfoDto.setRemark(transResDto.getRemark());
            interfaceInfoList.add(interfaceInfoDto);
        }
        
        responseMessage.setState(Constants.STATE_SUCCESS);
        responseMessage.setMessage(Constants.MSG_SUCCESS);
        responseMessage.setCode(Constants.CODE_SUCCESS);
        RespBodyData respBodyData = new RespBodyData();
        respBodyData.setInterface_list(interfaceInfoList);
        responseMessage.setData(respBodyData);
        
        return responseMessage;
    }

}
