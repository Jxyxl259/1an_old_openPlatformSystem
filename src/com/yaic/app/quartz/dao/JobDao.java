/************************************************************************
 * 描述 ：数据库表PMS_JOB对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.fa.dao.BaseDao;

public interface JobDao extends BaseDao<JobDto> {
	List<JobDto> findByCondition(Map<String,String> paraMap);
}