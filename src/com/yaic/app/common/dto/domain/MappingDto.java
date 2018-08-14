/************************************************************************
 * 描述 ：数据库表CMS_MAPPING对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-12-15 09:29:44
 *
 ************************************************************************/

package com.yaic.app.common.dto.domain;

import java.util.Date;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Table(name = "app_mapping")
public class MappingDto implements Serializable {

    private static final long serialVersionUID = MappingDto.class.getName().hashCode();

    /** 代码转换ID */
    @Id
    private String mappingId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 代码转换类型 */
    private String mappingType;

    /** 代码转换名称 */
    private String mappingName;

    /** 代码转换源 */
    private String mappingFrom;

    /** 代码转换目标 */
    private String mappingTo;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性代码转换ID的值
     */
    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    /**
     * 获取属性代码转换ID的值
     */     
    public String getMappingId() {
        return this.mappingId;
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
     * 设置属性代码转换类型的值
     */
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }

    /**
     * 获取属性代码转换类型的值
     */
    public String getMappingType() {
        return this.mappingType;
    }

    /**
     * 设置属性代码转换名称的值
     */
    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    /**
     * 获取属性代码转换名称的值
     */
    public String getMappingName() {
        return this.mappingName;
    }

    /**
     * 设置属性代码转换源的值
     */
    public void setMappingFrom(String mappingFrom) {
        this.mappingFrom = mappingFrom;
    }

    /**
     * 获取属性代码转换源的值
     */
    public String getMappingFrom() {
        return this.mappingFrom;
    }

    /**
     * 设置属性代码转换目标的值
     */
    public void setMappingTo(String mappingTo) {
        this.mappingTo = mappingTo;
    }

    /**
     * 获取属性代码转换目标的值
     */
    public String getMappingTo() {
        return this.mappingTo;
    }

    /**
     * 设置属性是否有效：0无效，1 有效的值
     */
    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * 获取属性是否有效：0无效，1 有效的值
     */
    public String getValidFlag() {
        return this.validFlag;
    }

    /**
     * 设置属性备注的值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取属性备注的值
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