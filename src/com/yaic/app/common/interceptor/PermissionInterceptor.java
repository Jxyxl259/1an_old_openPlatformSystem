package com.yaic.app.common.interceptor;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yaic.app.Constants;
import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.app.common.service.ResourceService;
import com.yaic.app.common.service.UMUserService;
import com.yaic.fa.log.Log;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private ArrayList<String> ignoreUrlList = null;

    private ArrayList<String> ignoreFileTypeList = null;

    private String ignoreUrl;

    private String ignoreFileType;

    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private UMUserService userService;
    
    @Log
    private Logger logger;

    public PermissionInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if (ignoreUrlList == null) {
            ignoreUrlList = new ArrayList<String>();
            ignoreUrlList.addAll(Arrays.asList(getIgnoreUrl().split(",")));
        }

        if (ignoreFileTypeList == null) {
            ignoreFileTypeList = new ArrayList<String>();
            ignoreFileTypeList.addAll(Arrays.asList(getIgnoreFileType().split(",")));
        }

        String url = request.getRequestURI();

        // 权限校验。判断是否包含权限。
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        // 已经登陆，多浏览器窗口打开 ，session是空， 在登陆情况下，清空浏览器缓存后刷新，将跳转到登陆页面
        if ((subject.isAuthenticated() || subject.isRemembered()) && session.getAttribute(Constants.CURRENT_USER) == null) {
            String userCode = (String) subject.getPrincipal();
            UserDto record = new UserDto();
            record.setUserCode(userCode);
            UserDto user = userService.selectOne(record);
            session.setAttribute(Constants.CURRENT_USER, user);
            session.setAttribute(Constants.LOGIN_USER_ID_KEY, user.getUserCode());
        }

        logger.info("url:" + url);
        logger.info("query string:" + request.getQueryString());
        for (String fileType : ignoreFileTypeList) {
            if (url.toLowerCase().endsWith(fileType)) {
                logger.info("ingore file type [" + fileType + "]:" + url);
                return true;
            }
        }

        if (ignoreUrlList.contains(url)) {
            logger.info("ingore url:" + url);
            return true;
        }

        // 登陆或退出
        if (url.contains("login")) {
            return true;
        }
        
        // 具体响应ShiroDbRealm。doGetAuthorizationInfo，判断是否包含此url的响应权限
        //String configUrl = url.replaceAll("/cms/", "");
        //subject.checkPermission(configUrl);//校验是否有权限，
        return true;
    }

    public String getIgnoreFileType() {
        return ignoreFileType;
    }

    public void setIgnoreFileType(String ignoreFileType) {
        this.ignoreFileType = ignoreFileType;
    }

    public String getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(String ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
