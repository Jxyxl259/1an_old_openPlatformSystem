package com.yaic.app.common.dto;

import java.io.Serializable;

/**
 * 下拉列表封装类
 * @author Administrator
 *
 */
public class ComboDto implements Serializable {

    private static final long serialVersionUID = 5787565589318520264L;

    /* 参数代码 */
    private String parameterCode;
    
    /* 针对C\T\E等不同语言，显示相应的名称。 */
    private String parameterName;

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    
}
