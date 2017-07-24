package com.example;

import com.example.model.*;
import com.example.util.MessageTimeOutService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class InMemoryQueueService implements QueueService {

    private static final Logger log = Logger.getLogger(InMemoryQueueService.class.getName());
    private MessageTimeOutService timeOUtService;

    public InMemoryQueueService(MessageTimeOutService timeOUtService){
        this.timeOUtService=timeOUtService;
    }

    private final int DEFAULT_PAGINATION_SIZE=10;

    private Map<String,Queue<Message>> queues =new ConcurrentHashMap<>();

    private Map<String,SuppressedMessage> processedMessages = new ConcurrentHashMap<>();

    @Override
    public Collection<String> listQueues() {
        return queues.keySet();
    }

    @Override
    public PushMessageResponse push(PushMessageRequest request) {
        Queue<Message> queue = initializeOrRetrieveQueue(request.getQueueUrl());
        Message msg = new Message();
        msg.setBody(request.getBody());
        msg.setId(UUID.randomUUID().toString());
        queue.add(msg);
        return new PushMessageResponse().withId(msg.getId());
    }
    @Override
    public PullMessageResponse pull(PullMessageRequest request) {
        Queue<Message> queue = initializeOrRetrieveQueue(request.getQueueUrl());
        Collection<Message> messages=new ArrayList<>();
        for (int i = 0; i < DEFAULT_PAGINATION_SIZE; i++) {
            Message msg=queue.poll();
            if(msg!=null){
                messages.add(msg);
                timeOUtService.suppressMessage(queue,msg,processedMessages);
            }
        }
        return new PullMessageResponse().withMessages(messages);
    }

    @Override
    public void deleteMessage(String messageId) {
        SuppressedMessage suppressedMessage =processedMessages.remove(messageId);
        if(suppressedMessage == null){
            throw new IllegalArgumentException("Message does not exist");
        }
        suppressedMessage.getFutureAction().cancel(true);
    }

    private Queue<Message> initializeOrRetrieveQueue(String url) {
        Queue<Message> queue =queues.get(checkRequiredValue("Message URL",url));
        if(queue == null){
            synchronized(queues){
                queue=new ArrayDeque<>();
                queues.put(url,queue);
            }
        }
        return queue;
    }

    private String checkRequiredValue(String name,String parameter) {
        if(parameter==null){
            throw  new IllegalArgumentException(String.format("The value %s is required",name));
        }
        return parameter;
    }


}
