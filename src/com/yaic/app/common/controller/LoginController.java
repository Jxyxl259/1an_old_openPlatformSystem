package com.yaic.app.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.fa.log.Log;

@Controller
@RequestMapping("/")
public class LoginController {

    @Log
    private Logger logger;

    @RequestMapping(value = {"/","/index"})
    public String index(UserDto user , Model model) throws Exception {
        logger.info("enter index page.");
        return "index";
    }
    
    /**
     * 无权限页面跳转
     */
    @RequestMapping(value = {"/unauthorized"})
    public String unauthorized(UserDto user , Model model) throws Exception {
        logger.info("enter unauthorized page.");
        return "unauthorized";
    }
    
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) throws Exception {
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(LockedAccountException.class.getName().equals(exceptionClassName)){
        	error = "账户被锁定，请联系管理员解锁" ;
        }else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登陆错误次数已超过5次,账户被锁定" ;
        }
        if(request.getParameter("forceLogout") != null) {
            model.addAttribute("error", "您已经被管理员强制退出，请重新登录");
        }
        model.addAttribute("userCode", request.getParameter("username"));
        model.addAttribute("error", error);
        return "login";
    }
    
    
}
