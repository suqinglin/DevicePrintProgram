package com.suql.print;

public interface PrintService {

    String createQrCodeByMac(String remark, String mac, String sn, String model, String name, int template);

    void print(String path);
}
