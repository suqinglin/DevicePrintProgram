package com.suql;

import com.suql.print.PrintServiceImpl;
import com.suql.utils.Constant;
import com.suql.utils.HttpsUtil;
import com.suql.utils.JsonUtil;
import org.apache.http.HttpResponse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevicePrint implements ActionListener {

    private JButton btnStartPrint;
    private JButton btnPrinterProofread;
    private JTextField tfName;
    private JTextField tfModel;
    private JTextField tfCount;
    private JLabel lbResult;
    private JLabel lbInfo;
    private PrintServiceImpl printService;
    private JRadioButton jrb1,jrb2,jrb3;
    private int temp = 0;

    public static void main(String[] args) {
        new DevicePrint();
    }

    public DevicePrint() {

        JFrame frame = new JFrame();
        try {
            Image icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("image/printer.ico"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setTitle("Device Print");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 模板
        JPanel jpTemplete = new JPanel();
        JLabel lbTemp = new JLabel("Template:         ");
        jrb1 = new JRadioButton("500*250 一般设备");
        jrb2 = new JRadioButton("770*270 一般设备");
        jrb3 = new JRadioButton("770*270 网关");
        jrb1.addActionListener(this);
        jrb2.addActionListener(this);
        jrb3.addActionListener(this);
        ButtonGroup bgTemplate = new ButtonGroup();
        bgTemplate.add(jrb1);
        bgTemplate.add(jrb2);
        bgTemplate.add(jrb3);
        jpTemplete.add(lbTemp);
        jpTemplete.add(jrb1);
        jpTemplete.add(jrb2);
        jpTemplete.add(jrb3);
        frame.add(jpTemplete);

        // 打印机校对
        JPanel jpPrinterProofreadButton = new JPanel(new GridLayout(1, 3));
        btnPrinterProofread = new JButton("打印机校对");
        btnPrinterProofread.addActionListener(this);
        jpPrinterProofreadButton.add(btnPrinterProofread);
        frame.add(jpPrinterProofreadButton);

        // Model输入
        JPanel jpName = new JPanel();
        tfName = new JTextField(20);
        jpName.add(tfName);
        jpName.setBorder(BorderFactory.createTitledBorder("Name"));
        frame.add(jpName);

        // Model输入
        JPanel jpModel = new JPanel();
        tfModel = new JTextField(20);
        jpModel.add(tfModel);
        jpModel.setBorder(BorderFactory.createTitledBorder("Model"));
        frame.add(jpModel);

        // Count输入
        JPanel jpCount = new JPanel(new FlowLayout());
        tfCount = new JTextField(20);
        jpCount.add(tfCount);
        jpCount.setBorder(BorderFactory.createTitledBorder("Count"));
        frame.add(jpCount);

        // 增加api地址说明
        JPanel jpInfoPanel = new JPanel(new FlowLayout());
        lbInfo = new JLabel("api接口地址:" + Constant.API_PRINT_DATA);
        jpInfoPanel.add(lbInfo);
        jpInfoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        frame.add(jpInfoPanel);


        JPanel jpButton = new JPanel(new GridLayout(1, 3));
        btnStartPrint = new JButton("开始打印");
        btnStartPrint.addActionListener(this);
        jpButton.add(btnStartPrint);
        frame.add(jpButton);

        JPanel jpResult = new JPanel();
        lbResult = new JLabel();
        jpResult.add(lbResult);
        frame.add(jpResult);

        Container p = frame.getContentPane();
        p.setLayout(new MyVFlowLayout());
        frame.setBounds(600, 200, 600, 400);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStartPrint) {
            try {
                if (temp == 0) {
                    lbResult.setText("请选择Template！");
                    return;
                }
                String model = tfModel.getText();
                String count = tfCount.getText();
                String name = tfName.getText();
                if (name == null || "".equals(name)) {
                    lbResult.setText("请输入Name！");
                    return;
                }
                if (model == null || "".equals(model)) {
                    lbResult.setText("请输入Model！");
                    return;
                }
                if (count == null || "".equals(count)) {
                    lbResult.setText("请输入Count！");
                    return;
                }
                HttpsUtil httpsUtil = new HttpsUtil();
                httpsUtil.initHttpClint();
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
                String jsonRequest = "{\"model\":\"" + model + "\",\"count\":\"" + count + "\"}";
                HttpResponse responseCreateDeviceCommand = httpsUtil.doPostJson(Constant.API_PRINT_DATA, header, jsonRequest);
                String responseBody = httpsUtil.getHttpResponseBody(responseCreateDeviceCommand);
                lbResult.setText(responseBody);
                Map response = JsonUtil.convertJsonStringToObject(responseBody, Map.class);
                if (response != null && "200".equals(response.get("code").toString())) {
                    lbResult.setText(response.get("code").toString());
                    Map printDataResponse = (Map) response.get("data");
                    List<Map> printDevices = (List<Map>) printDataResponse.get("deviceList");
                    printService = new PrintServiceImpl();
                    lbResult.setText("size:" + printDevices.size());
                    for (Map printDevice :
                            printDevices) {
                        lbResult.setText(printDevice.get("mac").toString());
                        String path = printService.createQrCodeByMac(
                                printDevice.get("remark").toString(),
                                printDevice.get("mac").toString(),
                                printDevice.get("sn").toString(),
                                model.replace("_", "-"),
                                name,
                                temp);
                        printService.print(path);
                    }
                } else {
                    lbResult.setText("response = null");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                lbResult.setText(e1.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
                lbResult.setText(e1.getMessage());
            }
        } else if (e.getSource() == btnPrinterProofread) {
            if (temp == 0) {
                lbResult.setText("请选择Template！");
                return;
            }
            printService = new PrintServiceImpl();
            for (int i = 0; i < 3; i++) {
                String path = printService.createQrCodeByMac("xxxx", "FFFFxxxxxxxxxxxx", "xxxxxxxxxx", "xxxxxxxxx", "xxxxxxxxxxxxxxxxx", temp);
                printService.print(path);
            }
        } else if (e.getSource() == jrb1) {
            temp = 1;
        } else if (e.getSource() == jrb2) {
            temp = 2;
        } else if (e.getSource() == jrb3) {
            temp = 3;
        }
    }
}
