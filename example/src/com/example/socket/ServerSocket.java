package com.example.socket;

import android.util.Log;

import java.io.IOException;

/**
 * Created by adjuster on 2018/1/5.
 */

public class ServerSocket extends Thread {

    @Override
    public void run() {
        try {
            Log.i("tst", "Server starting...");
            java.net.ServerSocket socket = SocketHelper.CreateServer();
            SocketHelper.GetClinetSocket();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
