package com.yaic.app.common.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.app.common.util.XssShieldUtil;

/**
 * <p>User: Qu Dihuai
 * <p>Date: 2018-01-09
 */
public class SecureHttpServletRequest extends HttpServletRequestWrapper {
	private final static Logger LOG = LoggerFactory.getLogger(SecureHttpServletRequest.class);

	public SecureHttpServletRequest(final HttpServletRequest request) {
		super(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final String requestBody =  getRequestBody(super.getInputStream());
		if (StringUtils.isBlank(requestBody)) {
			return null;
		}
		
		final String secureRequestBody = XssShieldUtil.stripXssChar(requestBody);
		final byte[] bodyArray = secureRequestBody.getBytes(StandardCharsets.UTF_8);
		final ByteArrayInputStream bais = new ByteArrayInputStream(bodyArray);
		
		try {
			return new ServletInputStream() {
				@Override
				public int read() throws IOException {
					return bais.read();
				}
			};
		} finally {
			IOUtils.closeQuietly(bais);
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		final Map<String, String[]> parameterMap = super.getParameterMap();
		
		int size;
		if (parameterMap == null || (size = parameterMap.size()) == 0) {
			return parameterMap;
		}
		
		String targetKey;
		String[] targetValue;
		
		final Map<String, String[]> map = new HashMap<>(size);
		for (final Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry != null) {
				targetKey = XssShieldUtil.stripXssChar(entry.getKey());
				targetValue = stripXssCharForArray(entry.getValue());
				map.put(targetKey, targetValue);
			}
		}
		
		return map;
	}

	@Override
	public String getHeader(final String name) {
		final String source = super.getHeader(name);
		final String target = XssShieldUtil.stripXssChar(source);
		return target;
	}

	@Override
	public String getParameter(final String name) {
		final String source = super.getParameter(name);
		final String target = XssShieldUtil.stripXssChar(source);
		return target;
	}
	
	@Override
	public String[] getParameterValues(final String name) {
		final String[] source = super.getParameterValues(name);
		final String[] target = stripXssCharForArray(source);
		return target;
	}

	private String[] stripXssCharForArray(final String[] source) {
		int len;
		if (source == null || (len = source.length) == 0) {
			return source;
		}
		
		final String[] target = new String[len];
		for (int i = 0; i < len; i++) {
			target[i] = XssShieldUtil.stripXssChar(source[i]);
		}
		
		return target;
	}

	private String getRequestBody(final ServletInputStream inputStream) {
		try {
			final List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8.name());
			if (CollectionUtils.isEmpty(lines)) {
				return null;
			}
			
			return appendLines(lines);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
		}
		
		return null;
	}

	private String appendLines(final List<String> lines) {
		final StringBuilder buffer = new StringBuilder();
		for (final String line : lines) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}
