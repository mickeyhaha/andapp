package com.test.engine;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

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
public class Message
{
   @Override
    public String toString()
    {
       return String.format("{\"type\":%d,\"msg\":\"%s\"}", type.getCode(), msg);
    }
    public Message(String msg)
       {
           this.type = MessageType.DEFAULT;
           this.msg = msg;
       }
    public Message(MessageType type,String msg)
    {
        this.type = type;
        this.msg = msg;
    }
   private MessageType type;
   private String msg;
}
