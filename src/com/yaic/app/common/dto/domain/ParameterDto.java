/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER对应的DTO，代码生成。
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

@Table(name = "app_parameter")
public class ParameterDto implements Serializable {

    private static final long serialVersionUID = ParameterDto.class.getName().hashCode();

    /** 参数ID */
    @Id
    private String parameterId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 参数类型 */
    private String parameterType;

    /** 参数代码 */
    private String parameterCode;

    /** 参数简体名称 */
    private String parameterCname;

    /** 参数英文名称 */
    private String parameterEname;

    /** 参数繁体名称 */
    private String parameterTname;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性参数ID的值
     */
    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    /**
     * 获取属性参数ID的值
     */     
    public String getParameterId() {
        return this.parameterId;
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
     * 设置属性参数类型的值
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * 获取属性参数类型的值
     */
    public String getParameterType() {
        return this.parameterType;
    }

    /**
     * 设置属性参数代码的值
     */
    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    /**
     * 获取属性参数代码的值
     */
    public String getParameterCode() {
        return this.parameterCode;
    }

    /**
     * 设置属性参数简体名称的值
     */
    public void setParameterCname(String parameterCname) {
        this.parameterCname = parameterCname;
    }

    /**
     * 获取属性参数简体名称的值
     */
    public String getParameterCname() {
        return this.parameterCname;
    }

    /**
     * 设置属性参数英文名称的值
     */
    public void setParameterEname(String parameterEname) {
        this.parameterEname = parameterEname;
    }

    /**
     * 获取属性参数英文名称的值
     */
    public String getParameterEname() {
        return this.parameterEname;
    }

    /**
     * 设置属性参数繁体名称的值
     */
    public void setParameterTname(String parameterTname) {
        this.parameterTname = parameterTname;
    }

    /**
     * 获取属性参数繁体名称的值
     */
    public String getParameterTname() {
        return this.parameterTname;
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