/************************************************************************
 * 描述 ：数据库表CMS_COMPANY对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
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

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yaic.app.Constants;
import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.CompanyDto;
import com.yaic.app.common.service.CompanyService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.dto.JsonResponse;
import com.yaic.fa.log.Log;


@Controller
@RequestMapping("/company")
public class CompanyController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private CompanyService companyService;
    
    Map<String, Object> map = new HashMap<String, Object>();
    
    /**
     * 机构管理页面
     * @return
     */
    @RequestMapping(value="/companyManage")
    public ModelAndView companyManager(){
    	ModelAndView model = new ModelAndView();
    	model.setViewName("sys/company/companyManage");
    	return model;
    }
    
    
    /**
     * 机构树
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCompanyListData")
	public Map<String, Object> getCompanyListData(@RequestBody JsonRequest<CompanyDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	
	        Map<String, Object> result = new HashMap<String, Object>();
	        //查询所有机构
	        List<TreeNode> companyList =  companyService.findAllCompany();
	
	        //封装树
	        List<TreeNode> copyCompanyNodes = new ArrayList<TreeNode>();
	        copyCompanyNodes.addAll(companyList); 
	        
	         //得到机构树
	        for(TreeNode treeNode : companyList){
	            for(int i = 0 ;i< copyCompanyNodes.size() ;i++ ){
	            	copyCompanyNodes.get(i).setIcon("fa fa-home fa-fw");
	                if(treeNode.getParentId().equals(copyCompanyNodes.get(i).getId())){
	                	treeNode.setText(treeNode.getId()+"-"+treeNode.getCompanyCname());
	                	copyCompanyNodes.get(i).addChild(treeNode);
	                	copyCompanyNodes.remove(treeNode);
	                }
	            }
	        }       
	        
	        List<TreeNode> companyListTree = new ArrayList<TreeNode>();
	        for(TreeNode treeNode:copyCompanyNodes){
	        	treeNode.setText(treeNode.getId()+"-"+treeNode.getCompanyCname());
	        	companyListTree.add(treeNode);
	        }
	        
	        auditLogger.info("查询所有机构" );
	        logger.info("查询所有机构");       
	        result.put("companyListTree", companyListTree);
	        return result;
	    }

    /**
     * 新增修改机构
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/addOrModifyCompany")
    public JsonResponse<CompanyDto> addOrModifyCompany(@RequestBody JsonRequest<CompanyDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	JsonResponse<CompanyDto> result = new JsonResponse<CompanyDto>();
    	CompanyDto companyDto = new CompanyDto();
    	CompanyDto company = new CompanyDto();
    	String msg = "";
    	String flag="";
   
    	String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
    	String operType =jsonRequest.getExtend().get("operType");
    	companyDto.setCompanyId(jsonRequest.getForm().getCompanyId());
    	companyDto.setCompanyCname(jsonRequest.getForm().getCompanyCname());
    	companyDto.setCompanyLevel(jsonRequest.getForm().getCompanyLevel());
    	companyDto.setParentCompanyId(jsonRequest.getForm().getParentCompanyId());
    	companyDto.setValidFlag(jsonRequest.getForm().getValidFlag());
    	companyDto.setRemark(jsonRequest.getForm().getRemark());
    	companyDto.setCompanyEname(jsonRequest.getForm().getCompanyEname());
    	companyDto.setCompanyTname(jsonRequest.getForm().getCompanyTname());

    	
    	if("add".equals(operType)){
    		company.setCompanyId(jsonRequest.getForm().getCompanyId());
    		company= companyService.selectByPrimaryKey(company);
    		if(company !=null){
                flag = "1";
    			msg="该机构编号已存在！";
    	    	map.put("msg", msg);
    	    	map.put("flag",flag );
    	    	result.setExtend(map);
    	        return result;
    		}else{
    			flag = "0";
    	  		operType="增加";
    	  		companyDto.setCreatedBy(userId);
    	  		companyDto.setUpdatedBy(userId);
            	int i =companyService.saveCompany(companyDto);
            	if(i>0){
            		msg="新增成功";
            	}else{
            		msg="新增失败";
            	}
    		}
    		
    	}else{
    		flag = "0";
    		operType="修改";
    		companyDto.setUpdatedBy(userId);
    		int i= companyService.updateByPrimaryKeyNotNull(companyDto);
    		if(i>0){
    			msg="修改成功！";
    		}else{
    			msg="修改失败！";
    		}
    		
    	}
    	
    	
   	    auditLogger.info("用户【" + userId  + "】" + operType + "机构【" + jsonRequest.getForm().getCompanyId() + "_" + jsonRequest.getForm().getCompanyCname()  + "】");
        logger.info("用户【" + userId  + "】" + operType + "机构【" + jsonRequest.getForm().getCompanyId() + "_" + jsonRequest.getForm().getCompanyCname()  + "】");
    	map.put("msg", msg);
    	map.put("flag",flag );
    	result.setExtend(map);
        return result;
    }
    
    /**
     * 根据resourceId删除机构
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCompany")
    public JsonResponse<CompanyDto> deleteCompany(@RequestBody JsonRequest<CompanyDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	JsonResponse<CompanyDto> result = new JsonResponse<CompanyDto>();
    	CompanyDto companyDto = new CompanyDto();
    	String msg = "";
    	
    	String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
    	String companyId=jsonRequest.getExtend().get("companyId");
    	companyDto.setCompanyId(companyId);
        int i= companyService.deleteByPrimaryKey(companyDto);
        if(i>0){
        	msg="删除成功！";
        }else{
        	msg="删除失败！";
        }
        
        auditLogger.info("用户【" + userId  + "】删除机构【" + companyId + "】");
        logger.info("用户【" + userId  + "】删除机构【"  + companyId + "】");
    	map.put("msg", msg);
    	result.setExtend(map);
        return result;
    }
    
    /**
     * 自动生成机构ID
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createCompanyId")
    public JsonResponse<CompanyDto> createCompanyId(@RequestBody JsonRequest<CompanyDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JsonResponse<CompanyDto> result = new JsonResponse<CompanyDto>();
    	List<CompanyDto> resultList = companyService.findMaxCompanyId();
    	String companyId="";
    	if(resultList!=null && resultList.get(0)!=null &&  resultList.size()>0){
    		String id = resultList.get(0).getCompanyId();
        	char[] ids = id.toCharArray();
        	int index =5;
        	  
        	for(int i=0;i<ids.length;i++){
        		int j = Integer.parseInt(String.valueOf(ids[i]));
        		if(j!=0){
        			index=i;
        			break;
        		}
        	}
        	String id1=id.substring(index);
        	String id2=id.substring(0,index);
        	companyId= String.valueOf(Integer.parseInt(id1)+1);
        	if(companyId.length()>id1.length()){
        		for(int i=0;i<id.length()-id1.length();i++){
        			companyId ="0"+companyId;
        		}
        		
        	}else{
        		companyId =id2 +companyId;
        	}
    	}else{
    		companyId="000000";
    	}
    	
    	
   	    auditLogger.info("自动生成机构编号");
        logger.info("自动生成机构编号");
    	map.put("companyId",companyId );
    	result.setExtend(map);
        return result;
    }
    
    /**
     * 查询机构名称ByParentId
     * @param jsonRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/findParentCompanyName")
    public JsonResponse<CompanyDto> findParentCompanyName(@RequestBody JsonRequest<CompanyDto> jsonRequest, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	JsonResponse<CompanyDto> result = new JsonResponse<CompanyDto>();
    	CompanyDto companyDto = new CompanyDto();
    	
    	companyDto.setCompanyId(jsonRequest.getExtend().get("parentId"));
    	companyDto =companyService.selectByPrimaryKey(companyDto);
    	String parentCompanyName = "";
        if(companyDto !=null){
        	parentCompanyName = companyDto.getCompanyCname();
        }
   	    auditLogger.info("查询上级机构名称");
        logger.info("查询上级机构名称");
    	map.put("parentCompanyName",parentCompanyName );
    	result.setExtend(map);
        return result;
    }
    
    /**
     * 组织自动补全文本框  数据获取
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAcDataList")
    public Map<String, Object> getAcDataList(@RequestBody JsonRequest<CompanyDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        String localLanguage = "C" ;
        List<AutoCompleteDto> dataList = companyService.getAcDataList(localLanguage);
        
        auditLogger.info("查询机构" );
        logger.info("查询机构");
        
        result.put("dataList", dataList);
        return result;
    }
    
}