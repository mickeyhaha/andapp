package com.server.log;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.io.UnsupportedEncodingException;

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
 *   bofan     2016骞�4鏈�4鏃� - Creation
 *
 */
public class LogHelper
{
    
    public static void println(String message)
    {
        System.out.println(message);
        
//        try
//        {
////            byte[] temp = message.getBytes("utf-8");// 杩欓噷鍐欏師缂栫爜鏂瑰紡
////            byte[] newtemp = new String(temp, "utf-8").getBytes("gbk");// 杩欓噷鍐欒浆鎹㈠悗鐨勭紪鐮佹柟寮�
////            String newStr = new String(newtemp, "gbk");// 杩欓噷鍐欒浆鎹㈠悗鐨勭紪鐮佹柟寮�
////            System.out.println(newStr);
//        } catch (UnsupportedEncodingException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
