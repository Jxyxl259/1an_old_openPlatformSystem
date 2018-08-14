/************************************************************************
 * 描述 ：数据库表PMS_JOB对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.service.ParameterService;
import com.yaic.app.quartz.QuartzConstants;
import com.yaic.app.quartz.dto.domain.JobDto;
import com.yaic.app.quartz.service.JobService;
import com.yaic.app.quartz.service.QuartzService;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.log.Log;
import com.yaic.fa.util.UuidUtils;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/job")
public class JobController {

    @Log
    private Logger logger;

    @Autowired
    private JobService jobService;

    @Autowired
    private ParameterService parameterService;
    
    @Autowired
    private QuartzService quartzService;

    /**
     * 定时任务页面
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "sys/job/jobManage";
    }

    /**
     * 获取页面参数
     * 
     * @return
     */
    @RequestMapping(value = "/getJobStatusList")
    public Map<String, Object> getJobStatusList() {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("localLanguage", "C");
        paraMap.put("parameterType", Constants.PARAMETERTYPE_JOB_STATUS);
        List<ComboDto> dataList = parameterService.getComboList(paraMap);
        result.put("dataList", dataList);
        return result;
    }

    /**
     * 查询定时任务列表
     * 
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public Map<String, Object> list(@RequestBody JsonRequest<JobDto> jsonRequest) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> paraMap = new HashMap<String, String>();
        String jobName = jsonRequest.getExtend().get("jobName");
        String jobGroupName = jsonRequest.getExtend().get("jobGroupName");
        String jobStatus = jsonRequest.getExtend().get("jobStatus");

        if (StringUtil.isNotEmpty(jobName)) {
            paraMap.put("jobName", "%" + jobName + "%");
        }
        if (StringUtil.isNotEmpty(jobGroupName)) {
            paraMap.put("jobGroupName", "%" + jobGroupName + "%");
        }
        if (StringUtil.isNotEmpty(jobStatus)) {
            paraMap.put("jobStatus", jobStatus);
        }
        List<JobDto> dataList = jobService.findByCondition(paraMap);
       /* List<JobDto> dataList = jobService.selectOrderBy(new JobDto(), "updated_date, job_name");*/

        logger.info("查询定时任务列表");

        result.put("dataList", dataList);
        return result;
    }

    /**
     * 新增任务
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveJob")
    public Map<String, Object> saveJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        Map<String, Object> result = new HashMap<String, Object>();
        JobDto jobDto = jsonRequest.getForm();
        jobDto.setJobId(UuidUtils.getUuid());
        jobDto.setCreatedBy(userId);
        jobDto.setUpdatedBy(userId);
        jobDto.setUpdatedDate(new Date());
        jobDto.setCreatedDate(new Date());
        jobService.insertNotNull(jobDto);
        logger.info("用户【{}】新增任务【{}】", userId, jsonRequest.getForm().getJobName());
        result.put("msg", "新增成功！");

        quartzService.addJob(jobDto);
        
        return result;
    }

    /**
     * 修改任务
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/updateJob")
    public Map<String, Object> updateJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        Map<String, Object> result = new HashMap<String, Object>();
        JobDto jobDto = jsonRequest.getForm();
        jobDto.setUpdatedBy(userId);
        jobDto.setUpdatedDate(new Date());
        jobService.updateByPrimaryKeyNotNull(jobDto);
        logger.info("用户【{}】修改任务【{}】", userId, jsonRequest.getForm().getJobName());

        result.put("msg", "修改成功！");

        jobDto = jobService.selectByPrimaryKey(jobDto);
        quartzService.modifyJob(jobDto);
        
        return result;
    }

    /**
     * 删除任务
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "/deleteJob")
    public Map<String, Object> deleteJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));
        JobDto jobDto = new JobDto();
        JobDto job = new JobDto();
        for (String id : ids) {
            jobDto.setJobId(id);
            job = jobService.selectByPrimaryKey(jobDto);
            jobService.deleteByPrimaryKey(jobDto);
            quartzService.deleteJob(job);
        }

        logger.info("用户【{}】删除任务【{}】", userId, JSON.toJSONString(ids));
        result.put("msg", "删除成功！");
        
        return result;
    }

    /**
     * 运行任务
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/runningJob")
    public Map<String, Object> runningJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        String jobId = jsonRequest.getExtend().get("jobId");
        JobDto jobDto = new JobDto();
        jobDto.setJobId(jobId);
        jobDto.setJobStatus(QuartzConstants.JOB_STATUS_RUNNING);
        jobService.updateByPrimaryKeyNotNull(jobDto);
        logger.info("用户【{}】运行任务【{}】", userId, jobId);
        result.put("msg", "操作成功！");

        jobDto = jobService.selectByPrimaryKey(jobDto);
        quartzService.runJob(jobDto);
        
        return result;
    }

    /**
     * 暂停任务
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/stopJob")
    public Map<String, Object> stopJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        String jobId = jsonRequest.getExtend().get("jobId");
        JobDto jobDto = new JobDto();
        jobDto.setJobId(jobId);
        jobDto.setJobStatus(QuartzConstants.JOB_STATUS_PAUSE);
        jobService.updateByPrimaryKeyNotNull(jobDto);
        logger.info("用户【{}】停止任务【{}】", userId, jobId);
        result.put("msg", "操作成功！");
        
        jobDto = jobService.selectByPrimaryKey(jobDto);
        quartzService.pauseJob(jobDto);
        
        return result;
    }

    /**
     * 更新任务状态
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/lockJob")
    public Map<String, Object> lockJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);

        List<String> datas = (List<String>) JSON.parse(jsonRequest.getExtend().get("datas"));
        for (String data : datas) {
            JobDto jobDto = new JobDto();
            jobDto.setJobId(data);
            jobDto.setJobStatus(QuartzConstants.JOB_STATUS_INVALID);
            jobService.updateByPrimaryKeyNotNull(jobDto);
            
            jobDto = jobService.selectByPrimaryKey(jobDto);
            quartzService.pauseJob(jobDto);
        }
        logger.info("用户【{}】禁用任务【{}】", userId, JSON.toJSONString(datas));
        result.put("msg", "操作成功！");
        
        
        
        return result;
    }

    /**
     * 更新任务状态
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/unlockJob")
    public Map<String, Object> unlockJob(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        JobDto jobDto = new JobDto();

        List<String> datas = (List<String>) JSON.parse(jsonRequest.getExtend().get("datas"));
        for (String data : datas) {
            jobDto.setJobId(data);
            jobDto.setJobStatus(QuartzConstants.JOB_STATUS_NOTRUN);
            jobService.updateByPrimaryKeyNotNull(jobDto);
            jobDto = jobService.selectByPrimaryKey(jobDto);
            quartzService.resumeJob(jobDto);
        }
        logger.info("用户【{}】启用任务【{}】", userId, JSON.toJSONString(datas));
        result.put("msg", "操作成功！");
        
        return result;
    }

    /**
     * 查询条件查询
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/query")
    public Map<String, Object> query(@RequestBody JsonRequest<JobDto> jsonRequest, HttpServletRequest request) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, String> paraMap = new HashMap<String, String>();
        String jobName = jsonRequest.getExtend().get("jobName");
        String jobGroupName = jsonRequest.getExtend().get("jobGroupName");
        String jobStatus = jsonRequest.getExtend().get("jobStatus");

        if (StringUtil.isNotEmpty(jobName)) {
            paraMap.put("jobName", jobName);
        }
        if (StringUtil.isNotEmpty(jobGroupName)) {
            paraMap.put("jobGroupName", jobGroupName);
        }
        if (StringUtil.isNotEmpty(jobStatus)) {
            paraMap.put("jobStatus", jobStatus);
        }
        List<JobDto> dataList = jobService.findByCondition(paraMap);

        result.put("dataList", dataList);

        return result;
    }
}