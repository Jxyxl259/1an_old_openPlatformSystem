/************************************************************************
 * 描述 ：数据库表CMS_ROLE对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.RoleDao;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.RoleDto;
import com.yaic.fa.service.BaseService;

@Service
public class RoleService extends BaseService<RoleDto>{

    @Autowired
    private ShiroCacheService shiroCacheService ;
    
    private RoleDao getRoleDao() {
        return (RoleDao) this.getBaseDao();
    }
    
    /**
     * 查找所有的角色
     * @return
     */
    public List<RoleDto> findAllRoles() {
        return getRoleDao().findAllRoles();
    }
    
    /**
     * 查找角色关联的资源
     * @param paraMap
     * @return
     */
    public  List<TreeNode> findAllResourceByRole(Map<String , String> paraMap) {
        return getRoleDao().findAllResourceByRole(paraMap);
    }

    /**
     * 保存角色资源
     * @param reqMap
     */
    @SuppressWarnings("unchecked")
    public void saveRoleResource(Map<String, Object> reqMap) {
        
        //删除角色资源
        getRoleDao().delRowResource((String) reqMap.get("roleId"));
        
        //保存
        if( ( (List<String>) reqMap.get("resourceIds") ).size() > 0 ){
            getRoleDao().saveRoleResource(reqMap);
            shiroCacheService.clearAllCache();
        }
       
    }

    /**
     * 保存角色
     * @param roleDto
     */
    public void saveRole(RoleDto roleDto) {
        insertNotNull(roleDto);
        shiroCacheService.clearAllCache();
    }

    /**
     * 修改角色
     * @param roleDto
     */
    public void updateRole(RoleDto roleDto) {
        updateByPrimaryKeyNotNull(roleDto);
        shiroCacheService.clearAllCache();
    }

    /**
     * 删除角色
     * @param roleId
     */
    public void delRole(String roleId) {
        
        //角色
        getRoleDao().delRow(roleId);
        //角色资源
        getRoleDao().delRowResource(roleId);
        shiroCacheService.clearAllCache();
    }

    /**
     * 禁用角色
     * @param roleIds
     */
    public void updateRoleState(List<String> roleIds) {

        getRoleDao().updateRoleState(roleIds);
    }

    /**
     * 查询列表
     * @param paraMap
     * @return
     */
    public List<RoleDto> getRecordsByConditions(Map<String, String> paraMap) {
        return  getRoleDao().getRecordsByConditions(paraMap);
    }

    
    
}
