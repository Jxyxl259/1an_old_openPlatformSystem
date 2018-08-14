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

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.digest.MD5Helper;
import com.yaic.servicelayer.util.HttpHelper;
import com.yaic.servicelayer.util.StringUtil;


public class TokenFiter implements Filter {

    public TokenFiter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String url = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        String serviceUrl = queryString == null ? url : url + "?" + queryString;

        String httpMethod = httpServletRequest.getMethod();

        String systemName = httpServletRequest.getHeader("System-Code");
        String token = httpServletRequest.getHeader("Token");

        if (StringUtil.isNotEmpty(token)) {
            if (!systemName.equalsIgnoreCase("CMS")) {
                httpServletResponse.sendRedirect("/cms/logout");
            }
            if ("post".equalsIgnoreCase(httpMethod)) {
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
                String body = HttpHelper.getBodyString(requestWrapper);
                String serverToken = MD5Helper.sign("token", serviceUrl + body, StandardCharset.UTF_8);
                System.out.println("POST==>Token:[" + token + "] ServerToken:[" + serverToken+"]");
                if (!token.equals(serverToken)) {
                    httpServletResponse.sendRedirect("/cms/logout");
                    return;
                }
                chain.doFilter(requestWrapper, response);
                return;
            } else if ("get".equalsIgnoreCase(httpMethod)) {
                String serverToken = MD5Helper.sign("token", serviceUrl, StandardCharset.UTF_8);
                System.out.println("GET==>Token:[" + token + "] ServerToken:[" + serverToken+"]");
                if (!token.equals(serverToken)) {
                    httpServletResponse.sendRedirect("/cms/logout");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
