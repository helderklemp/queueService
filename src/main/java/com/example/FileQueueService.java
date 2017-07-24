package com.example;

import com.example.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class FileQueueService implements QueueService {

    private final String BASE_DIR="./FileQueueService/";

    public FileQueueService(){
        File directory = new File(BASE_DIR);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
    @Override
    public PushMessageResponse push(PushMessageRequest request) {
        initializeQueue(request.getQueueUrl());
        Message msg = new Message();
        msg.setBody(request.getBody());
        msg.setId(UUID.randomUUID().toString());
        saveMessage(msg);
        return new PushMessageResponse().withId(msg.getId());
    }

    private void saveMessage(Message msg) {
    }

    @Override
    public Collection<String> listQueues() {
        Collection<String> queues=new ArrayList<>();
        try {
            try(Stream<Path> stream = Files.list(Paths.get(BASE_DIR))){
                stream.forEach(p -> queues.add(p.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } ;
        return queues;
    }
    @Override
    public PullMessageResponse pull(PullMessageRequest request) {
        return null;
    }

    @Override
    public void deleteMessage(String messageId) {

    }
    private void initializeQueue(String url) {
        File directory = new File(BASE_DIR+checkRequiredValue("Message URL",url));
        if (! directory.exists()){
            directory.mkdir();
        }
    }

    private String checkRequiredValue(String name,String parameter) {
        if(parameter==null){
            throw  new IllegalArgumentException(String.format("The value %s is required",name));
        }
        return parameter;
    }

}
