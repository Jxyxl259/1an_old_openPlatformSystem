/************************************************************************
 * 描述 ：数据库表CMS_USER_ROLE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.RoleDto;
import com.yaic.app.common.dto.domain.UserRoleDto;
import com.yaic.app.common.form.UserRoleForm;
import com.yaic.fa.dao.BaseDao;

public interface UserRoleDao extends BaseDao<UserRoleDto> {
 /**
  * 查询未分配用户角色列表
  * @param userId
  * @return
  */
    public List<RoleDto> findUnConfigRoles(String userId);
  
    /**
    * 查询用户角色列表 
    * @param userId
    * @return
    */
    public List<UserRoleForm>  findUserRoles (String userId);
    
    /**
     * 查找用户排除资源
     * @param paraMap
     * @return
     */
    public  List<TreeNode> findAllResourceByUser(String userId) ;
    
    /**
     * 查询角色排除资源
     * @param paraMap
     * @return
     */
    public  List<TreeNode> findAllResourceByRoles(Map paramMap) ;
    

}