/*
 * File Name: com.com.huawei.utils.Constant.java
 *
 * Copyright Notice:
 *      Copyright  1998-2008, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package com.suql.utils;

import javax.print.attribute.standard.MediaSizeName;

public class Constant {

    private static Constant instance;

    public static Constant getInstance() {
        if (instance == null) {
            instance = new Constant();
        }
        return instance;
    }

    private Constant() {

    }

    public void init(boolean isGateway) {
        IS_GATEWAY = isGateway;
        QR_TEXT_SPACE = 24;
        if (isGateway) {
            FONT_SIZE = 31;
            PRINT_WIDTH = 72;
            PRINT_HEIGHT = 24;
            QR_WIDTH = 270;
            TEXT_WIDTH = 660;
            PRINT_MEDIA_SIZE = MediaSizeName.ISO_A7;
        } else {
            FONT_SIZE = 31;
            PRINT_WIDTH = 53;
            PRINT_HEIGHT = 21;
            QR_WIDTH = 270;
            TEXT_WIDTH = 340;
            PRINT_MEDIA_SIZE = MediaSizeName.ISO_A8;
        }
    }

    //please replace the IP and Port of the IoT platform environment address, when you use the demo.
//    public static final String BASE_URL = "http://183.66.233.86:8089";
    public static final String BASE_URL = "http://120.92.114.123:8089";

    //*************************** The following constants do not need to be modified *********************************//

    /*
     * Application Access Security:
     * 1. APP_AUTH
     * 2. REFRESH_TOKEN
     */
    public static final String API_PRINT_DATA = BASE_URL + "/device/getPrintData";
    public static final String REFRESH_TOKEN = BASE_URL + "/iocm/app/sec/v1.1.0/refreshToken";

    public static boolean IS_GATEWAY;
    public static int PRINT_WIDTH;
    public static int PRINT_HEIGHT;
    public static int QR_WIDTH;
    public static int FONT_SIZE;
    public static int QR_TEXT_SPACE;
    public static int TEXT_WIDTH;
    public static MediaSizeName PRINT_MEDIA_SIZE;
    public static final String QR_FILE_PATH = "D:\\suql\\device_qr_code\\";
    public static final String APP_DOWNLOAD_URL = "http://downapp.xeiot.com/sn/";
}
