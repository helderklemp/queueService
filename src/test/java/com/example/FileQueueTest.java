package com.example;

import com.example.model.*;
import com.example.util.MessageTimeOutServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

public class FileQueueTest {

    QueueService service;
    private final String BASE_DIR="./FileQueueService/";
    @Before
    public void setUp(){
        service = new FileQueueService();
    }
    @After
    public void tearDown(){
        File dir = new File(BASE_DIR);
        for(File file: dir.listFiles())
        if (file.isDirectory())
            file.delete();
    }
    @Test
    public void testBasicFiles(){
        Assert.assertNotNull(service.listQueues());
    }
    @Test
    public void whenMessageIsPushedThenShouldReturnMessageId(){
        PushMessageRequest request=new PushMessageRequest().withBody("First Message").withUrl("testQueue");
        PushMessageResponse response = service.push(request);
        Assert.assertNotNull("Push response must return the message ID",response.getId());
        Assert.assertFalse("to send a message, at least one queue must be presented",service.listQueues().isEmpty());
    }
    @Test
    public void listQueueTest(){
        service.push(new PushMessageRequest().withUrl("localhost_testQueue-1"));
        service.push(new PushMessageRequest().withUrl("localhost_testQueue-2"));
        Collection<String> listQueues = service.listQueues();
        Assert.assertEquals("A message server can handle multiple queues",listQueues.size(),2);
    }
    @Test
    public void givenAPushedMessageWhenPullTheSameQueueThenShouldReturnDetails(){
        String queueUrl="localhost_testQueue-1";
        PushMessageResponse pushMessageResponse = service.push(new PushMessageRequest().withUrl(queueUrl).withBody("1"));
        PullMessageResponse messageResponse = service.pull(new PullMessageRequest().withQueueUrl(queueUrl));
        Assert.assertFalse("The message response should not be empty",messageResponse.getMessages().isEmpty());
        Optional<Message> message = messageResponse.getMessages().stream().findFirst();
        Assert.assertEquals("Must be the exact same message ",pushMessageResponse.getId(),message.get().getId());
    }

}
