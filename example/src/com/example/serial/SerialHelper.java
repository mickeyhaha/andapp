package com.example.serial;

import android.util.Log;

import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by adjuster on 2018/1/23.
 */

public class SerialHelper {

    private static final String TAG = SerialHelper.class.getSimpleName();

    public static void findSerialPort() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        ArrayList<Device> devices = serialPortFinder.getDevices();
    }

    public static void openSerial(Device device) {
        SerialPortManager mSerialPortManager = new SerialPortManager();

        // 添加打开串口监听
        mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {

            }

            @Override
            public void onFail(File device, Status status) {

            }
        });

        // 添加数据通信监听
        mSerialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });

        /* 打开串口
            - 参数1：串口
            - 参数2：波特率
            - 返回：串口打开是否成功
         */
        boolean openSerialPort = mSerialPortManager.openSerialPort(device.getFile(), 115200);


        //发送数据
        byte[] sendContentBytes = "test data".getBytes();
        boolean sendBytes = mSerialPortManager.sendBytes(sendContentBytes);

        //关闭串口
        mSerialPortManager.closeSerialPort();
    }
}
