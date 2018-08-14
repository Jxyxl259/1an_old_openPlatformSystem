package com.yaic.app.quartz.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.fa.spring.SpringUtils;

public class JobWapper implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDto jobDto = (JobDto) context.getMergedJobDataMap().get("JobDto");
        JobUtils jobUtils = SpringUtils.getBean("jobUtils");
        jobUtils.invokJob(jobDto);
    }
}
