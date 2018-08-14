/************************************************************************
 * 描述 ：数据库表CMS_ROLE对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
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

@Table(name = "app_role")
public class RoleDto implements Serializable {

    private static final long serialVersionUID = RoleDto.class.getName().hashCode();
    
    //有效标志：0无效，1 有效 */
    public static String VALIDFLAGTRUE = "1" ;
    public static String VALIDFLAGFALSE = "0" ;

    /** 角色ID */
    @Id
    private String roleId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 角色名称 */
    private String roleName;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /**
     * 设置属性角色ID的值
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取属性角色ID的值
     */     
    public String getRoleId() {
        return this.roleId;
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
     * 设置属性角色名称的值
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取属性角色名称的值
     */
    public String getRoleName() {
        return this.roleName;
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