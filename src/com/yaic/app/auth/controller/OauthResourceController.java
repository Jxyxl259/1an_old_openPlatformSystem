/*
 * Created By lujicong (2016-04-27 16:36:56)
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.auth.service.OauthResourceService;
import com.yaic.app.auth.dto.domain.OauthResourceDto;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.mybatis.mapper.entity.Condition;

@Controller
@RequestMapping("/oauthResource")
public class OauthResourceController {
    

    @Autowired
    private OauthResourceService oauthResourceService;
    
    @ResponseBody
    @RequestMapping(value = "/getOauthResourceList")
    public Map<String, Object> getOauthResourceList(@RequestBody JsonRequest<OauthResourceDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        
        OauthResourceDto oauthResourceDto = new OauthResourceDto();
        oauthResourceDto.setAppId(jsonRequest.getExtend().get("appId"));
        oauthResourceDto.setInvalidFlag(0);
        List<OauthResourceDto> oauthResourceList = oauthResourceService.findResourceInfo(oauthResourceDto);
        
        result.put("oauthResourceList", oauthResourceList);
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
    public Map<String, Object> saveOrUpdParaType(@RequestBody JsonRequest<OauthResourceDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        OauthResourceDto oauthResourceDto = new OauthResourceDto();
        oauthResourceDto.setAppId(jsonRequest.getExtend().get("appId"));
        oauthResourceDto.setResourceId(Integer.parseInt(jsonRequest.getExtend().get("resourceId")));
        oauthResourceDto.setInvalidFlag(Integer.parseInt(jsonRequest.getExtend().get("invalidFlag")));
        oauthResourceDto.setUpdatedUser(userId);
        oauthResourceDto.setUpdatedDate(new Date());
        
        if("add".equals(operType)){
            OauthResourceDto checkResource = new OauthResourceDto();
            checkResource.setAppId(oauthResourceDto.getAppId());
            checkResource.setResourceId(oauthResourceDto.getResourceId());
            checkResource.setInvalidFlag(0);
            if(oauthResourceService.count(checkResource) > 0) {
                result.put("flag", false);
                result.put("msg", "已经存在该资源！");
                return result;
            }
            
            oauthResourceDto.setCreatedUser(userId);
            oauthResourceDto.setCreatedDate(new Date());
            oauthResourceService.insertNotNull(oauthResourceDto);
            result.put("flag", true);
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){    
            
            oauthResourceDto.setId(Integer.parseInt(jsonRequest.getExtend().get("id")));
            
            Condition condition = new Condition(OauthResourceDto.class);
            condition.createCriteria().andEqualTo("appId", oauthResourceDto.getAppId())
                                      .andNotEqualTo("id", oauthResourceDto.getId())
                                      .andEqualTo("resourceId", oauthResourceDto.getResourceId())
                                      .andEqualTo("invalidFlag", 0);
            if(oauthResourceService.count(condition) > 0) {
                result.put("flag", false);
                result.put("msg", "已经存在该资源！");
                return result;
            }
            
            
            oauthResourceService.updateByPrimaryKeyNotNull(oauthResourceDto);
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
    @RequestMapping(value = "/delPara")
    public Map<String, Object> delParaType(@RequestBody JsonRequest<OauthResourceDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        
        OauthResourceDto oauthResourceDto = null;
        for(String id : ids){
            oauthResourceDto = new OauthResourceDto();
            oauthResourceDto.setId(Integer.parseInt(id));
            oauthResourceDto.setInvalidFlag(1);
            oauthResourceDto.setUpdatedUser(userId);
            oauthResourceDto.setUpdatedDate(new Date());
            oauthResourceService.updateByPrimaryKeyNotNull(oauthResourceDto);
        }
       
        result.put("msg", "删除成功！");
        return result;
    }
}
