package com.yaic.fa.shiro.bind.annotation;

import com.yaic.app.Constants;

import java.lang.annotation.*;

/**
 * <p>绑定当前登录的用户 </p>
 * <p>不同于@ModelAttribute </p>
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default Constants.CURRENT_USER;

}
