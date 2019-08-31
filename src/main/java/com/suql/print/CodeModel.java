package com.suql.print;

import java.io.File;

public class CodeModel {

    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param contents
     *            the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the character_set
     */
    public String getCharacter_set() {
        return character_set;
    }

    /**
     * @param character_set
     *            the character_set to set
     */
    public void setCharacter_set(String character_set) {
        this.character_set = character_set;
    }

    /**
     * @return the fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize
     *            the fontSize to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * @return the logoFile
     */
    public File getLogoFile() {
        return logoFile;
    }

    /**
     * @param logoFile
     *            the logoFile to set
     */
    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    /**
     * @return the logoRatio
     */
    public float getLogoRatio() {
        return logoRatio;
    }

    /**
     * @param logoRatio
     *            the logoRatio to set
     */
    public void setLogoRatio(float logoRatio) {
        this.logoRatio = logoRatio;
    }

    /**
     * @return the whiteWidth
     */
    public int getWhiteWidth() {
        return whiteWidth;
    }

    /**
     * @param whiteWidth
     *            the whiteWidth to set
     */
    public void setWhiteWidth(int whiteWidth) {
        this.whiteWidth = whiteWidth;
    }

    /**
     * @return the bottomStart
     */
    public int[] getBottomStart() {
        return bottomStart;
    }

    /**
     * @param bottomStart
     *            the bottomStart to set
     */
    public void setBottomStart(int[] bottomStart) {
        this.bottomStart = bottomStart;
    }

    /**
     * @return the bottomEnd
     */
    public int[] getBottomEnd() {
        return bottomEnd;
    }

    /**
     * @param bottomEnd
     *            the bottomEnd to set
     */
    public void setBottomEnd(int[] bottomEnd) {
        this.bottomEnd = bottomEnd;
    }

    /**
     * 正文
     */
    private String contents;
    /**
     * 二维码宽度
     */
    private int width = 400;
    /**
     * 二维码高度
     */
    private int height = 400;
    /**
     * 图片格式
     */
    private String format = "png";
    /**
     * 编码方式
     */
    private String character_set = "utf-8";
    /**
     * 字体大小
     */
    private int fontSize = 12;
    /**
     * logo
     */
    private File logoFile;
    /**
     * logo所占二维码比例
     */
    private float logoRatio = 0.20f;
    private int whiteWidth;//白边的宽度
    private int[] bottomStart;//二维码最下边的开始坐标
    private int[] bottomEnd;//二维码最下边的结束坐标
    private String productName;
    private String productModel;
    private String productMac;
    private String productSn;

    public String getProductName() {
        return productName;
    }

    public void setProductMac(String productMac) {
        this.productMac = productMac;
    }

    public String getProductMac() {
        return productMac;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }
}
