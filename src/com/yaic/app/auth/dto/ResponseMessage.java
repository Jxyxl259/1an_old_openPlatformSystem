package com.yaic.app.auth.dto;

public class ResponseMessage {
    private String code;
    private String message;
    private String state;
    private RespBodyData data = new RespBodyData();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public RespBodyData getData() {
        return data;
    }

    public void setData(RespBodyData data) {
        this.data = data;
    }
}
