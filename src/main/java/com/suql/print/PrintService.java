package com.suql.print;

public interface PrintService {

    String createQrCodeByMac(String remark, String mac, String sn, String model, String name);

    void print(String path);
}
