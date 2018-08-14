/************************************************************************
 * 描述 ：数据库表CMS_RESOURCE对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-10-22 09:44:48
 *
 ************************************************************************/

package com.yaic.app.common.dto.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yaic.fa.annotation.OrderByIndex;

@Table(name = "app_resource")
public class ResourceDto implements Serializable {

    private static final long serialVersionUID = ResourceDto.class.getName().hashCode();

    /** 资源ID */
    @Id
    @OrderBy("")
    @OrderByIndex(2)
    private String resourceId;

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 资源名称 */
    private String resourceName;

    /** 资源类型 */
    private String resourceType;

    /** 资源层级 */
    private Integer resourceLevel;

    /** 上级资源ID */
    @OrderBy("")
    @OrderByIndex(2)
    private String parentResourceId;

    /** 资源图标CLASS */
    private String resourceIconClass;

    /** 提交URL */
    private String actionUrl;

    /** 节点标志 */
    private String endFlag;
    
    /**显示顺序*/
    private Integer displayOrder;
    
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
     * 设置属性资源ID的值
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 获取属性资源ID的值
     */     
    public String getResourceId() {
        return this.resourceId;
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
     * 设置属性资源名称的值
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 获取属性资源名称的值
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * 设置属性资源类型的值
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取属性资源类型的值
     */
    public String getResourceType() {
        return this.resourceType;
    }

    /**
     * 设置属性资源层级的值
     */
    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    /**
     * 获取属性资源层级的值
     */
    public Integer getResourceLevel() {
        return this.resourceLevel;
    }

    /**
     * 设置属性上级资源ID的值
     */
    public void setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    /**
     * 获取属性上级资源ID的值
     */
    public String getParentResourceId() {
        return this.parentResourceId;
    }

    /**
     * 设置属性资源图标CLASS的值
     */
    public void setResourceIconClass(String resourceIconClass) {
        this.resourceIconClass = resourceIconClass;
    }

    /**
     * 获取属性资源图标CLASS的值
     */
    public String getResourceIconClass() {
        return this.resourceIconClass;
    }

    /**
     * 设置属性提交URL的值
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    /**
     * 获取属性提交URL的值
     */
    public String getActionUrl() {
        return this.actionUrl;
    }

    /**
     * 设置属性节点标志的值
     */
    public void setEndFlag(String endFlag) {
        this.endFlag = endFlag;
    }

    /**
     * 获取属性节点标志的值
     */
    public String getEndFlag() {
        return this.endFlag;
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