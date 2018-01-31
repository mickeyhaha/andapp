package com.test;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.log.LogHelper;
import com.test.engine.MsgEngine;

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
 *   bofan     2016��4��3�� - Creation
 *
 */
public class Demo
{
    public static void main(String[] args) throws Exception
    {
   
        // 加入length标示
        String msg = "这里是服务器";
        msg = String.format("length:%05d%s", msg.length(),msg);
        LogHelper.println("translate message:" + msg);
        
        System.out.println(msg.getBytes("UTF-8"));
        
        TestRegister reg = new TestRegister();
        reg.testRegister();
    }
}
