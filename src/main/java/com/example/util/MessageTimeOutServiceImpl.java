package com.example.util;

import com.example.model.Message;
import com.example.model.SuppressedMessage;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MessageTimeOutServiceImpl implements  MessageTimeOutService{
    private static final Logger log = Logger.getLogger(MessageTimeOutServiceImpl.class.getName());
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private int timeoutInMilliSeconds =5000;

    @Override
    public void suppressMessage(Queue<Message> queue, Message msg,Map<String,SuppressedMessage> processedMessages) {
        SuppressedMessage futureMsg=new SuppressedMessage();
        Runnable action = () -> {
            log.info("TimeOut reached for message "+msg.getId());
            queue.add(msg);
            processedMessages.remove(msg.getId());
        };
        futureMsg.setFutureAction(executor.schedule(action, timeoutInMilliSeconds, TimeUnit.MILLISECONDS));
        processedMessages.put(msg.getId(),futureMsg);
    }
}
