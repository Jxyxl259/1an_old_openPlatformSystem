/************************************************************************
 * 描述 ：数据库表CMS_CONFIG对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.domain.ConfigDto;
import com.yaic.app.common.service.ConfigService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/config")
public class ConfigController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private ConfigService configService;
    
    /**
     * 跳转到系统配置主页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manage")
    public String managePage() throws Exception {
        return "sys/config/configManage";
    }
    
    /**
     * 查询所有系统配置
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getCfgList")
    public Map<String, Object> getCfgList(@RequestBody JsonRequest<ConfigDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        
        Map<String , String> paraMap = new HashMap<String, String>();
        //查询条件
        String configCode = jsonRequest.getExtend().get("configCode");
        if(!StringUtil.isEmpty(configCode)){
            paraMap.put("configCode" , configCode);
        }
        String configValue = jsonRequest.getExtend().get("configValue");
        if(!StringUtil.isEmpty(configValue)){
            paraMap.put("configValue" , configValue);
        }
        String configName = jsonRequest.getExtend().get("configName");
        if(!StringUtil.isEmpty(configName)){
            paraMap.put("configName", "%" + configName + "%");
        }
        String validFlag = jsonRequest.getExtend().get("validFlag");
        if(!StringUtil.isEmpty(validFlag)){
            paraMap.put("validFlag" , validFlag);
        }
        
        List<ConfigDto> dataList = configService.getRecordsByConditions(paraMap);
        
        auditLogger.info("查询系统配置" );
        logger.info("查询系统配置");
        
        result.put("dataList", dataList);
        return result;
    }
    
    /**
     * 新增、修改系统配置
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdCfg")
    public Map<String, Object> saveOrUpdCfg(@RequestBody JsonRequest<ConfigDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        ConfigDto configDto = new ConfigDto();
        configDto.setConfigCode(jsonRequest.getExtend().get("configCode"));
        configDto.setConfigName(jsonRequest.getExtend().get("configName"));
        configDto.setCondition1(jsonRequest.getExtend().get("condition1"));
        configDto.setCondition2(jsonRequest.getExtend().get("condition2"));
        configDto.setCondition3(jsonRequest.getExtend().get("condition3"));
        configDto.setCondition4(jsonRequest.getExtend().get("condition4"));
        configDto.setCondition5(jsonRequest.getExtend().get("condition5"));
        configDto.setConfigValue(jsonRequest.getExtend().get("configValue"));
        configDto.setValidFlag(jsonRequest.getExtend().get("validFlag"));
        configDto.setFlag(jsonRequest.getExtend().get("flag"));
        configDto.setRemark(jsonRequest.getExtend().get("remark"));
        
        if("add".equals(operType)){
            
            configDto.setCreatedBy(userId);
            configDto.setUpdatedBy(userId);
            
            configService.insertNotNull(configDto);
            
            auditLogger.info("用户【" + userId  + "】保存系统配置【" + configDto.getConfigName() + "】" );
            logger.info("用户【" + userId  + "】保存系统配置【" + configDto.getConfigName() + "】" );
            
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){
            configDto.setConfigId(jsonRequest.getExtend().get("configId"));
            configDto.setUpdatedDate(new Date());
            configDto.setUpdatedBy(userId);
            configService.updateByPrimaryKeyNotNull(configDto);
            
            auditLogger.info("用户【" + userId + "】修改系统配置【" + configDto.getConfigName() + "】" );
            logger.info("用户【" + userId + "】修改系统配置【" + configDto.getConfigName() + "】" );
            
            result.put("msg", "修改成功！");
        }
        
        return result;
    }
    
    /**
     * 删除系统配置
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/delCfg")
    public Map<String, Object> delCfg(@RequestBody JsonRequest<ConfigDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        for(String id : ids){
            
            ConfigDto configDto = new ConfigDto();
            configDto.setConfigId(id);
            configService.deleteByPrimaryKey(configDto);
        }
       
        auditLogger.info("用户【" + userId  + "】删除系统配置【" + JSON.toJSONString(ids) + "】");
        logger.info("用户【" + userId  + "】删除系统配置【" + JSON.toJSONString(ids) + "】");
        result.put("msg", "删除成功！");
        return result;
    }
    
    /**
     * 禁用系统配置
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/updateStates")
    public Map<String, Object> updateStates(@RequestBody JsonRequest<ConfigDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        //批量更新变逐条更新
        for(String id : ids){
            ConfigDto configDto = new ConfigDto();
            configDto.setConfigId(id.trim());
            configDto.setValidFlag("0");
            configService.updateByPrimaryKeyNotNull(configDto);
        }
        
        auditLogger.info("用户【" + userId  + "】禁用系统配置【" + JSON.toJSONString(ids) + "】");
        logger.info("用户【" + userId  + "】禁用系统配置【" + JSON.toJSONString(ids) + "】");
        result.put("msg", "禁用系统配置成功！");
        return result;
    }
}