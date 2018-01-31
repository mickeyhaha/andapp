package com.server.manager;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.core.BlockingMsgQueue;
import com.server.core.ReadEngine;
import com.server.core.WriteEngine;

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
public class ServerContext
{
    // 读入消息引擎
    ReadEngine readEngine;
    BlockingMsgQueue receivedMsgQueue;
    
    // 写入消息引擎
    WriteEngine writeEngine;
    BlockingMsgQueue writtingMsgQueue;
    
    Socket socket;
    int port;
    
    public ServerContext(int port)
    {
        this.port = port;
    }
    
    @SuppressWarnings("resource")
    public void BeginServer()
    {
        ServerSocket server;
        try
        {
            server = new ServerSocket(port);
            System.out.println("begin listen....");
            server.setReuseAddress(true);
            socket = server.accept();
            
            // 启动读引擎
            receivedMsgQueue = new BlockingMsgQueue();
            readEngine = new ReadEngine(socket, receivedMsgQueue);
            
            // 启动写引擎
            writtingMsgQueue = new BlockingMsgQueue();
            writeEngine = new WriteEngine(socket, writtingMsgQueue);
            
            System.out.println("server initilize finished");
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public BlockingMsgQueue getReceivedMsgQueue()
    {
        return receivedMsgQueue;
    }

    public void setReceivedMsgQueue(BlockingMsgQueue receivedMsgQueue)
    {
        this.receivedMsgQueue = receivedMsgQueue;
    }

    public BlockingMsgQueue getWrittingMsgQueue()
    {
        return writtingMsgQueue;
    }

    public void setWrittingMsgQueue(BlockingMsgQueue writtingMsgQueue)
    {
        this.writtingMsgQueue = writtingMsgQueue;
    }
    
    
}
