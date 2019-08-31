package com.suql.model;

public class PrintDeviceBean {

    private String remark;

    private String mac;

    private String sn;

    public PrintDeviceBean() {
    }

    public PrintDeviceBean(String remark, String mac, String sn) {
        this.remark = remark;
        this.mac = mac;
        this.sn = sn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
