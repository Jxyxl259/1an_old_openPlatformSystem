/************************************************************************
 * 描述 ：数据库表CMS_RESOURCE对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-10-22 09:44:48
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yaic.app.Constants;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.ResourceDto;
import com.yaic.app.common.service.ResourceService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.dto.JsonResponse;
import com.yaic.fa.log.Log;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Log("auditLogger")
	private Logger auditLogger;
    @Log
    private Logger logger;

    @Autowired
    private ResourceService resourceService;
    
    Map<String, Object> map = new HashMap<String, Object>();
    
    @ResponseBody
    @RequestMapping(value = "/menu")
    public HashMap<String, Object> menu(HttpServletRequest request) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();
        String userId = "" ;
        if(subject.isAuthenticated() || subject.isRemembered()){
            userId = (String) subject.getPrincipal();
        }
        if(!StringUtil.isEmpty(userId)){
            String menu = resourceService.getMenu(userId);
            if (logger.isDebugEnabled()) {
                logger.debug("system menu:" + menu);
            }
            result.put("menu", menu);
        }
        return result;
    }
    
    
    

    /**
     * 资源管理页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resourceManage")
    public ModelAndView resourceManager(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sys/resource/resourceManage");
        return modelAndView;
    }
    
    /**
     * 资源树
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getResourceListData")
    public Map<String, Object> getResourceListData(@RequestBody JsonRequest<ResourceDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        //查询所有资源
        List<TreeNode> menuResourceList =  resourceService.findAllMenuResource();
        List<TreeNode> serviceResourceList =  resourceService.findAllServiceResource();
        
        //封装树
        List<TreeNode> copyMenuNodes = new ArrayList<TreeNode>();
        List<TreeNode> copyServiceNodes = new ArrayList<TreeNode>();
        copyMenuNodes.addAll(menuResourceList); 
        copyServiceNodes.addAll(serviceResourceList);
        
        //得到菜单树
        for(TreeNode treeNode : menuResourceList){
            for(int i = 0 ;i<copyMenuNodes.size() ;i++ ){
                if(treeNode.getParentId().equals(copyMenuNodes.get(i).getId())){
                	copyMenuNodes.get(i).addChild(treeNode);
                	copyMenuNodes.remove(treeNode);
                }
               
            }
        }       
        
        //得到服务树
        for(TreeNode treeNode : serviceResourceList){
            for(int i = 0 ;i<copyServiceNodes.size() ;i++ ){
                if(treeNode.getParentId().equals(copyServiceNodes.get(i).getId())){
                	copyServiceNodes.get(i).addChild(treeNode);
                	copyServiceNodes.remove(treeNode);
                }
               
            }
        }      
        auditLogger.info("查询所有资源" );
        logger.info("查询所有资源");       
        result.put("menuResourceTree", copyMenuNodes);
        result.put("serviceResourceTree", copyServiceNodes);
        return result;
    }

    /**
     * 根据resourceId删除资源
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteResource")
    public JsonResponse<ResourceDto> deleteMenu(@RequestBody JsonRequest<ResourceDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	JsonResponse<ResourceDto> result = new JsonResponse<ResourceDto>();
    	ResourceDto resourceDto = new ResourceDto();
    	String msg = "";
    	
    	String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
    	String resourceId=jsonRequest.getExtend().get("resourceId");
        resourceDto.setResourceId(resourceId);
        int i=resourceService.deleteByPrimaryKey(resourceDto);
        if(i>0){
        	msg="删除成功！";
        }else{
        	msg="删除失败！";
        }
        
        auditLogger.info("用户【" + userId  + "】删除资源【" + resourceId + "】");
        logger.info("用户【" + userId  + "】删除资源【"  + resourceId + "】");
    	map.put("msg", msg);
    	result.setExtend(map);
        return result;
    }
    
    
    

    
    /**
     * 新增修改资源
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/addOrModifyMenu")
    public JsonResponse<ResourceDto> addOrModifyMenu(@RequestBody JsonRequest<ResourceDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	JsonResponse<ResourceDto> result = new JsonResponse<ResourceDto>();
    	ResourceDto resourceDto = new ResourceDto();
    	ResourceDto resource = new ResourceDto();
    	String msg = "";
    	String flag="";
    	
    	String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
    	String operType =jsonRequest.getExtend().get("operType");
    	resourceDto.setResourceId(jsonRequest.getForm().getResourceId());
    	resourceDto.setResourceName(jsonRequest.getForm().getResourceName());
    	resourceDto.setResourceType(jsonRequest.getForm().getResourceType());
    	resourceDto.setResourceLevel(jsonRequest.getForm().getResourceLevel());
    	resourceDto.setParentResourceId(jsonRequest.getForm().getParentResourceId());
    	resourceDto.setUpdatedBy(userId);
    	resourceDto.setActionUrl(jsonRequest.getForm().getActionUrl());
    	resourceDto.setEndFlag(jsonRequest.getForm().getEndFlag());
    	resourceDto.setResourceIconClass(jsonRequest.getForm().getResourceIconClass());
    	resourceDto.setDisplayOrder(jsonRequest.getForm().getDisplayOrder());
    	if("add".equals(operType)){
    		resource.setResourceId(jsonRequest.getForm().getResourceId());
    		resource=resourceService.selectByPrimaryKey(resourceDto);
    		if(resource !=null){
                flag = "1";
    			msg="该资源编号已存在！";
    	    	map.put("msg", msg);
    	    	map.put("flag",flag );
    	    	result.setExtend(map);
    	        return result;
    		}else{
    			flag = "0";
    	  		operType="增加";
            	resourceDto.setCreatedBy(userId);
            	resourceDto.setUpdatedBy(userId);
            	int i =resourceService.saveResource(resourceDto);
            	if(i>0){
            		msg="新增成功";
            	}else{
            		msg="新增失败";
            	}
    		}
    		
    	}else{
    		flag = "0";
    		operType="修改";
    		resourceDto.setUpdatedBy(userId);
    		int i= resourceService.updateByPrimaryKeyNotNull(resourceDto);
    		if(i>0){
    			msg="修改成功！";
    		}else{
    			msg="修改失败！";
    		}
    	}
    	
   	    auditLogger.info("用户【" + userId  + "】" + operType + "资源【" + jsonRequest.getForm().getResourceId() + "_" + jsonRequest.getForm().getResourceName()  + "】");
        logger.info("用户【" + userId  + "】" + operType + "资源【" + jsonRequest.getForm().getResourceId() + "_" + jsonRequest.getForm().getResourceName()  + "】");
    	map.put("msg", msg);
    	map.put("flag",flag );
    	result.setExtend(map);
        return result;
    }
    
    /**
     * 自动生成资源ID
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createResourceId")
    public JsonResponse<ResourceDto> createRsourceId(@RequestBody JsonRequest<ResourceDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JsonResponse<ResourceDto> result = new JsonResponse<ResourceDto>();
    	List<ResourceDto> resultList = resourceService.findMaxResourceId();
    	String resourceId="";
    	if(resultList!=null && resultList.get(0)!=null &&  resultList.size()>0){
    		String id = resultList.get(0).getResourceId();
        	
        	char[] ids = id.toCharArray();
        	int index =3;
        	
        	for(int i=0;i<ids.length;i++){
        		int j = Integer.parseInt(String.valueOf(ids[i]));
        		if(j!=0){
        			index=i;
        			break;
        		}
        	}
        	String id1=id.substring(index);
        	String id2=id.substring(0,index);
        	resourceId= String.valueOf(Integer.parseInt(id1)+1);
        	if(resourceId.length()>id1.length()){
        		for(int i=0;i<id.length()-id1.length();i++){
        			resourceId ="0"+resourceId;
        		}
        		
        	}else{
        		resourceId =id2 +resourceId;
        	}
    	}else{
    		resourceId = "0000";
    	}
    	
    	
   	    auditLogger.info("自动生成机构编号");
        logger.info("自动生成机构编号");
    	map.put("resourceId",resourceId );
    	result.setExtend(map);
        return result;
    }
    
    
    /**
     * 自动生成资源ID
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/findParentResourceName")
    public JsonResponse<ResourceDto> findParentResourceName(@RequestBody JsonRequest<ResourceDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JsonResponse<ResourceDto> result = new JsonResponse<ResourceDto>();
    	ResourceDto resourceDto = new ResourceDto();
    	resourceDto.setResourceId(jsonRequest.getExtend().get("parentId"));
    	resourceDto =resourceService.selectByPrimaryKey(resourceDto);
    	String parentResourceName ="";
        if(resourceDto!=null){
        	parentResourceName =resourceDto.getResourceName();
        }
   	    auditLogger.info("查询父类资源名称");
        logger.info("查询父类资源名称");
    	map.put("parentResourceName",parentResourceName );
    	result.setExtend(map);
        return result;
    }
    
    
}