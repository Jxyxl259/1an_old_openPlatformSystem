/************************************************************************
 * 描述 ：数据库表CMS_ROLE_RESOURCE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;

import com.yaic.app.common.dto.domain.RoleResourceDto;
import com.yaic.fa.dao.BaseDao;

public interface RoleResourceDao extends BaseDao<RoleResourceDto> {

    List<RoleResourceDto> findUserResources(String userCode);
}