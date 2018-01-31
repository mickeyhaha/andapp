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
public enum MessageType
{
    DEFAULT(0),
    DEFAULT_ACK(1),
    
    PHONE_NUMBER(2),
    PHONE_NUMBER_ACK(3);
    
    private int code;
    public int getCode()
    {
        return code;
    }
    private MessageType(int code)
   {
        this.code = code;
   }
}
