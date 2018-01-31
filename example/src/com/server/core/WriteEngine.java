package com.server.core;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.server.log.LogHelper;

import android.R.bool;

/*
 * DESCRIPTION
 *     TODO
 *
 * PRIVATE CLASSES
 *     NONE
 *
 * NOTES
 *    <other useful comments, qualifications, etc.>
 *
 * MODIFIED    (MM/DD/YY)
 *   bofan     2016年4月4日 - Creation
 *
 */
public class WriteEngine implements Runnable
{
    private BlockingMsgQueue writtingMsgQueue;
    private Thread thread;
    private Socket socket;
    
    public WriteEngine(Socket socket,BlockingMsgQueue queue)
    {
        writtingMsgQueue = queue;
        this.socket = socket;
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run()
    {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bufNumber = 0;
        
        OutputStream outPut;
        PrintWriter outWriter = null;
        try
        {
            outPut = socket.getOutputStream();
            outWriter=new PrintWriter(outPut);
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        while(true)
        {
            try
            {
                boolean isClose = isServerClose(socket);//判断是否断开  
                if (isClose)
                {
                    break;
                }
                String msg = writtingMsgQueue.fetch();
               
                // 加入length标示
                msg = String.format("length:%05d%s", msg.length(),msg);
                LogHelper.println("translate message:" + msg);
                
                outWriter.write(new String(msg.getBytes("UTF-8")));
                outWriter.flush();
            }
            catch (IOException ex)
            {
                System.out.println( ex.getMessage());
            }
            catch (InterruptedException exception)
            {
                System.out.println( exception.getMessage());
            }
        }
        
        try
        {
            socket.close();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    private boolean isServerClose(Socket socket2)
    {
        if (socket2.isClosed())
        {
            return true;
        }
        try{  
            socket2.sendUrgentData(0);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信  
            return false;  
        }
        catch(Exception se)
        {  
            return true;  
      
        }  
    }

}
