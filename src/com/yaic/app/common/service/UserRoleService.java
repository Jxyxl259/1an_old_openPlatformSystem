/************************************************************************
 * 描述 ：数据库表CMS_USER_ROLE对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.UserRoleDao;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.RoleDto;
import com.yaic.app.common.dto.domain.UserRoleDto;
import com.yaic.app.common.form.UserRoleForm;
import com.yaic.fa.service.BaseService;

@Service
public class UserRoleService extends BaseService<UserRoleDto>{
  private UserRoleDao getUserRoleDao() {
	        return (UserRoleDao) this.getBaseDao();
	    }
	/**
	 * 查询未分配的角色列表
	 * @param userDto
	 * @return
	 */
	public List<RoleDto> findUnConfigRoles(String userId) {
	    List<RoleDto> result = null  ;
	    result = getUserRoleDao().findUnConfigRoles(userId);
		return result;
	}
	
	/**
	 * 查询用户角色列表
	 * @param userDto
	 * @return
	 */
	public List<UserRoleForm> findUserRoles(String userId) {
	    List<UserRoleForm> result = null  ;
	    result = getUserRoleDao().findUserRoles(userId);
		return result;
	}
	
	   /**
     * 查找用户排除资源
     * @param paraMap
     * @return
     */
    public  List<TreeNode> findAllResourceByUser(String userId) {
        return getUserRoleDao().findAllResourceByUser(userId);
    }
    /**
     * 查询角色排除资源
     * @param paraMap
     * @return
     */
    public  List<TreeNode> findAllResourceByRoles(Map paramMap) {
    	  return getUserRoleDao().findAllResourceByRoles(paramMap);
    }
	
}
