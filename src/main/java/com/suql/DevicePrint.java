package com.suql;

import com.suql.print.PrintServiceImpl;
import com.suql.utils.Constant;
import com.suql.utils.HttpsUtil;
import com.suql.utils.JsonUtil;
import org.apache.http.HttpResponse;

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
    private PrintServiceImpl printService;

    public static void main(String[] args) {
        new DevicePrint();
    }

    public DevicePrint() {

        JFrame frame = new JFrame();
        frame.setTitle("Device Print");

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
                String model = tfModel.getText();
                String count = tfCount.getText();
                String name = tfName.getText();
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
                                name);
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

            printService = new PrintServiceImpl();
            for (int i = 0; i < 5; i++) {
                String path = printService.createQrCodeByMac("xxxx", "FFFFxxxxxxxxxxxx", "xxxxxxxxxx", "xxxxxxxxx", "xxxxxxxxxxxxxxxxx");
                printService.print(path);
            }
        }
    }
}
