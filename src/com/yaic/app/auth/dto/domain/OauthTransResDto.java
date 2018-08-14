/*
 * Created By lujicong (2016-05-03 14:19:38)
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

@Table(name = "t_sns_oauth_trans_res")
public class OauthTransResDto implements Serializable {
    
    private static final long serialVersionUID = OauthTransResDto.class.getName().hashCode();
    
    /** id */ 
    @Id
    private java.lang.Integer id;
    
    /** 接口名称 */
    private java.lang.String interfaceName;
    
    /** 请求资源URL */ 
    private java.lang.String requestUrl;
    
    /** 转换资源URL */ 
    private java.lang.String transUrl;
    
    /** 编码类型 */ 
    private java.lang.String encodingType;
    
    /** 内容格式 */
    private java.lang.String accept;
    
    /** 0:不需要;1:需要 */ 
    private Integer isAuth;
    
    /** 合作机构编码 */
    private java.lang.String orgId;
    
    /** 协议号 */
    private java.lang.String agrtCode;
    
    /** 用户ID(来源于用户中心) */
    private java.lang.String userId;
    
    /** 备注描述 */ 
    private java.lang.String remark;
    
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
    
    /** 是否带参数名:0:否,1:是 */ 
    private Integer isPair;
    
    /** 允许请求IP(多个英文分号;分隔) */ 
    private String allowIp;
    
    /** 是否模糊查询 0-否，1-是 */
    private Integer fuzzy;

	/** 是否加密(如果加密就不走鉴权) 0-否,1-是 */ 
    private String isEncrypt;
    
    /** 加密方式(不加密忽略该配置)*/ 
    private String encryptType;
    
    /** 加密KEY*/ 
    private String encryptKey;
    
    @Transient
    private String appId;
    
    @Transient
    private String idStr;
    
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
    
    public java.lang.String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(java.lang.String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * 设置属性资源URL的值
     */ 
    public void setRequestUrl(java.lang.String requestUrl) {
        this.requestUrl = requestUrl;
    }
    
    /**
     * 获取属性资源URL的值
     */ 
    public java.lang.String getRequestUrl() {
        return this.requestUrl;
    }
    
    /**
     * 设置属性资源URL的值
     */ 
    public void setTransUrl(java.lang.String transUrl) {
        this.transUrl = transUrl;
    }
    
    /**
     * 获取属性资源URL的值
     */ 
    public java.lang.String getTransUrl() {
        return this.transUrl;
    }
    
    /**
     * 设置属性编码类型的值
     */ 
    public void setEncodingType(java.lang.String encodingType) {
        this.encodingType = encodingType;
    }
    
    /**
     * 获取属性编码类型的值
     */ 
    public java.lang.String getEncodingType() {
        return this.encodingType;
    }
    
    public java.lang.String getAccept() {
        return accept;
    }

    public void setAccept(java.lang.String accept) {
        this.accept = accept;
    }

    /**
     * 设置属性0:不需要;1:需要的值
     */ 
    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }
    
    /**
     * 获取属性0:不需要;1:需要的值
     */ 
    public Integer getIsAuth() {
        return this.isAuth;
    }
    
    /**
     * 获取属性合作机构编码的值
     */
    public java.lang.String getOrgId() {
        return orgId;
    }

    /**
     * 设置属性合作机构编码的值
     */ 
    public void setOrgId(java.lang.String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取属性协议号的值
     */ 
    public java.lang.String getAgrtCode() {
        return agrtCode;
    }

    /**
     * 设置属性协议号的值
     */ 
    public void setAgrtCode(java.lang.String agrtCode) {
        this.agrtCode = agrtCode;
    }

    /**
     * 获取属性用户ID(来源于用户中心)的值
     */ 
    public java.lang.String getUserId() {
        return userId;
    }

    /**
     * 设置属性用户ID(来源于用户中心)的值
     */ 
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    /**
     * 获取属性备注的值
     */ 
    public java.lang.String getRemark() {
        return remark;
    }

    /**
     * 设置属性备注的值
     */ 
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
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
    
    /**
     * 设置属性是否带参数名:0:否,1:是的值
     */ 
    public void setIsPair(Integer isPair) {
        this.isPair = isPair;
    }
    
    /**
     * 获取属性是否带参数名:0:否,1:是的值
     */ 
    public Integer getIsPair() {
        return isPair;
    }

    /**
     * 设置属性允许请求IP(多个英文分号;分隔)的值
     */ 
    public void setAllowIp(String allowIp) {
        this.allowIp = allowIp;
    }
    
    /**
     * 获取属性允许请求IP(多个英文分号;分隔)的值
     */ 
    public String getAllowIp() {
        return allowIp;
    }
    
    /** 是否模糊查询 0-否，1-是 */    
    public Integer getFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(Integer fuzzy) {
		this.fuzzy = fuzzy;
	}

    /**
     * 设置属性是否加密(如果加密就不走鉴权)0-否,1-是的值
     */ 
    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    /**
     * 获取属性是否加密(如果加密就不走鉴权)0-否,1-是的值
     */ 
    public String getIsEncrypt() {
        return isEncrypt;
    }
    
    /**
     * 设置属性加密方式(不加密忽略该配置)的值
     */ 
    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }
    
    /**
     * 获取属性加密方式(不加密忽略该配置)的值
     */ 
    public String getEncryptType() {
        return encryptType;
    }

    /**
     * 设置属性加密KEY的值
     */ 
    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }
    
    /**
     * 获取属性加密KEY的值
     */ 
    public String getEncryptKey() {
        return encryptKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
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