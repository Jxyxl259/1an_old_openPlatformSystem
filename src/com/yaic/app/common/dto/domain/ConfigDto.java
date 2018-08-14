/************************************************************************
 * 描述 ：数据库表CMS_CONFIG对应的DTO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
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

@Table(name = "app_config")
public class ConfigDto implements Serializable {

    private static final long serialVersionUID = ConfigDto.class.getName().hashCode();

    /** 配置ID */
    @Id
    private String configId;
 

    /** 创建日期 */
    private Date createdDate;

    /** 创建人 */
    private String createdBy;

    /** 更新日期 */
    private Date updatedDate;

    /** 更新人 */
    private String updatedBy;

    /** 配置代码 */
    private String configCode;

    /** 配置名称 */
    private String configName;

    /** 配置条件1 */
    private String condition1;

    /** 配置条件2 */
    private String condition2;

    /** 配置条件3 */
    private String condition3;

    /** 配置条件4 */
    private String condition4;

    /** 配置条件5 */
    private String condition5;

    /** 配置值 */
    private String configValue;

    /** 是否有效：0无效，1 有效 */
    private String validFlag;

    /** 备注 */
    private String remark;

    /** 其他标志 */
    private String flag;

    /**
     * 设置属性配置ID的值
     */
    public void setConfigId(String configId) {
        this.configId = configId;
    }

    /**
     * 获取属性配置ID的值
     */     
    public String getConfigId() {
        return this.configId;
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
     * 设置属性配置代码的值
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    /**
     * 获取属性配置代码的值
     */
    public String getConfigCode() {
        return this.configCode;
    }

    /**
     * 设置属性配置名称的值
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取属性配置名称的值
     */
    public String getConfigName() {
        return this.configName;
    }

    /**
     * 设置属性配置条件1的值
     */
    public void setCondition1(String condition1) {
        this.condition1 = condition1;
    }

    /**
     * 获取属性配置条件1的值
     */
    public String getCondition1() {
        return this.condition1;
    }

    /**
     * 设置属性配置条件2的值
     */
    public void setCondition2(String condition2) {
        this.condition2 = condition2;
    }

    /**
     * 获取属性配置条件2的值
     */
    public String getCondition2() {
        return this.condition2;
    }

    /**
     * 设置属性配置条件3的值
     */
    public void setCondition3(String condition3) {
        this.condition3 = condition3;
    }

    /**
     * 获取属性配置条件3的值
     */
    public String getCondition3() {
        return this.condition3;
    }

    /**
     * 设置属性配置条件4的值
     */
    public void setCondition4(String condition4) {
        this.condition4 = condition4;
    }

    /**
     * 获取属性配置条件4的值
     */
    public String getCondition4() {
        return this.condition4;
    }

    /**
     * 设置属性配置条件5的值
     */
    public void setCondition5(String condition5) {
        this.condition5 = condition5;
    }

    /**
     * 获取属性配置条件5的值
     */
    public String getCondition5() {
        return this.condition5;
    }

    /**
     * 设置属性配置值的值
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    /**
     * 获取属性配置值的值
     */
    public String getConfigValue() {
        return this.configValue;
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