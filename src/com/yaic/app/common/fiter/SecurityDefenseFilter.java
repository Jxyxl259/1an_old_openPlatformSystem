package com.yaic.app.common.fiter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.app.common.http.SecureHttpServletRequest;

/**
 * <p> 主要功能为安全防护, 如xss漏洞、SQL注入等
 * 
 * <p> User: Qu Dihuai
 * 
 * <p> Date: 2018-01-09
 */
public class SecurityDefenseFilter implements Filter {
	private final static Logger LOG = LoggerFactory.getLogger(SecurityDefenseFilter.class);
	
	private String[] ignoreSecurityCheckUrls;

	@Override
	public void init(final FilterConfig config) throws ServletException {
		final String ignoreSecurityCheckUrlsString = config.getInitParameter("ignoreSecurityCheckUrls");
		if (StringUtils.isNotBlank(ignoreSecurityCheckUrlsString)) {
			// 在web.xml中配置参数ignoreSecurityCheckUrls, 它的每个url是以分号符号隔开的
			this.ignoreSecurityCheckUrls = ignoreSecurityCheckUrlsString.split(";");
		}
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		final String requestURI = httpServletRequest.getRequestURI();
		if (ignoreSecurityCheck(requestURI)) {
			final HttpServletRequest secureHttpServletRequest = new SecureHttpServletRequest(httpServletRequest);
			chain.doFilter(secureHttpServletRequest, httpServletResponse);
			return;
		}
		LOG.info("Ignore the security check for requestURI '{}'.", requestURI);
		chain.doFilter(httpServletRequest, httpServletResponse);
		
		
	}

	@Override
	public void destroy() {
		
	}

	private boolean ignoreSecurityCheck(final String uri) {
		if (ArrayUtils.isEmpty(ignoreSecurityCheckUrls)) {
			return false;
		}
		
		for (final String configuredUrl : ignoreSecurityCheckUrls) {
			if (StringUtils.isNotBlank(configuredUrl) && uri.contains(configuredUrl)) {
				return true;
			}
		}
		
		return false;
	}
}
