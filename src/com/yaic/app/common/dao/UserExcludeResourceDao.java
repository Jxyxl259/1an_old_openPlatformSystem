/************************************************************************
 * 描述 ：数据库表CMS_USER_EXCLUDE_RESOURCE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.Map;

import com.yaic.app.common.dto.domain.UserExcludeResourceDto;
import com.yaic.fa.dao.BaseDao;

public interface UserExcludeResourceDao extends BaseDao<UserExcludeResourceDto> {
	
    /**
     * 批次保存
     * @param reqMap
     */
    public void saveExcludeResource(Map<String, Object> map);
}