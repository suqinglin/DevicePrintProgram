package com.suql.print;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.suql.utils.Constant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QR_Code {
    private static int BLACK = 0x000000;
    private static int WHITE = 0xFFFFFF;

    /**
     * 1.创建最原始的二维码图片
     *
     * @param info
     * @return
     */
    private BufferedImage createCodeImage(CodeModel info) {

        String contents = info.getContents();//获取正文
        int width = info.getWidth();//宽度
        int height = info.getHeight();//高度
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置二维码的纠错级别【级别分别为M L H Q ，H纠错能力级别最高，约可纠错30%的数据码字】
        hint.put(EncodeHintType.CHARACTER_SET, info.getCharacter_set());//设置二维码编码方式【UTF-8】
        hint.put(EncodeHintType.MARGIN, 0);

        MultiFormatWriter writer = new MultiFormatWriter();
        BufferedImage img = null;
        try {
            //构建二维码图片
            //QR_CODE 一种矩阵二维码
            BitMatrix bm = writer.encode(contents, BarcodeFormat.QR_CODE, width, height + 5, hint);
            int[] locationTopLeft = bm.getTopLeftOnBit();
            int[] locationBottomRight = bm.getBottomRightOnBit();
            info.setBottomStart(new int[] { locationTopLeft[0], locationBottomRight[1] });
            info.setBottomEnd(locationBottomRight);
            int w = bm.getWidth();
            int h = bm.getHeight();
            img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    img.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * 1.创建最原始的二维码图片
     *
     * @param info
     * @return
     */
    private BufferedImage createCodeImage1(CodeModel info) {

        String contents = info.getContents();//获取正文
        int width = info.getWidth();//宽度
        int height = info.getHeight();//高度
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置二维码的纠错级别【级别分别为M L H Q ，H纠错能力级别最高，约可纠错30%的数据码字】
        hint.put(EncodeHintType.CHARACTER_SET, info.getCharacter_set());//设置二维码编码方式【UTF-8】
        hint.put(EncodeHintType.MARGIN, 0);

        MultiFormatWriter writer = new MultiFormatWriter();
        BufferedImage img = null;
        try {
            //构建二维码图片
            //QR_CODE 一种矩阵二维码
            BitMatrix bm = writer.encode(contents, BarcodeFormat.QR_CODE, width, height, hint);
            int[] locationTopLeft = bm.getTopLeftOnBit();
            int[] locationBottomRight = bm.getBottomRightOnBit();
            info.setBottomStart(new int[] { locationTopLeft[0], locationBottomRight[1] });
            info.setBottomEnd(locationBottomRight);
            int w = bm.getWidth();
            int h = bm.getHeight();
            img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    img.setRGB(x, y, bm.get(x, y) ? BLACK : WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return img;
    }


    /**
     * 2.为二维码增加logo和二维码下文字 logo--可以为null 文字--可以为null或者空字符串""
     *
     * @param info
     * @param output
     */
    private void dealDesc(CodeModel info, OutputStream output) throws IOException {
        //获取原始二维码图片
        BufferedImage bm = createCodeImage1(info);
//        int width = bm.getWidth() + 12;
        int width = bm.getWidth() + Constant.QR_TEXT_SPACE;
        int height = bm.getHeight();
        Graphics g = bm.getGraphics();

        Font font = new Font("微软雅黑", Font.PLAIN, info.getFontSize());
        Font fontLarge = new Font("微软雅黑", Font.PLAIN, info.getFontSize() + 4);
        int fontHeight = g.getFontMetrics(font).getHeight();

        BufferedImage bm1 = new BufferedImage(width + Constant.TEXT_WIDTH, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g1 = bm1.getGraphics();
        g1.setColor(Color.WHITE);
        g1.fillRect(0, 0, width + Constant.TEXT_WIDTH, height);
        g1.drawImage(bm, 12, 0, null);
        g1.setColor(new Color(BLACK));
        if (Constant.IS_GATEWAY) {
            Image cccImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("image/ccc.png"));
            Image wifiImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("image/wifi.png"));
            Image loraImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("image/lora.png"));
            Image recycelImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("image/recycle.png"));
            g1.setFont(fontLarge);
            g1.drawString(info.getProductName(), width, fontHeight );
            g1.drawImage(loraImage, width + 280, 12, null);
            g1.drawImage(wifiImage, width + 370, 12, null);
            g1.drawImage(cccImage, width + 500, 12, null);
            g1.setFont(font);
            g1.drawString("型号:" + info.getProductModel(), width, fontHeight * 2 + 21);
            g1.drawString("序号:" + info.getProductSn(), width + 350, fontHeight * 2 + 21);
            g1.drawString("地址:" + info.getProductMac(), width, fontHeight * 3 + 30);
            String wifiPwd = info.getProductMac().substring(info.getProductMac().length() - 4);
            g1.drawString("热点:NEXLESS_" + wifiPwd, width + 350, fontHeight * 3 + 30);
            g1.drawString("设置:nexlesswifi.com", width, fontHeight * 4 + 36);
            g1.drawString("密码:12345678", width + 350, fontHeight * 4 + 36);
            g1.drawString("厂商:重庆星翼智慧科技有限公司", width, fontHeight * 5 + 48);
            g1.drawImage(recycelImage, width + 500, fontHeight * 5, null);
        } else {
            g1.setFont(font);
            g1.drawString(info.getProductName(), width, fontHeight);
            g1.drawString("Model:" + info.getProductModel(), width, fontHeight * 2 + 11);
            g1.drawString("SN:" + info.getProductSn(), width, fontHeight * 3 + 22);
            g1.drawString("MAC:" + info.getProductMac(), width, fontHeight * 4 + 33);
            g1.drawString("Nexless Co.,Ltd.", width, fontHeight * 5 + 44);
        }

//        URL url = getClass().getClassLoader().getResource("/resources/image/ccc.png");
//        g1.drawString("型号:NL-GWF01A", width, fontHeight * 2 + 21);
//        g1.drawString("序号:1234567890", width + 310, fontHeight * 2 + 21);
//        g1.drawString("地址:DC2C26123456", width, fontHeight * 3 + 30);
//        g1.drawString("热点:NEXLESS_3456", width + 310, fontHeight * 3 + 30);
//        g1.drawString("设置:nexlesswifi.com", width, fontHeight * 4 + 36);
//        g1.drawString("密码:12345678", width + 310, fontHeight * 4 + 36);
//        g1.drawString("厂商:重庆星翼智慧科技有限公司", width, fontHeight * 5 + 48);
        g1.dispose();
        bm = bm1;

        try {
            ImageIO.write(bm, info.getFormat(), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3.创建 带logo和文字的二维码
     *
     * @param info
     * @param file
     */
    public void createCodeImage(CodeModel info, File file) {
        File parent = file.getParentFile();
        if (!parent.exists())
            parent.mkdirs();
        OutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            dealDesc(info, output);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.创建 带logo和文字的二维码
     *
     * @param info
     * @param filePath
     */
    public void createCodeImage(CodeModel info, String filePath) {
        createCodeImage(info, new File(filePath));
    }

    /**
     * 5.读取 二维码 获取二维码中正文
     *
     * @param input
     * @return
     */
    public String decode(InputStream input) {
        Map<DecodeHintType, Object> hint = new HashMap<DecodeHintType, Object>();
        hint.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        String result = "";
        try {
            BufferedImage img = ImageIO.read(input);
            int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
            LuminanceSource source = new RGBLuminanceSource(img.getWidth(), img.getHeight(), pixels);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            QRCodeReader reader = new QRCodeReader();
            Result r = reader.decode(bitmap, hint);
            result = r.getText();
        } catch (Exception e) {
            result = "读取错误";
        }
        return result;
    }

}
