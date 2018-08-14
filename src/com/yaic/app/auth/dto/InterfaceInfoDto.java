package com.yaic.app.auth.dto;

public class InterfaceInfoDto  {

    private String serviceName;
    private String serviceContext;
    private String remark;
    
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceContext() {
        return serviceContext;
    }
    public void setServiceContext(String serviceContext) {
        this.serviceContext = serviceContext;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}