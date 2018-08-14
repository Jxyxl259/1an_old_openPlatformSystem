package com.yaic.app.callback.dto;

public class ResultDto {
    
    /** 应答码*/
    private String code;
    /** 应答信息 */
    private String message;
    /** 状态标识码 */
    private String statusCode;
    
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
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
    
}
