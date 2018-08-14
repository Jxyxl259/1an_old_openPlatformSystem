/*
 * Created By lujicong (2016-05-05 16:25:04)
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

@Table(name = "t_sns_oauth_service_log")
public class OauthServiceLogDto implements Serializable {
    
    private static final long serialVersionUID = OauthServiceLogDto.class.getName().hashCode();
    
    /** id */ 
    @Id
    private java.lang.Integer id;
    
    /** app_id */ 
    private java.lang.String appId;
    
    /** token */ 
    private java.lang.String token;
    
    /** 接口资源ID */ 
    private java.lang.Integer resourceId;
    
    /** 请求内容 */ 
    private java.lang.String reqContent;
    
    /** 响应内容 */ 
    private java.lang.String respContent;
    
    /** 创建时间 */ 
    private java.util.Date createdDate;
    
    
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
    public void setToken(java.lang.String token) {
        this.token = token;
    }
    
    /**
     * 获取属性token的值
     */ 
    public java.lang.String getToken() {
        return this.token;
    }
    
    /**
     * 设置属性接口资源ID的值
     */ 
    public void setResourceId(java.lang.Integer resourceId) {
        this.resourceId = resourceId;
    }
    
    /**
     * 获取属性接口资源ID的值
     */ 
    public java.lang.Integer getResourceId() {
        return this.resourceId;
    }
    
    /**
     * 设置属性请求内容的值
     */ 
    public void setReqContent(java.lang.String reqContent) {
        this.reqContent = reqContent;
    }
    
    /**
     * 获取属性请求内容的值
     */ 
    public java.lang.String getReqContent() {
        return this.reqContent;
    }
    
    /**
     * 设置属性响应内容的值
     */ 
    public void setRespContent(java.lang.String respContent) {
        this.respContent = respContent;
    }
    
    /**
     * 获取属性响应内容的值
     */ 
    public java.lang.String getRespContent() {
        return this.respContent;
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