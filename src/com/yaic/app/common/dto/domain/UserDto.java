/************************************************************************
 * 描述 ：数据库表CMS_USER对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dto.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "app_user")  
public class UserDto implements Serializable {

    private static final long serialVersionUID = UserDto.class.getName().hashCode();

    /** 员工ID */
    @Id
    private String userId;
 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;


    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 员工代码 */
    private String userCode;

    /** 员工简体中文名称 */
    private String userCname;

    /** 员工繁体中文名称 */
    private String userTname;

    /** 员工英文名称 */
    private String userEname;

    /** 密码 */
    private String password;

    /** 印鉴 */
    private String seal;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
    /** 密码设置日期 */
    private Date passwordSetDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
    /** 密码过期日期 */
    private Date passwordExpireDate;

    /** 机构代码 */
    private String companyCode;

    /** 电话号码 */
    private String phone;

    /** 手机号码 */
    private String mobile;

    /** 通信地址 */
    private String address;

    /** 邮政编码 */
    private String postCode;

    /** 邮箱 */
    private String email;

    /** 1-有效；0-无效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 标志字段 */
    private String flag;

    /** 加密密码的盐 */
    private String salt;
    
    /**
     * 设置属性员工ID的值
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取属性员工ID的值
     */     
    public String getUserId() {
        return this.userId;
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
     * 设置属性员工代码的值
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取属性员工代码的值
     */
    public String getUserCode() {
        return this.userCode;
    }

    /**
     * 设置属性员工简体中文名称的值
     */
    public void setUserCname(String userCname) {
        this.userCname = userCname;
    }

    /**
     * 获取属性员工简体中文名称的值
     */
    public String getUserCname() {
        return this.userCname;
    }

    /**
     * 设置属性员工繁体中文名称的值
     */
    public void setUserTname(String userTname) {
        this.userTname = userTname;
    }

    /**
     * 获取属性员工繁体中文名称的值
     */
    public String getUserTname() {
        return this.userTname;
    }

    /**
     * 设置属性员工英文名称的值
     */
    public void setUserEname(String userEname) {
        this.userEname = userEname;
    }

    /**
     * 获取属性员工英文名称的值
     */
    public String getUserEname() {
        return this.userEname;
    }

    /**
     * 设置属性密码的值
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取属性密码的值
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置属性印鉴的值
     */
    public void setSeal(String seal) {
        this.seal = seal;
    }

    /**
     * 获取属性印鉴的值
     */
    public String getSeal() {
        return this.seal;
    }

    /**
     * 设置属性密码设置日期的值
     */
    public void setPasswordSetDate(Date passwordSetDate) {
        this.passwordSetDate = passwordSetDate;
    }

    /**
     * 获取属性密码设置日期的值
     */
    public Date getPasswordSetDate() {
        return this.passwordSetDate;
    }

    /**
     * 设置属性密码过期日期的值
     */
    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    /**
     * 获取属性密码过期日期的值
     */
    public Date getPasswordExpireDate() {
        return this.passwordExpireDate;
    }

    /**
     * 设置属性机构代码的值
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 获取属性机构代码的值
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * 设置属性电话号码的值
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取属性电话号码的值
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置属性手机号码的值
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取属性手机号码的值
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置属性通信地址的值
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取属性通信地址的值
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置属性邮政编码的值
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * 获取属性邮政编码的值
     */
    public String getPostCode() {
        return this.postCode;
    }

    /**
     * 设置属性邮箱的值
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取属性邮箱的值
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 设置属性1-有效；0-无效的值
     */
    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * 获取属性1-有效；0-无效的值
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
     * 设置属性标志字段的值
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 获取属性标志字段的值
     */
    public String getFlag() {
        return this.flag;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getCredentialsSalt() {
        return userCode + salt;
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