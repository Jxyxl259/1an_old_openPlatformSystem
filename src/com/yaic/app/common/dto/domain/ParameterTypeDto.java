/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER_TYPE对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
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

@Table(name = "app_parameter_type")
public class ParameterTypeDto implements Serializable {

    private static final long serialVersionUID = ParameterTypeDto.class.getName().hashCode();

    /** 参数类型ID */
    @Id
    private String parameterTypeId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 参数类型类型 */
    private String parameterType;

    /** 参数类型简体名称 */
    private String parameterTypeCname;

    /** 参数类型英文名称 */
    private String parameterTypeEname;

    /** 参数类型繁体名称 */
    private String parameterTypeTname;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性参数类型ID的值
     */
    public void setParameterTypeId(String parameterTypeId) {
        this.parameterTypeId = parameterTypeId;
    }

    /**
     * 获取属性参数类型ID的值
     */     
    public String getParameterTypeId() {
        return this.parameterTypeId;
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
     * 设置属性参数类型类型的值
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * 获取属性参数类型类型的值
     */
    public String getParameterType() {
        return this.parameterType;
    }

    /**
     * 设置属性参数类型简体名称的值
     */
    public void setParameterTypeCname(String parameterTypeCname) {
        this.parameterTypeCname = parameterTypeCname;
    }

    /**
     * 获取属性参数类型简体名称的值
     */
    public String getParameterTypeCname() {
        return this.parameterTypeCname;
    }

    /**
     * 设置属性参数类型英文名称的值
     */
    public void setParameterTypeEname(String parameterTypeEname) {
        this.parameterTypeEname = parameterTypeEname;
    }

    /**
     * 获取属性参数类型英文名称的值
     */
    public String getParameterTypeEname() {
        return this.parameterTypeEname;
    }

    /**
     * 设置属性参数类型繁体名称的值
     */
    public void setParameterTypeTname(String parameterTypeTname) {
        this.parameterTypeTname = parameterTypeTname;
    }

    /**
     * 获取属性参数类型繁体名称的值
     */
    public String getParameterTypeTname() {
        return this.parameterTypeTname;
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