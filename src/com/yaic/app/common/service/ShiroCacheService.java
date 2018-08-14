package com.yaic.app.common.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.yaic.fa.log.Log;
import com.yaic.fa.shiro.realm.UserRealm;

/**
 * shiro缓存管理
 */
@Service
public class ShiroCacheService {

    @Log
    private Logger logger;
    
    /**
     * 修改权限时调用清缓存
     */
    public void clearCachedAuthorizationInfo(){
        Subject subject = SecurityUtils.getSubject();
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager(); 
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next(); 
        userRealm.clearCachedAuthorizationInfo(subject.getPrincipals());
        logger.info("clearCachedAuthorizationInfo刷新缓存！");
    }
    
    /**
     * 修改密码时调用清缓存
     */
    public void clearCachedAuthenticationInfo(){
        Subject subject = SecurityUtils.getSubject();
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager(); 
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next(); 
        userRealm.clearCachedAuthenticationInfo(subject.getPrincipals());
        logger.info("clearCachedAuthenticationInfo刷新缓存！");
    }
    
    /**
     * 清空当前用户所有缓存
     */
    public void clearCache(){
        Subject subject = SecurityUtils.getSubject();
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager(); 
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next(); 
        userRealm.clearCache(subject.getPrincipals());
        logger.info("clearCache刷新缓存！");
    }
    
    /***
     * 清空所有缓存
     */
    public void clearAllCache(){
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager(); 
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next(); 
        userRealm.clearAllCache();
        logger.info("clearAllCache刷新缓存！");
    }
    
    
}
