/*
 * Created By lujicong (2016-04-27 09:47:16)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.app.auth.dto.domain.OauthUserDto;
import com.yaic.app.auth.service.OauthTransResService;
import com.yaic.app.auth.service.OauthUserService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.mybatis.mapper.entity.Condition;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/auth")
public class OauthUserController {
    
    @Autowired
    private OauthUserService oauthUserService;
    
    @Autowired
    private OauthTransResService oauthTransResService;
    
    /**
     * 跳转到设置主页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manage")
    public String managerPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
        
        OauthTransResDto oauthTransResDto = new OauthTransResDto();
        oauthTransResDto.setInvalidFlag(0);
        List<OauthTransResDto> oauthTransResList = oauthTransResService.select(oauthTransResDto);
        request.setAttribute("oauthTransResList", oauthTransResList);
        
        return "auth/user/userManage";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getOauthUserList")
    public Map<String, Object> getOauthUserList(@RequestBody JsonRequest<OauthUserDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
                
        Condition condition = new Condition(OauthUserDto.class);
        String appId = jsonRequest.getExtend().get("appId");
        if (!StringUtil.isEmpty(appId)) {
            condition.createCriteria().andLike("appId", "%"+appId+"%");
        }
        String appSecret = jsonRequest.getExtend().get("appSecret");
        if (!StringUtil.isEmpty(appSecret)) {
            condition.createCriteria().andLike("appSecret", "%"+appSecret+"%");
        }
        String invalidFlag = jsonRequest.getExtend().get("invalidFlag");
        if (!StringUtil.isEmpty(invalidFlag)) {
            condition.createCriteria().andEqualTo("invalidFlag", Integer.parseInt(invalidFlag));
        }
        String userName = jsonRequest.getExtend().get("userName");
        if (!StringUtil.isEmpty(userName)) {
            condition.createCriteria().andLike("userName", "%"+userName+"%");
        }
        String phoneNumber = jsonRequest.getExtend().get("phoneNumber");
        if (!StringUtil.isEmpty(phoneNumber)) {
            condition.createCriteria().andLike("phoneNumber", "%"+phoneNumber+"%");
        }
        List<OauthUserDto> dataList = oauthUserService.selectByCondition(condition);
        result.put("dataList", dataList);
        return result;
    }
    
    /**
     * 新增\修改
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdParaType")
    public Map<String, Object> saveOrUpdParaType(@RequestBody JsonRequest<OauthUserDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        OauthUserDto oauthUserDto = new OauthUserDto();
        oauthUserDto.setAppId(jsonRequest.getExtend().get("appId"));
        oauthUserDto.setAppSecret(jsonRequest.getExtend().get("appSecret"));
        oauthUserDto.setUserName(jsonRequest.getExtend().get("userName"));
        oauthUserDto.setPhoneNumber(jsonRequest.getExtend().get("phoneNumber"));
        oauthUserDto.setCallbackUrl(jsonRequest.getExtend().get("callbackUrl"));
        oauthUserDto.setInvalidFlag(Integer.parseInt(jsonRequest.getExtend().get("invalidFlag")));
        oauthUserDto.setUpdatedUser(userId);
        oauthUserDto.setUpdatedDate(new Date());
        
        if("add".equals(operType)){
            OauthUserDto checkOauthUser = new OauthUserDto();
            checkOauthUser.setAppId(jsonRequest.getExtend().get("appId"));
            if(oauthUserService.count(checkOauthUser) > 0) {
                result.put("flag", false);
                result.put("msg", "已经存在该appId！");
                return result;
            }
            oauthUserDto.setCreatedUser(userId);
            oauthUserDto.setCreatedDate(new Date());
            oauthUserService.insertNotNull(oauthUserDto);
            result.put("flag", true);
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){    
            oauthUserService.updateByPrimaryKeyNotNull(oauthUserDto);
            result.put("flag", true);
            result.put("msg", "修改成功！");
        }
        
        return result;
    }
    
    /**
     * 删除
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/delParaType")
    public Map<String, Object> delParaType(@RequestBody JsonRequest<OauthUserDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> appIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("appIds"));
        
        OauthUserDto oauthUserDto = null;
        for(String appId : appIds){
            oauthUserDto = new OauthUserDto();
            oauthUserDto.setAppId(appId);
            oauthUserDto.setInvalidFlag(1);
            oauthUserDto.setUpdatedUser(userId);
            oauthUserDto.setUpdatedDate(new Date());
            oauthUserService.updateByPrimaryKeyNotNull(oauthUserDto);
        }
       
        result.put("msg", "删除成功！");
        return result;
    }
}
