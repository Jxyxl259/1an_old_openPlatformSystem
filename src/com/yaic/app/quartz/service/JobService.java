/************************************************************************
 * 描述 ：数据库表PMS_JOB对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.quartz.dao.JobDao;
import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.fa.service.BaseService;

@Service
public class JobService extends BaseService<JobDto>{
	private JobDao getJobDao(){
		return (JobDao) this.getBaseDao();
	}
	
	/**
	 * 条件查询
	 * @param paraMap
	 * @return
	 */
	public List<JobDto> findByCondition(Map<String,String> paraMap){
		return getJobDao().findByCondition(paraMap);
	}
}
