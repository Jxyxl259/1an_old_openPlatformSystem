package com.yaic.fa.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonRequest<T> implements Serializable {

    private static final long serialVersionUID = JsonRequest.class.hashCode();

    private T form;

    private PageDto<T> page;

    private Map<String, String> extend;

    public JsonRequest() {
        extend = new HashMap<String, String>();
    }

    public T getForm() {
        return form;
    }

    public void setForm(T form) {
        this.form = form;
    }

    public PageDto<T> getPage() {
        return page;
    }

    public void setPage(PageDto<T> page) {
        this.page = page;
    }

    public Map<String, String> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, String> extend) {
        this.extend = extend;
    }
}
