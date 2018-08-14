/*
 * Created By lujicong (2016-06-02 10:11:06)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.callback.dto.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Table(name = "t_sns_callback_info")
public class SnsCallbackInfoDto implements Serializable {

    private static final long serialVersionUID = SnsCallbackInfoDto.class.getName().hashCode();

    /** id */
    @Id
    @GeneratedValue(generator = "JDBC")
    private java.lang.Integer id;

    /** app_id */
    private java.lang.String appId;

    /** 回调内容 */
    private java.lang.String content;

    /** 业务单号 */
    private java.lang.String businessNo;

    /** 处理类型 */
    private java.lang.String dealType;

    /** 处理状态:0未处理成功,1已处理成功 */
    private java.lang.Integer dealStatus;

    /** 处理次数 */
    private java.lang.Integer dealCount;

    /** 创建人 */
    private java.lang.String createdUser;

    /** 创建时间 */
    private java.util.Date createdDate;

    /** 更新人 */
    private java.lang.String updatedUser;

    /** 更新时间 */
    private java.util.Date updatedDate;

    /** 是否有效:0正常,1作废值 */
    private java.lang.Integer invalidFlag;

    /** 一次处理条数 */
    @Transient
    private java.lang.Integer limitCount;

    /**
     * 设置属性id的值
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取属性id的值
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 设置属性app_id的值
     */
    public void setAppId(java.lang.String appId) {
        this.appId = appId;
    }

    /**
     * 获取属性app_id的值
     */
    public java.lang.String getAppId() {
        return this.appId;
    }

    /**
     * 设置属性处理类型的值
     */
    public void setDealType(java.lang.String dealType) {
        this.dealType = dealType;
    }

    /**
     * 获取属性处理类型的值
     */
    public java.lang.String getDealType() {
        return dealType;
    }

    /**
     * 设置属性回调内容的值
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * 获取属性回调内容的值
     */
    public java.lang.String getContent() {
        return this.content;
    }

    /**
     * 设置属性业务单号的值
     */
    public void setBusinessNo(java.lang.String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * 获取属性业务单号的值
     */
    public java.lang.String getBusinessNo() {
        return businessNo;
    }

    /**
     * 设置属性处理状态:0未处理成功,1已处理成功的值,2处理中
     */
    public void setDealStatus(java.lang.Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    /**
     * 获取属性处理状态:0未处理成功,1已处理成功的值
     */
    public java.lang.Integer getDealStatus() {
        return this.dealStatus;
    }

    /**
     * 设置属性处理次数的值
     */
    public void setDealCount(java.lang.Integer dealCount) {
        this.dealCount = dealCount;
    }

    /**
     * 获取属性处理次数的值
     */
    public java.lang.Integer getDealCount() {
        return this.dealCount;
    }

    /**
     * 设置属性创建人的值
     */
    public void setCreatedUser(java.lang.String createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * 获取属性创建人的值
     */
    public java.lang.String getCreatedUser() {
        return this.createdUser;
    }

    /**
     * 设置属性创建时间的值
     */
    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取属性创建时间的值
     */
    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * 设置属性更新人的值
     */
    public void setUpdatedUser(java.lang.String updatedUser) {
        this.updatedUser = updatedUser;
    }

    /**
     * 获取属性更新人的值
     */
    public java.lang.String getUpdatedUser() {
        return this.updatedUser;
    }

    /**
     * 设置属性更新时间的值
     */
    public void setUpdatedDate(java.util.Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 获取属性更新时间的值
     */
    public java.util.Date getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * 设置属性是否有效:0正常,1作废值的值
     */
    public void setInvalidFlag(java.lang.Integer invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    /**
     * 获取属性是否有效:0正常,1作废值的值
     */
    public java.lang.Integer getInvalidFlag() {
        return this.invalidFlag;
    }

    public java.lang.Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(java.lang.Integer limitCount) {
        this.limitCount = limitCount;
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