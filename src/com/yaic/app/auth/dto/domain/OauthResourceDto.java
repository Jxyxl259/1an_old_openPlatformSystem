/*
 * Created By lujicong (2016-04-27 16:36:56)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.dto.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Table(name = "t_sns_oauth_resource")
public class OauthResourceDto implements Serializable {
    
    private static final long serialVersionUID = OauthResourceDto.class.getName().hashCode();
    
    /** id */ 
    @Id
    private java.lang.Integer id;
    
    /** app_id */ 
    private java.lang.String appId;
    
    /** resource_id */ 
    private java.lang.Integer resourceId;
    
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
    
    @Transient
    private java.lang.String idStr;
    
    @Transient
    private java.lang.String resourceIdStr;
    
    @Transient
    private java.lang.String requestUrl;
    
    @Transient
    private java.lang.String interfaceName;
    
    @Transient
    private java.lang.String orgId;
    
    @Transient
    private java.lang.String agrtCode;
    
    /**
     * 设置属性id的值
     */ 
    public void setId(java.lang.Integer id) {
        this.id = id;
        this.idStr = String.valueOf(id);
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
    
    public java.lang.Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(java.lang.Integer resourceId) {
        this.resourceId = resourceId;
        this.resourceIdStr = String.valueOf(resourceId);
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
    
    public java.lang.String getIdStr() {
        return idStr;
    }

    public void setIdStr(java.lang.String idStr) {
        this.idStr = idStr;
    }

    public java.lang.String getResourceIdStr() {
        return resourceIdStr;
    }

    public void setResourceIdStr(java.lang.String resourceIdStr) {
        this.resourceIdStr = resourceIdStr;
    }

    public java.lang.String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(java.lang.String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public java.lang.String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(java.lang.String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public java.lang.String getOrgId() {
        return orgId;
    }

    public void setOrgId(java.lang.String orgId) {
        this.orgId = orgId;
    }

    public java.lang.String getAgrtCode() {
        return agrtCode;
    }

    public void setAgrtCode(java.lang.String agrtCode) {
        this.agrtCode = agrtCode;
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