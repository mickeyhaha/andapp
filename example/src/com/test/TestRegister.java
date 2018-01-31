package com.test;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.log.LogHelper;
import com.server.manager.DispatchCenter;
import com.server.manager.ServerContext;
import com.test.engine.MsgEngine;

import android.R.string;

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
 *   bofan     2016  - Creation
 *
 */
public class TestRegister
{

    public void testRegister() throws IOException, InterruptedException
    {
        
        ServerContext context = new ServerContext(42222);
        context.BeginServer();
        DispatchCenter dispatchCenter = new DispatchCenter(context);
        
        context.getWrittingMsgQueue().add("你大爷 uuuu");
        
        String msg = context.getReceivedMsgQueue().fetch();
        
        if (msg.contains("客服端"))
        {
            LogHelper.println("sucess recevie msg from client");
        }else
            LogHelper.println("failed recevie msg from client");
        
        System.in.read();

    }
}
