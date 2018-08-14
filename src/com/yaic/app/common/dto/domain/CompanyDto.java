/************************************************************************
 * 描述 ：数据库表CMS_COMPANY对应的DTO，代码生成。
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

@Table(name = "app_company")
public class CompanyDto implements Serializable {

    private static final long serialVersionUID = CompanyDto.class.getName().hashCode();

    /** 机构ID */
    @Id
    private String companyId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 机构简体名称 */
    private String companyCname;

    /** 机构英文名称 */
    private String companyEname;

    /** 机构繁体名称 */
    private String companyTname;

    /** 菜单层级 */
    private Integer companyLevel;

    /** 上级菜单ID */
    private String parentCompanyId;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性机构ID的值
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取属性机构ID的值
     */     
    public String getCompanyId() {
        return this.companyId;
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
     * 设置属性机构简体名称的值
     */
    public void setCompanyCname(String companyCname) {
        this.companyCname = companyCname;
    }

    /**
     * 获取属性机构简体名称的值
     */
    public String getCompanyCname() {
        return this.companyCname;
    }

    /**
     * 设置属性机构英文名称的值
     */
    public void setCompanyEname(String companyEname) {
        this.companyEname = companyEname;
    }

    /**
     * 获取属性机构英文名称的值
     */
    public String getCompanyEname() {
        return this.companyEname;
    }

    /**
     * 设置属性机构繁体名称的值
     */
    public void setCompanyTname(String companyTname) {
        this.companyTname = companyTname;
    }

    /**
     * 获取属性机构繁体名称的值
     */
    public String getCompanyTname() {
        return this.companyTname;
    }

    /**
     * 设置属性菜单层级的值
     */
    public void setCompanyLevel(Integer companyLevel) {
        this.companyLevel = companyLevel;
    }

    /**
     * 获取属性菜单层级的值
     */
    public Integer getCompanyLevel() {
        return this.companyLevel;
    }

    /**
     * 设置属性上级菜单ID的值
     */
    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    /**
     * 获取属性上级菜单ID的值
     */
    public String getParentCompanyId() {
        return this.parentCompanyId;
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