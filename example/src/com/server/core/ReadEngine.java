package com.server.core;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/
import java.io.DataInputStream;
import java.io.IOException;

import java.net.Socket;

import com.server.log.LogHelper;


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
public class ReadEngine implements Runnable
{
    private BlockingMsgQueue receivedMsgQueue;
    private Thread thread;
    private Socket socket;
    public ReadEngine(Socket socket,BlockingMsgQueue queue)
    {
        receivedMsgQueue = queue;
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
        
        DataInputStream dis = null;
        try
        {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        while(true)
        {
            boolean isClose = isServerClose(socket);//判断是否断开  
            if (isClose)
            {
                break;
            }
            try
            {
                bufNumber = dis.read(buffer, 0, 1024);
                String msg = new String(buffer, "utf-8");
                msg = msg.trim();
                if (msg.trim().length() == 0)
                {
                    continue;
                }
                
                LogHelper.println(String.format("read [%s] from client",msg));
                sb.append(msg);
                
                String currentMsg = sb.toString();
                
                // 12为 length:00000 的字符长度，用于标示消息体的长度
                int FLAG_LENGTH = 12;
                while(currentMsg.length() > FLAG_LENGTH)
                {
                    // 获取标示位表示的消息体长度
                    String flag = currentMsg.substring(0, FLAG_LENGTH);
                    flag = flag.substring(7);
                    flag = flag.replaceFirst("^0*", "");
                    Integer len = Integer.parseInt(flag);
                    
                    // 截取标示位后面的字符
                    currentMsg = currentMsg.substring(FLAG_LENGTH);
                    
                    // 截取标示位中指定长度的字符
                    String fragmentMsg = currentMsg.substring(0,len);
                    receivedMsgQueue.add(fragmentMsg);
                    
                    // 移除len长度的字符
                    currentMsg = currentMsg.substring(len);
                }
                
                sb = new StringBuilder(currentMsg);
                buffer[0] = '\0';
            }
            catch (IOException exception)
            {
                
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
