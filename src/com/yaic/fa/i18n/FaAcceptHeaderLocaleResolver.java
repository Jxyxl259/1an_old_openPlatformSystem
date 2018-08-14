package com.yaic.fa.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class FaAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    private Locale locale;

    public Locale resolveLocale(HttpServletRequest request) {
        return locale;
    }

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        this.locale = locale;
    }
}
