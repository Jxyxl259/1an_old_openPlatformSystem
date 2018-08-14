/*
 * Created By lujicong (2016-04-27 14:03:22)
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

@Table(name = "t_sns_oauth_token")
public class OauthTokenDto implements Serializable {
    
    private static final long serialVersionUID = OauthTokenDto.class.getName().hashCode();
    
    /** id */ 
    @Id
    private java.lang.Integer id;
    
    /** app_id */ 
    private java.lang.String appId;
    
    /** token */ 
    private String token;
    
    /** expire_Time */ 
    private java.util.Date expireTime;
    
    /** refresh_token */ 
    private String refreshToken;
    
    /** ref_expire_Time */ 
    private java.util.Date refExpireTime;
    
    /** open_id */ 
    private java.lang.String openId;
    
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
     * 设置属性token的值
     */ 
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * 获取属性token的值
     */ 
    public String getToken() {
        return this.token;
    }
    
    /**
     * 设置属性expire_Time的值
     */ 
    public java.util.Date getExpireTime() {
        return expireTime;
    }

    /**
     * 获取属性expire_Time的值
     */ 
    public void setExpireTime(java.util.Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 设置属性refresh_token的值
     */ 
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    /**
     * 获取属性refresh_token的值
     */ 
    public String getRefreshToken() {
        return this.refreshToken;
    }
    
    public java.util.Date getRefExpireTime() {
        return refExpireTime;
    }

    public void setRefExpireTime(java.util.Date refExpireTime) {
        this.refExpireTime = refExpireTime;
    }

    /**
     * 设置属性open_id的值
     */ 
    public void setOpenId(java.lang.String openId) {
        this.openId = openId;
    }
    
    /**
     * 获取属性open_id的值
     */ 
    public java.lang.String getOpenId() {
        return this.openId;
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