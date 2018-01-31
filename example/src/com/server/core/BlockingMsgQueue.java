package com.server.core;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.test.engine.Message;

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
public class BlockingMsgQueue
{

    private BlockingQueue<String> blockingQueue;
    
    public BlockingMsgQueue()
    {
        blockingQueue = new LinkedBlockingQueue<String>();
    }
    
    public String fetch() throws InterruptedException
    {
       return blockingQueue.take();
    }
    
    public void add(String msg)
    {
        blockingQueue.add(msg);
    }
    
}
