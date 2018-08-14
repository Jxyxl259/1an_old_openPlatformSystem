/************************************************************************
 * 描述 ：数据库表CMS_CONFIG对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.domain.ConfigDto;
import com.yaic.fa.dao.BaseDao;

public interface ConfigDao extends BaseDao<ConfigDto> {

    List<ConfigDto> getRecordsByConditions(Map<String, String> paraMap);
}