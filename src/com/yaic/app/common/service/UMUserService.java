/************************************************************************
 * 描述 ：数据库表CMS_USER对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.RoleResourceDao;
import com.yaic.app.common.dao.UserDao;
import com.yaic.app.common.dao.UserExcludeResourceDao;
import com.yaic.app.common.dao.UserRoleDao;
import com.yaic.app.common.dto.domain.RoleResourceDto;
import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.app.common.dto.domain.UserExcludeResourceDto;
import com.yaic.app.common.dto.domain.UserRoleDto;
import com.yaic.app.common.form.UserRoleForm;
import com.yaic.fa.service.BaseService;

@Service
public class UMUserService extends BaseService<UserDto> {
	@Autowired
	private UserExcludeResourceDao userExcludeResourceDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleResourceDao roleResourceDao ;
    
	private UserDao getUserDao(){
	    return (UserDao) this.getBaseDao();
	}
	/**
	 * 
	 * @param userDto
	 * @return
	 */
	public List<UserDto> selectList(UserDto userDto) {
		List<UserDto> result = this.select(userDto);
		return result;
	}

	/**
	 * 删除用户列表
	 * @param userDto
	 * @return
	 */
	public int deleteList(List<UserDto> userDtoList) {
		int count = 0; 
		UserRoleDto userRoleDto = new UserRoleDto();
		UserExcludeResourceDto userExcludeResourceDto = new UserExcludeResourceDto();
		for (UserDto userDto : userDtoList) {
			userRoleDto.setUserId(userDto.getUserCode());
			userExcludeResourceDto.setUserId(userDto.getUserCode());
			this.delete(userDto);
			//删除用户排除资源
			 userExcludeResourceDao.delete(userExcludeResourceDto);
			//删除用户角色
			 userRoleDao.delete(userRoleDto);
			count++;
		}
		return count;
	}

	/**
	 *  查找用户角色
	 * @param username
	 * @return
	 */
    public List<UserRoleForm> findUserRoles(String userCode) {
        return userRoleDao.findUserRoles(userCode);
    }

    /**
     * 查找用户权限
     * @param userCode
     * @return
     */
    public List<RoleResourceDto> findUserResources(String userCode) {
        return roleResourceDao.findUserResources(userCode);
    }
    
    /**
     * 根据用户代码修改用户密码
     * @param paraMap
     */
    public void updatePassword(Map<String,String> paraMap){
    	 getUserDao().updatePassword(paraMap);
    }
}
