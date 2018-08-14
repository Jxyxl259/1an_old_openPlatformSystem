/************************************************************************
 * 描述 ：数据库表PMS_JOB对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 14:18:13
 *
 ************************************************************************/

package com.yaic.app.quartz.dto.domain;

import java.util.Date;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Table(name = "app_job")
public class JobDto implements Serializable {

    private static final long serialVersionUID = JobDto.class.getName().hashCode();

    /** 任务ID */
    @Id
    private String jobId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 任务名称 */
    private String jobName;

    /** 任务组 */
    private String jobGroupName;

    /** 任务状态 0 未运行 1 运行中 2 暂停 3 作废 */
    private String jobStatus;

    /** 任务表达式 */
    private String cronExpression;

    /** 任务执行类全名 */
    private String jobClass;

    /** 任务执行类在SPRING配置中的ID */
    private String springId;

    /** 任务执行方法，无参。【注：选择JOB_CLASS或SPRING_ID时必录】 */
    private String methodName;

    /** 任务执行RESTFUL服务URL，无参。【注：JOB_CLASS、SPRING_ID和RESTFUL_URL方式三选一】 */
    private String restfulUrl;

    /** 任务说明 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性任务ID的值
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取属性任务ID的值
     */     
    public String getJobId() {
        return this.jobId;
    }

    /**
     * 设置属性创建日期的值
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取属性创建日期的值
     */
    public Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * 设置属性创建人的值
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取属性创建人的值
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 设置属性更新日期的值
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 获取属性更新日期的值
     */
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * 设置属性更新人的值
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取属性更新人的值
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * 设置属性任务名称的值
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取属性任务名称的值
     */
    public String getJobName() {
        return this.jobName;
    }

    /**
     * 设置属性任务组的值
     */
    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    /**
     * 获取属性任务组的值
     */
    public String getJobGroupName() {
        return this.jobGroupName;
    }

    /**
     * 设置属性任务状态 0 未运行 1 运行中 2 暂停 3 作废的值
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 获取属性任务状态 0 未运行 1 运行中 2 暂停 3 作废的值
     */
    public String getJobStatus() {
        return this.jobStatus;
    }

    /**
     * 设置属性任务表达式的值
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取属性任务表达式的值
     */
    public String getCronExpression() {
        return this.cronExpression;
    }

    /**
     * 设置属性任务执行类全名的值
     */
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * 获取属性任务执行类全名的值
     */
    public String getJobClass() {
        return this.jobClass;
    }

    /**
     * 设置属性任务执行类在SPRING配置中的ID的值
     */
    public void setSpringId(String springId) {
        this.springId = springId;
    }

    /**
     * 获取属性任务执行类在SPRING配置中的ID的值
     */
    public String getSpringId() {
        return this.springId;
    }

    /**
     * 设置属性任务执行方法，无参。【注：选择JOB_CLASS或SPRING_ID时必录】的值
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取属性任务执行方法，无参。【注：选择JOB_CLASS或SPRING_ID时必录】的值
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * 设置属性任务执行RESTFUL服务URL，无参。【注：JOB_CLASS、SPRING_ID和RESTFUL_URL方式三选一】的值
     */
    public void setRestfulUrl(String restfulUrl) {
        this.restfulUrl = restfulUrl;
    }

    /**
     * 获取属性任务执行RESTFUL服务URL，无参。【注：JOB_CLASS、SPRING_ID和RESTFUL_URL方式三选一】的值
     */
    public String getRestfulUrl() {
        return this.restfulUrl;
    }

    /**
     * 设置属性任务说明的值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取属性任务说明的值
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置属性其他标志的值
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 获取属性其他标志的值
     */
    public String getFlag() {
        return this.flag;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}