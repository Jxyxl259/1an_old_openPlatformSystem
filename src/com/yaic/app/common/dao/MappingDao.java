/************************************************************************
 * 描述 ：数据库表CMS_MAPPING对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.domain.MappingDto;
import com.yaic.fa.dao.BaseDao;

public interface MappingDao extends BaseDao<MappingDto> {
	List<MappingDto> findByMappingName(Map<String, String> paraMap);
}