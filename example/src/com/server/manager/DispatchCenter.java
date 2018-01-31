package com.server.manager;
/* Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.*/

import com.server.core.BlockingMsgQueue;

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
public class DispatchCenter implements Runnable
{
    private BlockingMsgQueue msgQueue;
    ServerContext context;
    IMessageHandler handler;
    
    Thread dispatchThread;
    
    public DispatchCenter(ServerContext ctx)
    {
        this(ctx, new DefaultHandler());
    }
    
    public DispatchCenter(ServerContext ctx,IMessageHandler handler)
    {
        this.context = ctx;
        this.msgQueue = ctx.receivedMsgQueue;
        this.handler = handler;
        
        dispatchThread = new Thread(this);
        dispatchThread.start();
    }
    

    /**
     * 消息分发器
     */
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String content = msgQueue.fetch();
                if (handler != null)
                {
                    handler.handler(context, content);
                }
                
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
    
    
    /**
     * 默认的消息处理器，将接收到的消息打印出来
     * @author bod
     *
     */
    static class  DefaultHandler implements IMessageHandler
    {

        @Override
        public void handler(ServerContext ctx, String content)
        {
           System.out.println("接收到消息：" + content);            
        }
        
    }
}
