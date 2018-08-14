/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER_TYPE对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.domain.ParameterDto;
import com.yaic.app.common.dto.domain.ParameterTypeDto;
import com.yaic.app.common.service.ParameterService;
import com.yaic.app.common.service.ParameterTypeService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.fa.mybatis.mapper.entity.Condition;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/parameterType")
public class ParameterTypeController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private ParameterTypeService parameterTypeService;
    
    @Autowired
    private ParameterService parameterService;
    
    /**
     * 跳转到系统参数设置主页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manage")
    public String managerPage(ModelMap modelMap) throws Exception {
        
        Map< String, String > paraMap = new HashMap<String, String>();
        paraMap.put("localLanguage", "C");
        
        List<AutoCompleteDto> companyList = parameterTypeService.getAcDataList(paraMap);
        auditLogger.info("查询全部参数类型" );
        logger.info("查询全部参数类型");
        modelMap.put("parameterTypeList", JSON.toJSONString(companyList));
        
        return "sys/para/paraManage";
    }
    
    /**
     * 获取系统参数类型
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getParaTypeList")
    public Map<String, Object> getParaTypeList(@RequestBody JsonRequest<ParameterTypeDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String , String> paraMap = new HashMap<String, String>();
        //查询条件
        String parameterType = jsonRequest.getExtend().get("parameterType");
        if(!StringUtil.isEmpty(parameterType)){
            paraMap.put("parameterType" , parameterType.toUpperCase());
        }
        String validFlag = jsonRequest.getExtend().get("validFlag");
        if(!StringUtil.isEmpty(validFlag)){
            paraMap.put("validFlag" , validFlag);
        }
        String parameterCname = jsonRequest.getExtend().get("parameterCname");
        if(!StringUtil.isEmpty(parameterCname)){
            paraMap.put("parameterCname", "%" + parameterCname + "%");
        }
        String parameterEname = jsonRequest.getExtend().get("parameterEname");
        if(!StringUtil.isEmpty(parameterEname)){
            paraMap.put("parameterEname", "%" + parameterEname + "%");
        }
        String parameterTname = jsonRequest.getExtend().get("parameterTname");
        if(!StringUtil.isEmpty(parameterTname)){
            paraMap.put("parameterTname","%" + parameterTname + "%");
        }
        
        List<ParameterTypeDto> parameterTypeList = parameterTypeService.getRecordsByType(paraMap);
        
        auditLogger.info("查询所有系统参数类型" );
        logger.info("查询所有系统参数类型");
        
        result.put("parameterTypeList", parameterTypeList);
        return result;
    }
    
    /**
     * 新增\修改系统参数类型
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdParaType")
    public Map<String, Object> saveOrUpdParaType(@RequestBody JsonRequest<ParameterTypeDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        ParameterTypeDto parameterTypeDto = new ParameterTypeDto();
        parameterTypeDto.setParameterType(jsonRequest.getExtend().get("parameterType"));
        parameterTypeDto.setParameterTypeCname(jsonRequest.getExtend().get("parameterCname"));
        parameterTypeDto.setParameterTypeEname(jsonRequest.getExtend().get("parameterEname"));
        parameterTypeDto.setParameterTypeTname(jsonRequest.getExtend().get("parameterTname"));
        parameterTypeDto.setValidFlag(jsonRequest.getExtend().get("validFlag"));
        parameterTypeDto.setFlag(jsonRequest.getExtend().get("flag"));
        parameterTypeDto.setRemark(jsonRequest.getExtend().get("remark"));
        
        if("add".equals(operType)){
            parameterTypeDto.setParameterTypeId(UUID.randomUUID().toString());
            parameterTypeDto.setCreatedBy(userId);
            parameterTypeDto.setUpdatedBy(userId);
            
            parameterTypeService.insertNotNull(parameterTypeDto);
            
            auditLogger.info("用户【" + userId  + "】保存系统参数类型【" + parameterTypeDto.getParameterType() + "】" );
            logger.info("用户【" + userId  + "】保存系统参数类型【" + parameterTypeDto.getParameterType() + "】" );
            
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){
            parameterTypeDto.setParameterTypeId(jsonRequest.getExtend().get("parameterTypeId"));
            
            parameterTypeDto.setUpdatedDate(new Date());
            parameterTypeDto.setUpdatedBy(userId);
            //同步更改参数表
            ParameterTypeDto tmpParameterTypeDto = parameterTypeService.selectByPrimaryKey(parameterTypeDto);
            if(tmpParameterTypeDto != null && !tmpParameterTypeDto.getParameterType().equals(parameterTypeDto.getParameterType())){
                
                Condition condition = new Condition(ParameterDto.class);
                ParameterDto parameterDto = new ParameterDto();
                parameterDto.setParameterType(parameterTypeDto.getParameterType());
                parameterDto.setUpdatedDate(new Date());
                parameterDto.setUpdatedBy(userId);
                condition.createCriteria().andEqualTo("parameterType", tmpParameterTypeDto.getParameterType());
                parameterService.updateByConditionNotNull(parameterDto, condition);
            }
            parameterTypeService.updateByPrimaryKeyNotNull(parameterTypeDto);
            
            auditLogger.info("用户【" + userId + "】修改系统参数类型【" + parameterTypeDto.getParameterType() + "】" );
            logger.info("用户【" + userId + "】修改系统参数类型【" + parameterTypeDto.getParameterType() + "】" );
            
            result.put("msg", "修改成功！");
        }
        
        return result;
    }
    
    /**
     * 删除系统参数类型
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/delParaType")
    public Map<String, Object> delParaType(@RequestBody JsonRequest<ParameterTypeDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> parameterTypeIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        for(String parameterTypeId : parameterTypeIds){
            
            
            ParameterTypeDto parameterTypeDto = new ParameterTypeDto();
            parameterTypeDto.setParameterTypeId(parameterTypeId);
            parameterTypeDto = parameterTypeService.selectByPrimaryKey(parameterTypeDto);
            
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterType(parameterTypeDto.getParameterType());
            parameterService.delete(parameterDto);
            
            parameterTypeService.deleteByPrimaryKey(parameterTypeDto);
        }
       
        auditLogger.info("用户【" + userId  + "】删除系统参数类型【" + JSON.toJSONString(parameterTypeIds) + "】");
        logger.info("用户【" + userId  + "】删除系统参数类型【" + JSON.toJSONString(parameterTypeIds) + "】");
        result.put("msg", "删除成功！");
        return result;
    }
    
    /**
     * 更新状态
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateStates")
    public Map<String, Object> updateStates(@RequestBody JsonRequest<ParameterTypeDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> parameterTypeIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        //批量更新变逐条更新
        for(String str : parameterTypeIds){
            ParameterTypeDto parameterTypeDto = new ParameterTypeDto();
            parameterTypeDto.setParameterTypeId(str.trim());
            parameterTypeDto.setValidFlag("0");
            parameterTypeService.updateByPrimaryKeyNotNull(parameterTypeDto);
        }
        
        auditLogger.info("用户【" + userId  + "】禁用系统参数类型【" + JSON.toJSONString(parameterTypeIds) + "】");
        logger.info("用户【" + userId  + "】禁用系统参数类型【" + JSON.toJSONString(parameterTypeIds) + "】");
        result.put("msg", "禁用系统参数类型成功！");
        return result;
    }
    
}