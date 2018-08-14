package com.yaic.fa.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonResponse<T> implements Serializable {

    private static final long serialVersionUID = JsonResponse.class.hashCode();

    private T form;

    private PageDto<T> page;

    private Map<String, Object> extend;

    public JsonResponse() {
        extend = new HashMap<String, Object>();
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

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public void put(String key, Object value) {
        extend.put(key, value);
    }

    public void remove(String key) {
        extend.remove(key);
    }
}
