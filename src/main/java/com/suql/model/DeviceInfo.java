package com.suql.model;

public class DeviceInfo {

    private String model;
    private String sn;
    private String mac;

    public DeviceInfo() {
    }

    public DeviceInfo(String model, String sn, String mac) {
        this.model = model;
        this.sn = sn;
        this.mac = mac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
