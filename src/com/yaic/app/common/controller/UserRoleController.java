/************************************************************************
 * 描述 ：数据库表CMS_USER_ROLE对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.UserExcludeResourceDto;
import com.yaic.app.common.dto.domain.UserRoleDto;
import com.yaic.app.common.service.UserExcludeResourceService;
import com.yaic.app.common.service.UserRoleService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.fa.util.UuidUtils;

@Controller
@RequestMapping("/userRole")
public class UserRoleController {

	@Log("auditLogger")
	private Logger auditLogger;

	@Log
	private Logger logger;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserExcludeResourceService userExcludeResourceService;

	/**
	 * 用户角色编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public Map<String, Object> loadUserRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userCode = request.getParameter("userCode");
		Map<String, Object> map = new HashMap<String, Object>();
		UserRoleDto userRoleDto = new UserRoleDto();
		userRoleDto.setUserId(userCode);
		// 角色列表
		List userRoleList = userRoleService.findUserRoles(userCode);
		map.put("userCode", userCode);
		map.put("userRoleList", userRoleList);
		List<TreeNode> resourceList = userRoleService
				.findAllResourceByUser(userCode);
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		treeNodes.addAll(resourceList);

		for (TreeNode treeNode : resourceList) {
			for (int i = 0; i < treeNodes.size(); i++) {
				if (treeNode.getParentId().equals(treeNodes.get(i).getId())) {
					treeNodes.get(i).addChild(treeNode);
					treeNodes.remove(treeNode);
				}
			}
		}
		map.put("resourceTree", treeNodes);
		return map;
	}
	
	

	/**
	 * 用户角色更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Map<String, Object> updateUserRole(
			@RequestBody JsonRequest<UserRoleDto> jsonRequest,
			HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String loginUserId = (String) session
				.getAttribute(Constants.LOGIN_USER_ID_KEY);
		String userCode = (String) jsonRequest.getExtend().get("userId");
		List<JSONObject> userRoleList = (List<JSONObject>) JSON
				.parse(jsonRequest.getExtend().get("userRoleList"));
		List<String> resourceIds = (List<String>) JSON.parse(jsonRequest
				.getExtend().get("resourceIds"));
		paraMap.put("userCode", userCode);
		paraMap.put("resourceIds", resourceIds);
		paraMap.put("loginUserId", loginUserId);
		/** 用户角色 **/
		UserRoleDto userRoleDto = new UserRoleDto();
		userRoleDto.setUserId(userCode);
		userRoleService.delete(userRoleDto);

		if (userRoleList != null) {
			for (JSONObject userRoleJsonObj : userRoleList) {
				UserRoleDto userRoleDtoTmp = JSONObject.toJavaObject(
						userRoleJsonObj, UserRoleDto.class);
				userRoleDtoTmp.setUserRoleId(UuidUtils.getUuid());
				userRoleDtoTmp.setCreatedBy(loginUserId);
				userRoleDtoTmp.setCreatedDate(new Date());
				userRoleDtoTmp.setUpdatedBy(loginUserId);
				userRoleDtoTmp.setUpdatedDate(new Date());
				userRoleService.insert(userRoleDtoTmp);
			}
		}
		/** 用户排除资源 **/
		UserExcludeResourceDto userExcludeResourceDto = new UserExcludeResourceDto();
		userExcludeResourceDto.setUserId(userCode);
		userExcludeResourceService.delete(userExcludeResourceDto);
		if (resourceIds != null && resourceIds.size() > 0) {
			userExcludeResourceService.saveExcludeResource(paraMap);
		}

		logger.info("用户 " + "[" + loginUserId + "] 修改用户 [" + userCode + "]权限");
		map.put("msg", "权限修改成功");
		return map;
	}
	
	/**
	 * 加载资源数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/loadRoleResource")
	public Map<String, Object> loadRoleResource(
			@RequestBody JsonRequest<UserRoleDto> jsonRequest) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		String userCode = (String) jsonRequest.getExtend().get("userCode");
		List<String> roleList = (List<String>) JSON.parse(jsonRequest
				.getExtend().get("roleList"));

		paramMap.put("userCode", userCode);
		paramMap.put("roleList", roleList);
		if (roleList != null && roleList.size() > 0) {
			List<TreeNode> resourceList = userRoleService
					.findAllResourceByRoles(paramMap);

			treeNodes.addAll(resourceList);
			for (TreeNode treeNode : resourceList) {
				for (int i = 0; i < treeNodes.size(); i++) {
					if (treeNode.getParentId().equals(treeNodes.get(i).getId())) {
						treeNodes.get(i).addChild(treeNode);
						treeNodes.remove(treeNode);
					}
				}
			}
		}
		map.put("resourceTree", treeNodes);
		return map;
	}

}