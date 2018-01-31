package com.example.socket;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by adjuster on 2018/1/5.
 */

public class ClientSocket extends Thread
{

    @Override
    public void run() {
        try {
            Socket socket = SocketHelper.CreateClient("192.168.159.59");
            SocketHelper.SendMsg(socket, "hello,I am come from android");
            SocketHelper.Close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientSocket().start();
    }

}