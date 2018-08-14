package com.yaic.app.quartz.util;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.fa.log.Log;
import com.yaic.fa.spring.SpringUtils;
import com.yaic.servicelayer.util.StringUtil;

public class JobUtils {

    @Log
    private Logger logger;

    @Autowired
    private RestTemplate restTemplate;

    public void invokJob(JobDto jobDto) {
        
        Object object = null;
        Class clazz = null;
        if (StringUtil.isNotEmpty(jobDto.getSpringId())) {
            object = SpringUtils.getBean(jobDto.getSpringId());
        } else if (StringUtil.isNotEmpty(jobDto.getJobClass())) {
            try {
                clazz = Class.forName(jobDto.getJobClass());
                object = clazz.newInstance();
            } catch (Exception e) {
            }
        }
        if (object != null) {
            clazz = object.getClass();
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(jobDto.getMethodName());
            } catch (NoSuchMethodException e) {
                logger.error("任务[" + jobDto + "]，未启动成功，方法名设置错误！", e);
            }
            if (method != null) {
                Object result = null;
                try {
                    result = method.invoke(object);
                } catch (Exception e) {
                    logger.error("任务[" + jobDto + "]，未启动成功，方法调用错误！", e);
                    return;
                }
                //logger.info("任务[" + jobDto + "]，启动成功，结果为：" + result);
            } else {
                logger.error("任务[" + jobDto + "]，未启动成功，任务设置错误！");
            }
        } else if (StringUtil.isNotEmpty(jobDto.getRestfulUrl())) {
            String result = null;
            try {
                result = restTemplate.getForObject(jobDto.getRestfulUrl(), String.class);
            } catch (Exception e) {
                logger.error("任务[" + jobDto + "]，未启动成功，服务调用错误！", e);
                return;
            }
            //logger.info("任务[" + jobDto + "]，启动成功，结果为：" + result);
        } else {
            logger.error("任务[" + jobDto + "]，未启动成功，任务设置错误！");
        }
    }
}
