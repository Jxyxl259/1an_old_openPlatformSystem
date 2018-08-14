package com.yaic.fa.shiro.modules;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展默认的用户认证的bean
 * <p>Author:lujicong
 * <p>Date: 2015-12-9
 * <p>Version: 1.0
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public UsernamePasswordCaptchaToken() {
		super();

	}

	public UsernamePasswordCaptchaToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public UsernamePasswordCaptchaToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		this(username, password == null ? null : password.toCharArray(),
				rememberMe, host, captcha);
	}

}
