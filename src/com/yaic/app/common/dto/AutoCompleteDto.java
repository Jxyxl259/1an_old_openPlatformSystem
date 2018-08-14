package com.yaic.app.common.dto;

import java.io.Serializable;

/**
 * data-ac='{
 *  "dataFn":dataFn,//定义数据源
 *  "valueKey":"value",//值
 *  "labelKey":"xxx",//显示labelKey的值
 *  "labelFn":labelFn,//自定义显示labelKey的值
 *  "selectFn":selectFn//选中时触发事件
 *  }'
 * @author Administrator
 *
 */
public class AutoCompleteDto implements Serializable {


    private static final long serialVersionUID = 992330244624333405L;

    /**
     * valueKey
     */
    private String value;

    /**
     * labelKey
     */
    private String xxx;

    /**
     * labelValue
     */
    private String yyy;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    public String getYyy() {
        return yyy;
    }

    public void setYyy(String yyy) {
        this.yyy = yyy;
    }
    
}
