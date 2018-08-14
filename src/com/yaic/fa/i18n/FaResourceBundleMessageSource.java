package com.yaic.fa.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.support.ResourceBundleMessageSource;

public class FaResourceBundleMessageSource extends ResourceBundleMessageSource {

    private static final String ENCODING = "UTF-8";

    Map<String, String> encodingCache = new ConcurrentHashMap<String, String>(20);

    private String defaultEncoding = "ISO-8859-1";

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        super.setDefaultEncoding(defaultEncoding);
        this.defaultEncoding = defaultEncoding;
    }

    public FaResourceBundleMessageSource() {
        super();
    }

    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String message = super.resolveCodeWithoutArguments(code, locale);
        if (!getDefaultEncoding().equalsIgnoreCase(ENCODING)) {
            return decodeString(message, ENCODING);
        }
        return message;
    }

    protected MessageFormat createMessageFormat(String msg, Locale locale) {
        if (!getDefaultEncoding().equalsIgnoreCase(ENCODING)) {
            msg = decodeString(msg, ENCODING);
        }
        return new MessageFormat((msg != null ? msg : ""), locale);
    }

    private String decodeString(String message, String encode) {
        String encodMessage = encodingCache.get(message);
        if (encodMessage == null) {
            try {
                encodMessage = new String(message.getBytes(getDefaultEncoding()), ENCODING);
                encodingCache.put(message, encodMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encodMessage;
    }
}
