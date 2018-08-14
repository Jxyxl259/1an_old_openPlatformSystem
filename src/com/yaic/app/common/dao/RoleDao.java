/************************************************************************
 * 描述 ：数据库表CMS_ROLE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.RoleDto;
import com.yaic.fa.dao.BaseDao;

public interface RoleDao extends BaseDao<RoleDto> {

    /**
     * 查找所有的角色
     * @return
     */
    List<RoleDto> findAllRoles();

    /**
     * 查找角色关联的资源
     * @param paraMap
     * @return
     */
    List<TreeNode> findAllResourceByRole(Map<String , String> paraMap);

    /**
     * 批量保存
     * @param reqMap
     */
    public void saveRoleResource(Map<String, Object> reqMap);

    /**
     * 禁用
     * @param roleIds
     * @return
     */
    public void updateRoleState(List<String> roleIds);

    /**
     * 删除角色
     * @param object
     */
    void delRow(String roleId);

    /**
     * 删除角色资源
     * @param roleId
     */
    void delRowResource(String roleId);

    /**
     * 查询列表
     * @param paraMap
     * @return
     */
    List<RoleDto> getRecordsByConditions(Map<String, String> paraMap);
    
}