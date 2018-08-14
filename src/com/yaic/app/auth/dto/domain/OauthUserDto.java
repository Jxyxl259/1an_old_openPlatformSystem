/*
 * Created By lujicong (2016-04-27 09:47:16)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.dto.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Table(name = "t_sns_oauth_user")
public class OauthUserDto implements Serializable {
    
    private static final long serialVersionUID = OauthUserDto.class.getName().hashCode();
    
    /** app_id */ 
    @Id
    private java.lang.String appId;
    
    /** app_secret */ 
    private java.lang.String appSecret;
    
    /** 用户名 */ 
    private java.lang.String userName;
    
    /** 手机号 */ 
    private java.lang.String phoneNumber;
    
    /** 创建人 */ 
    private java.lang.String createdUser;
    
    /** 创建时间 */ 
    private java.util.Date createdDate;
    
    /** 更新人 */ 
    private java.lang.String updatedUser;
    
    /** 更新时间 */ 
    private java.util.Date updatedDate;
    
    /** 是否有效:0正常,1作废值 */ 
    private Integer invalidFlag;
    
    /** 回调URL */ 
    private java.lang.String callbackUrl;
    
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
     * 设置属性app_secret的值
     */ 
    public void setAppSecret(java.lang.String appSecret) {
        this.appSecret = appSecret;
    }
    
    /**
     * 获取属性app_secret的值
     */ 
    public java.lang.String getAppSecret() {
        return this.appSecret;
    }
    
    /**
     * 设置属性用户名的值
     */ 
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }
    
    /**
     * 获取属性用户名的值
     */ 
    public java.lang.String getUserName() {
        return this.userName;
    }
    
    /**
     * 设置属性手机号的值
     */ 
    public void setPhoneNumber(java.lang.String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * 获取属性手机号的值
     */ 
    public java.lang.String getPhoneNumber() {
        return this.phoneNumber;
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
    public void setInvalidFlag(Integer invalidFlag) {
        this.invalidFlag = invalidFlag;
    }
    
    /**
     * 获取属性是否有效:0正常,1作废值的值
     */ 
    public Integer getInvalidFlag() {
        return this.invalidFlag;
    }
    
    /**
     * 设置属性回调URL的值
     */ 
    public void setCallbackUrl(java.lang.String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
    
    /**
     * 获取属性回调URL的值
     */ 
    public java.lang.String getCallbackUrl() {
        return callbackUrl;
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