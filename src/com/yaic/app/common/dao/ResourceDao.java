/************************************************************************
 * 描述 ：数据库表CMS_RESOURCE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-10-22 09:44:48
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.HashMap;
import java.util.List;

import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.ResourceDto;
import com.yaic.fa.dao.BaseDao;

public interface ResourceDao extends BaseDao<ResourceDto> {
    List<ResourceDto> getResource(HashMap<String, String> condition);
    List<TreeNode> findAllMenuResource();
    List<TreeNode> findAllServiceResource();
    List<ResourceDto> findMaxResourceId();
}