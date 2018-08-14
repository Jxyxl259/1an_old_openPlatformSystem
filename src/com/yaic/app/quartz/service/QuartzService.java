/************************************************************************
 * 描述 ：Quartz的服务类。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.service;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.yaic.app.quartz.QuartzConstants;
import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.app.quartz.util.JobWapper;
import com.yaic.fa.log.Log;

@Service
public class QuartzService implements InitializingBean {

    @Log
    private Logger logger;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    JobService jobService;

    public void runJob(JobDto jobDto) throws SchedulerException {
        addJob(jobDto);
    }

    public void addJob(JobDto jobDto) throws SchedulerException {
        if (jobDto == null || !QuartzConstants.JOB_STATUS_RUNNING.equals(jobDto.getJobStatus())) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(jobDto.getJobName(), jobDto.getJobGroupName());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            Class<? extends Job> clazz = JobWapper.class;

            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobDto.getJobName(), jobDto.getJobGroupName()).build();

            jobDetail.getJobDataMap().put("JobDto", jobDto);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDto.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity(jobDto.getJobName(), jobDto.getJobGroupName()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDto.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

        logger.info("job[" + jobDto.getJobName() + "] starting.");
    }

    public void deleteJob(JobDto jobDto) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobDto.getJobName(), jobDto.getJobGroupName());
        if (jobKey != null) {
            scheduler.deleteJob(jobKey);
        }
    }

    public void pauseJob(JobDto jobDto) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobDto.getJobName(), jobDto.getJobGroupName());
        if (jobKey != null) {
            scheduler.pauseJob(jobKey);
        }
    }

    public void resumeJob(JobDto jobDto) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobDto.getJobName(), jobDto.getJobGroupName());
        if (jobKey != null) {
            scheduler.resumeJob(jobKey);
        } else {
            addJob(jobDto);
        }
    }

    public void modifyJob(JobDto jobDto) throws SchedulerException {
        deleteJob(jobDto);
        addJob(jobDto);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDto record = new JobDto();
        record.setJobStatus(QuartzConstants.JOB_STATUS_RUNNING);
        List<JobDto> jobList = jobService.select(record);
        for (JobDto job : jobList) {
            addJob(job);
        }
        scheduler.start();
    }
}
