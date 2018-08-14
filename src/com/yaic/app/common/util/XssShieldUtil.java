package com.yaic.app.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yaic.app.common.enums.XssShieldPatternEnum;

/**
 * 处理非法字符
 * 
 * <p>
 * User: Qu Dihuai
 * <p>
 * Date: 2018-01-10
 */
public class XssShieldUtil {
	
	/**
	 * @param string
	 * 
	 * <pre> 处理非法字符： </pre>
	 * <pre> "<" 替换为"&lt;", ">"替换为"&gt;"</pre>
	 * <pre> 其他非法字符全部替换为空字符串</pre>
	 */
	public static String stripXssChar(String arg) {
		if (StringUtils.isBlank(arg)) {
			return arg;
		}
		
		Matcher matcher = null;
		for (final Pattern pattern : XssShieldPattern.getPatterns()) {
			matcher = pattern.matcher(arg);
			if (matcher.find()) {
				arg = matcher.replaceAll(StringUtils.EMPTY);
			}
		}
		
		return arg.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	/**
	 * @param string
	 * 
	 * <pre> 判断是否包含非法字符 </pre>
	 */
	public static boolean containsXssChar(final String string) {
		if (StringUtils.isBlank(string)) {
			return false;
		}
		
		Matcher matcher = null;
		for (final Pattern pattern : XssShieldPattern.getPatterns()) {
			matcher = pattern.matcher(string);
			if (matcher.find()) {
				return true;
			}
		}
		
		return false;
	}
	
	private final static class XssShieldPattern {
		private static volatile List<Pattern> PATTERNS;
		
		static {
			PATTERNS = new ArrayList<>();
			for (final XssShieldPatternEnum xssShieldPattern : XssShieldPatternEnum.values()) {
				PATTERNS.add(xssShieldPattern.pattern());
			}
		}
		
		protected static List<Pattern> getPatterns() {
			return PATTERNS;
		}
	}
}