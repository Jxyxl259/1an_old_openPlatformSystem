/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER对应的Controller，代码生成。
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.domain.ParameterDto;
import com.yaic.app.common.service.ParameterService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;

@Controller
@RequestMapping("/parameter")
public class ParameterController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private ParameterService parameterService;
    
    /**
     * 查询系统参数
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getParaList")
    public Map<String, Object> getParaList(@RequestBody JsonRequest<ParameterDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        
        ParameterDto parameterDto = new ParameterDto();
        //查询条件
        parameterDto.setParameterType(jsonRequest.getExtend().get("paraType"));
        List<ParameterDto> roleList = parameterService.selectOrderBy( parameterDto , "updated_date, parameter_id");
        
        auditLogger.info("查询所有系统参数" );
        logger.info("查询所有系统参数");
        
        result.put("paraList", roleList);
        return result;
    }
    
    /**
     * 新增\修改系统参数
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdaPara")
    public Map<String, Object> saveOrUpdaPara(@RequestBody JsonRequest<ParameterDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        ParameterDto parameterDto = new ParameterDto();
        
        ParameterDto pDto = new ParameterDto();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        parameterDto.setParameterType(jsonRequest.getExtend().get("parameterType"));
        parameterDto.setParameterCode(jsonRequest.getExtend().get("parameterCode"));
        parameterDto.setParameterCname(jsonRequest.getExtend().get("parameterCname"));
        parameterDto.setParameterEname(jsonRequest.getExtend().get("parameterEname"));
        parameterDto.setParameterTname(jsonRequest.getExtend().get("parameterTname"));
        parameterDto.setValidFlag(jsonRequest.getExtend().get("validFlag"));
        parameterDto.setFlag(jsonRequest.getExtend().get("flag"));
        parameterDto.setRemark(jsonRequest.getExtend().get("remark"));
        
        
        Boolean flag = true;
        if("add".equals(operType)){
            pDto.setParameterType(jsonRequest.getExtend().get("parameterType"));
        	pDto.setParameterCode(jsonRequest.getExtend().get("parameterCode"));
        	pDto = parameterService.selectOne(pDto);
        	
        	if(pDto!=null){
        		flag=false;
        		result.put("msg", "该参数代码已存在，请重新录入！");
        		return result;
        	}
        	parameterDto.setParameterId(UUID.randomUUID().toString());
            parameterDto.setUpdatedBy(userId);
            parameterDto.setCreatedBy(userId);
            parameterService.insertNotNull(parameterDto);
            
            auditLogger.info("用户【" + userId  + "】保存系统参数【" + parameterDto.getParameterType() + "】" );
            logger.info("用户【" + userId  + "】保存系统参数【" + parameterDto.getParameterType() + "】");
            
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){
            parameterDto.setParameterId(jsonRequest.getExtend().get("parameterId"));
            
            parameterDto.setUpdatedDate(new Date());
            parameterDto.setUpdatedBy(userId);
            parameterService.updateByPrimaryKeyNotNull(parameterDto);
            
            auditLogger.info("用户【" + userId + "】修改系统参数【" + parameterDto.getParameterType() + "】" );
            logger.info("用户【" + userId + "】修改系统参数【" + parameterDto.getParameterType() + "】" );
            result.put("msg", "修改成功！");
        }
        result.put("flag", flag);
        return result;
    }
    
    /**
     * 删除系统参数
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/delPara")
    public Map<String, Object> delPara(@RequestBody JsonRequest<ParameterDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        
        List<String> parameterIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        for(String parameterId : parameterIds){
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterId(parameterId);
            parameterService.deleteByPrimaryKey(parameterDto);
        }
       
        auditLogger.info("用户【" + userId  + "】删除系统参数【" + JSON.toJSONString(parameterIds) + "】");
        logger.info("用户【" + userId  + "】删除系统参数【" + JSON.toJSONString(parameterIds) + "】");
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
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "/updateStates")
    public Map<String, Object> updateStates(@RequestBody JsonRequest<ParameterDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        
        List<String> parameterIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        //批量更新变逐条更新
        for(String str : parameterIds){
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterId(str.trim());
            parameterDto.setValidFlag("0");
            parameterService.updateByPrimaryKeyNotNull(parameterDto);
        }
        
        auditLogger.info("用户【" + userId  + "】禁用参数【" + JSON.toJSONString(parameterIds) + "】");
        logger.info("用户【" + userId  + "】禁用参数【" + JSON.toJSONString(parameterIds) + "】");
        result.put("msg", "禁用参数成功！");
        return result;
    }
    
}