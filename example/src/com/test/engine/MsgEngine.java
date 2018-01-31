package com.test.engine;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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
 *   bofan     2016 - Creation
 *
 */
public class MsgEngine implements Runnable
{
    private Socket socket;
    public MsgEngine(Socket socket)
    {
        this.socket = socket;
    }
    
    public void run()
    {
        try
        {
            OutputStream outPut = socket.getOutputStream();
            PrintWriter outWriter=new PrintWriter(outPut);
            
            for(int i=0;i < 10;i++)
            {
                String str = "this is from java" + i;
                Message message = new Message(str);
                str = String.format("length:%05d",message.toString().length()) + message.toString();
                System.out.println(str);
                outWriter.write(new String(str.getBytes("UTF-8")));
                outWriter.flush();
            }
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
