/************************************************************************
 * 描述 ：数据库表CMS_ROLE对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.ArrayList;
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
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.RoleDto;
import com.yaic.app.common.service.RoleService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Log("auditLogger")
	private Logger auditLogger;
	
	@Log
	private Logger logger;

    @Autowired
    private RoleService roleService;
    
    /**
     * 角色管理首页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manage")
    public String toListPage() throws Exception {
        return "sys/role/roleManage";
    }
    
    /**
     * 获取角色管理列表数据，包括失效的角色
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleListData")
    public Map<String, Object> getRoleListData(@RequestBody JsonRequest<RoleDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        
        Map<String , String> paraMap = new HashMap<String, String>();
        //查询条件
        String roleId = jsonRequest.getExtend().get("roleId");
        if(!StringUtil.isEmpty(roleId)){
            paraMap.put("roleId" , roleId);
        }
        String roleName = jsonRequest.getExtend().get("roleName");
        if(!StringUtil.isEmpty(roleName)){
            paraMap.put("roleName", "%" + roleName + "%");
        }
        String validFlag = jsonRequest.getExtend().get("validFlag");
        if(!StringUtil.isEmpty(validFlag)){
            paraMap.put("validFlag" , validFlag);
        }
        
        List<RoleDto> roleList = roleService.getRecordsByConditions(paraMap);
//        List<RoleDto> roleList = roleService.selectOrderBy(new RoleDto(), "updated_date, role_id");
        
        auditLogger.info("查询所有角色" );
        logger.info("查询所有角色");
        
        result.put("roleList", roleList);
        return result;
    }
    
    
    /**
     * 角色资源树
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleRescourceListData")
    public Map<String, Object> getRoleRescourceListData(@RequestBody JsonRequest<RoleDto> jsonRequest) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        String roleId = jsonRequest.getExtend().get("roleId");
        //查询所有资源
        Map<String , String> paraMap = new HashMap<String, String>();
        paraMap.put("roleId", roleId);
        List<TreeNode> roleList = roleService.findAllResourceByRole(paraMap);
            
        //封装树
        List<TreeNode> copyNodes = new ArrayList<TreeNode>();
        copyNodes.addAll(roleList);  
        
        for(TreeNode treeNode : roleList){
            for(int i = 0 ;i< copyNodes.size() ;i++ ){
               
                if(treeNode.getParentId().equals(copyNodes.get(i).getId())){
                    copyNodes.get(i).addChild(treeNode);
                    
                    //子节点没有选中，父节点处于半选中状态
                    if(!treeNode.isSelected()){
                        copyNodes.get(i).setSelected(treeNode.isSelected());
                    }
                    
                    copyNodes.remove(treeNode);
                }
            }
        }
        
        auditLogger.info("查询角色【" + roleId  + "】资源" );
        logger.info("查询角色【" + roleId  + "】资源");
        
        result.put("roleResources", copyNodes);
        return result;
    }
    
    /**
     * 保存角色资源
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/saveRoleResource")
    public Map<String, Object> saveRoleResource(@RequestBody JsonRequest<RoleDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String , Object> reqMap = new HashMap<String, Object>();
        
        reqMap.put("roleId", jsonRequest.getExtend().get("roleId"));
        reqMap.put("resourceIds", (List<String>) JSON.parse(jsonRequest.getExtend().get("resourceIds")));
        reqMap.put("userId", (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY));
        roleService.saveRoleResource(reqMap);
        
        auditLogger.info("用户【" + reqMap.get("userId")  + "】保存角色【" + reqMap.get("roleId") + "】资源" + reqMap.get("resourceIds"));
        logger.info("用户【" + reqMap.get("userId")  + "】保存角色【" + reqMap.get("roleId") + "】资源" + reqMap.get("resourceIds"));
        
        result.put("msg", "保存成功！");
        return result;
    }
    
    /**
     * 新增、修改角色
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateRole")
    public Map<String, Object> saveOrUpdateRole(@RequestBody JsonRequest<RoleDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(jsonRequest.getExtend().get("roleId"));
        roleDto.setRoleName(jsonRequest.getExtend().get("roleName"));
        roleDto.setValidFlag(jsonRequest.getExtend().get("validFlag"));
        roleDto.setCreatedBy(userId);
        roleDto.setUpdatedBy(userId);
        
        String operType = jsonRequest.getExtend().get("operType");
        if("add".equals(operType)){
            roleDto.setValidFlag(RoleDto.VALIDFLAGTRUE);
            roleService.saveRole(roleDto);
            
            result.put("msg", "保存成功！");
        }else if("edit".equals(operType)){
            roleService.updateRole(roleDto);
            
            result.put("msg", "修改成功！");
        }
        
        auditLogger.info("用户【" + userId  + "】" + operType + "角色【" + roleDto.getRoleId() + "_" + roleDto.getRoleName()  + "】");
        logger.info("用户【" + userId  + "】" + operType + "角色【" + roleDto.getRoleId() + "_" + roleDto.getRoleName()  + "】");
        
        return result;
    }
    
    /**
     * 删除角色
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delRole")
    public Map<String, Object> delRole(@RequestBody JsonRequest<RoleDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        
        List<String> roleIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("roleIds"));
        for(String roleId : roleIds){
           
            roleService.delRole(roleId);
        }
       
        auditLogger.info("用户【" + userId  + "】删除角色【" + JSON.toJSONString(roleIds) + "】");
        logger.info("用户【" + userId  + "】删除角色【" + JSON.toJSONString(roleIds) + "】");
        result.put("msg", "删除成功！");
        return result;
    }
    
    /**
     * 禁用角色
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateRoleState")
    public Map<String, Object> updateRoleState(@RequestBody JsonRequest<RoleDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        
        List<String> roleIds = (List<String>) JSON.parse(jsonRequest.getExtend().get("roleIds"));
        for(String roleId : roleIds){
            RoleDto roleDto = new RoleDto();
            roleDto.setRoleId(roleId);
            roleDto.setValidFlag("0");
            roleService.updateByPrimaryKeyNotNull(roleDto);
        }
//        roleService.updateRoleState(roleIds);
        
        auditLogger.info("用户【" + userId  + "】禁用角色【" + JSON.toJSONString(roleIds) + "】");
        logger.info("用户【" + userId  + "】禁用角色【" + JSON.toJSONString(roleIds) + "】");
        result.put("msg", "禁用角色成功！");
        return result;
    }

    
}