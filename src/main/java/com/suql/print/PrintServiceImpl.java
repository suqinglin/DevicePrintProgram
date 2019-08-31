package com.suql.print;

import com.suql.utils.Constant;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;
import java.io.IOException;
public class PrintServiceImpl implements com.suql.print.PrintService {

    public String createQrCodeByMac(String remark, String mac, String sn, String model, String name) {
        // 初始化方法必须调用
        Constant.getInstance().init("GWF_".equals(remark));
        String msg = String.valueOf(System.currentTimeMillis()) + ".png";

        CodeModel info = new CodeModel();
        String content = Constant.APP_DOWNLOAD_URL + remark + "#" + mac + "#" + sn;
        System.out.println("Qr Content:" + content);
//        info.setContents(Constant.APP_DOWNLOAD_URL + remark + "#" + mac + "#" + sn + "#" + model.replace("NL-", ""));
        info.setContents(content);
        info.setWidth(Constant.QR_WIDTH);
        info.setHeight(Constant.QR_WIDTH);
        info.setFontSize(Constant.FONT_SIZE);
        info.setLogoFile(null);
        if (mac.startsWith("FFFF")) {
            info.setProductMac(mac.substring(4));
        } else {
            info.setProductMac(mac);
        }
        info.setProductName(name);
        info.setProductModel(model);
        info.setProductSn(sn);
        QR_Code code = new QR_Code();
        String filePath = Constant.QR_FILE_PATH + msg;
        code.createCodeImage(info, filePath);
        return filePath;
    }

    public void print(String path) {
        try {
            DocFlavor dof = DocFlavor.INPUT_STREAM.PNG;
            // 获取默认打印机
            javax.print.PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            //          pras.add(OrientationRequested.PORTRAIT);
                      pras.add(PrintQuality.HIGH);
            pras.add(new Copies(1));
            pras.add(Constant.PRINT_MEDIA_SIZE); // 设置打印的纸张

            DocAttributeSet das = new HashDocAttributeSet();
//            das.add(new MediaPrintableArea(0, 0, 56, 21, MediaPrintableArea.MM));
            das.add(new MediaPrintableArea(0, 0, Constant.PRINT_WIDTH, Constant.PRINT_HEIGHT, MediaPrintableArea.MM));
            FileInputStream fin = new FileInputStream(path);
            Doc doc = new SimpleDoc(fin, dof, das);
            DocPrintJob job = ps.createPrintJob();

            job.print(doc, pras);
            fin.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (PrintException pe) {
            pe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintServiceImpl printService = new PrintServiceImpl();
//        info.setProductMac("DC2C28123456");
//        info.setProductName("Human Counter");
//        info.setProductModel("NL-HMC21C");
//        info.setProductSn("1234567890");
//        String path = printService.createQrCodeByMac("GWF_", "FFFFDC2C28DBABAB", "1234567891", "NL-GWF01A", "Lora网关路由器");
//        for (int i = 0; i < 50 ; i++) {
            String path = printService.createQrCodeByMac("FRM_", "FFFFDC2C280003B5", "0000000945", "NL-FLL19B", "Smart Face Lock M2");
            printService.print(path);
//        }
    }
}
