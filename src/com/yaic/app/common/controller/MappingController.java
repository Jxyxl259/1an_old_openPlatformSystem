/************************************************************************
 * 描述 ：数据库表CMS_MAPPING对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
 *
 ************************************************************************/

package com.yaic.app.common.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.domain.MappingDto;
import com.yaic.app.common.service.MappingService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping(value="/mapping")
public class MappingController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private MappingService mappingService;
    
    /**
     * 代码转换页面
     * @return
     */
    @RequestMapping(value="/mappingManage")
    public ModelAndView mappingManage(){
    	ModelAndView model = new ModelAndView();
    	model.setViewName("sys/mapping/mappingManage");
    	return model;
    }
    
    /**
     * 查询代码转换List
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getMappingList")
    public Map<String, Object> getMappingList(@RequestBody JsonRequest<MappingDto> jsonRequest) throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	
        Map<String, String> paraMap = new HashMap<String, String>();
        String mappingName=jsonRequest.getExtend().get("mappingName");
        String mappingFrom=jsonRequest.getExtend().get("mappingFrom");
        String mappingTo=jsonRequest.getExtend().get("mappingTo");
        String mappingType =jsonRequest.getExtend().get("mappingType");
        String validFlag =jsonRequest.getExtend().get("validFlag");
        if(StringUtil.isNotEmpty(mappingType)){
        	paraMap.put("mappingType", mappingType);
        }
        if(StringUtil.isNotEmpty(mappingName)){
        	 paraMap.put("mappingName", mappingName);
        }
        if(StringUtil.isNotEmpty(mappingFrom)){
        	 paraMap.put("mappingFrom", mappingFrom);
       }
        if(StringUtil.isNotEmpty(mappingTo)){
        	 paraMap.put("mappingTo", mappingTo);
       }
        if(StringUtil.isNotEmpty(validFlag)){
       	 paraMap.put("validFlag", validFlag);
       }
        
        List<MappingDto> dataList = mappingService.findByMappingName(paraMap);
       // List<MappingDto> dataList = mappingService.selectOrderBy(new MappingDto(), "updated_date, mapping_id");
        
        auditLogger.info("查询代码转换" );
        logger.info("查询代码转换");
        
        result.put("dataList", dataList);
        return result;
    }
    
    /**
     * 新增、修改代码转换
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateMapping")
    public Map<String, Object> saveOrUpdateMapping(@RequestBody JsonRequest<MappingDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        MappingDto mappingDto = new MappingDto();
        
        String operType = jsonRequest.getExtend().get("operType");
        mappingDto.setMappingName(jsonRequest.getForm().getMappingName());
        mappingDto.setMappingType(jsonRequest.getForm().getMappingType());
        mappingDto.setMappingFrom(jsonRequest.getForm().getMappingFrom());
        mappingDto.setMappingTo(jsonRequest.getForm().getMappingTo());
        mappingDto.setValidFlag(jsonRequest.getForm().getValidFlag());
        mappingDto.setRemark(jsonRequest.getForm().getRemark());
        if("add".equals(operType)){
        	MappingDto mapping = new MappingDto();
        	mapping.setMappingFrom(jsonRequest.getForm().getMappingFrom());
        	mapping=mappingService.selectOne(mapping);
        	if(mapping==null){
            	mappingDto.setCreatedBy(userId);
            	mappingDto.setUpdatedBy(userId);
                mappingService.insertNotNull(mappingDto);
                auditLogger.info("用户【" + userId  + "】新增代码【" + jsonRequest.getForm().getMappingName() + "】" );
                logger.info("用户【" + userId  + "】新增代码【" + jsonRequest.getForm().getMappingName() + "】" );
                result.put("flag", true);
                result.put("msg", "新增成功！");
        	}else{
        		result.put("flag", false);
        		result.put("msg", "代码源值已存在,请重新录入！");
        	}
        }else if("edit".equals(operType)){
        	mappingDto.setMappingId(jsonRequest.getForm().getMappingId());
        	mappingService.updateByPrimaryKeyNotNull(mappingDto);
            auditLogger.info("用户【" + userId + "】修改代码转换【" + jsonRequest.getForm().getMappingName() + "】" );
            logger.info("用户【" + userId + "】修改代码转换【" + jsonRequest.getForm().getMappingName() + "】" );
            result.put("flag", true);
            result.put("msg", "修改成功！");
        }
        
        return result;
    }
    
    /**
     * 删除代码转换
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/deleteMapping")
    public Map<String, Object> deleteMapping(@RequestBody JsonRequest<MappingDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        MappingDto mappingDto = new MappingDto();
        for(String id : ids){
        	mappingDto.setMappingId(id);
            mappingService.deleteByPrimaryKey(mappingDto);
        }
       
        auditLogger.info("用户【" + userId  + "】删除代码【" + JSON.toJSONString(ids) + "】");
        logger.info("用户【" + userId  + "】删除代码【" + JSON.toJSONString(ids) + "】");
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
    @RequestMapping(value = "/updateValidFlag")
    public Map<String, Object> updateStates(@RequestBody JsonRequest<MappingDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        List<String> datas = (List<String>) JSON.parse(jsonRequest.getExtend().get("datas"));
         
        for(String data : datas){
        	MappingDto mappingDto = new MappingDto();
        	String[] str= data.split(",");
        	mappingDto.setMappingId(str[0]);
        	if("1".equals(str[1])){
        		mappingDto.setValidFlag("0");
                auditLogger.info("用户【" + userId  + "】禁用【" + str[0] + "】");
                logger.info("用户【" + userId  + "】禁用【" + str[0] + "】");
        	}else{
        		mappingDto.setValidFlag("1");
                auditLogger.info("用户【" + userId  + "】启用【" + str[0] + "】");
                logger.info("用户【" + userId  + "】启用【" + str[0] + "】");
        	}
            mappingService.updateByPrimaryKeyNotNull(mappingDto);
        }
        result.put("msg", "操作成功！");
        return result;
    }
    
    
    /**
     * 查询条件查询
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/mappingSearch")
    public Map<String, Object> mappingSearch(@RequestBody JsonRequest<MappingDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> paraMap = new HashMap<String, String>();
        String mappingName=jsonRequest.getExtend().get("mappingName");
        String mappingFrom=jsonRequest.getExtend().get("mappingFrom");
        String mappingTo=jsonRequest.getExtend().get("mappingTo");
        String mappingType =jsonRequest.getExtend().get("mappingType");
        String validFlag =jsonRequest.getExtend().get("validFlag");
        if(StringUtil.isNotEmpty(mappingType)){
        	paraMap.put("mappingType", mappingType);
        }
        if(StringUtil.isNotEmpty(mappingName)){
        	 paraMap.put("mappingName", mappingName);
        }
        if(StringUtil.isNotEmpty(mappingFrom)){
        	 paraMap.put("mappingFrom", mappingFrom);
       }
        if(StringUtil.isNotEmpty(mappingTo)){
        	 paraMap.put("mappingTo", mappingTo);
       }
        if(StringUtil.isNotEmpty(validFlag)){
       	 paraMap.put("validFlag", validFlag);
       }
        List<MappingDto> dataList = mappingService.findByMappingName(paraMap);
       
        result.put("dataList", dataList);
      
        return result;
    }
}