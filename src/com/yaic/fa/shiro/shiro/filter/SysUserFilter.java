package com.yaic.fa.shiro.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yaic.app.Constants;
import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.app.common.service.UMUserService;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UMUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String userCode = (String)SecurityUtils.getSubject().getPrincipal();
        UserDto userDto = new UserDto();
        userDto.setUserCode(userCode);
        userDto = userService.selectOne(userDto);
        
        request.setAttribute(Constants.CURRENT_USER, userDto);
        return true;
    }
}
