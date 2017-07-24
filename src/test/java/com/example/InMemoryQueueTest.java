package com.example;

import com.example.model.*;
import com.example.util.MessageTimeOutService;
import com.example.util.MessageTimeOutServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.IntStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class InMemoryQueueTest {

    QueueService service;
    @Mock
    MessageTimeOutService timeOutService;


    @Before
    public void setUp(){
        service = new InMemoryQueueService(new MessageTimeOutServiceImpl());
    }


    @Test(expected=IllegalArgumentException.class)
    public void givenNullParameterWhenPushMessageThenShouldFail(){
        service.push(new PushMessageRequest());
        Assert.fail();
    }
    @Test(expected=IllegalArgumentException.class)
    public void givenNullParameterWhenPullMessageThenShouldFail(){
        service.pull(new PullMessageRequest());
        Assert.fail();
    }
    @Test(expected=IllegalArgumentException.class)
    public void whenDeleteAnIncorrectMessageShouldFail(){
        service.deleteMessage("1");
        Assert.fail();
    }

    @Test
    public void whenMessageIsPushedThenShouldReturnMessageId(){
        PushMessageRequest request=new PushMessageRequest().withBody("First Message").withUrl("localhost/testQueue");
        PushMessageResponse response = service.push(request);
        Assert.assertNotNull("Push response must return the message ID",response.getId());
        Assert.assertFalse("to send a message, at least one queue must be presented",service.listQueues().isEmpty());
    }
    @Test
    public void listQueueTest(){
        Collection<String> listQueues = service.listQueues();
        Assert.assertTrue(listQueues.isEmpty());
        service.push(new PushMessageRequest().withUrl("localhost/testQueue-1"));
        service.push(new PushMessageRequest().withUrl("localhost/testQueue-2"));
        Assert.assertEquals("A message server can handle multiple queues",listQueues.size(),2);
    }
    @Test
    public void givenAPushedMessageWhenPullTheSameQueueThenShouldReturnDetails(){
        String queueUrl="localhost/testQueue-1";
        PushMessageResponse pushMessageResponse = service.push(new PushMessageRequest().withUrl(queueUrl).withBody("1"));
        PullMessageResponse messageResponse = service.pull(new PullMessageRequest().withQueueUrl(queueUrl));
        Assert.assertFalse("The message response should not be empty",messageResponse.getMessages().isEmpty());
        Optional<Message> message = messageResponse.getMessages().stream().findFirst();
        Assert.assertEquals("Must be the exact same message ",pushMessageResponse.getId(),message.get().getId());
    }
    @Test
    public void givenAReceivedMessageWhenDeleteThenShouldRemoveFromSystem(){
        Message msg=produceAndConsumeMessage("localhost/queue1","body");
        service.deleteMessage(msg.getId());
    }
    @Test
    public void whenMultiplePushedMessageThenShouldReceivedMultipleMessages(){
        String queueUrl="localhost/testQueue-1";
        IntStream.range(0,10).forEach(i->
                service.push(new PushMessageRequest().withUrl(queueUrl).withBody(i)));

        PullMessageResponse messageResponse = service.pull(new PullMessageRequest().withQueueUrl(queueUrl));
        Assert.assertEquals("Must return the same messages size",10,messageResponse.getMessages().size());
    }
    @Test
    public void givenAReceivedMessageWhenTimeoutThenMessageIsPushBackToQueue() throws InterruptedException {
        MockitoAnnotations.initMocks(this);
        service = new InMemoryQueueService(timeOutService);
        String queueUrl="localhost/testQueue-1";

        doAnswer(invocation -> {
            Message msg = (Message) invocation.getArguments()[1];
            ((Queue<Message>)invocation.getArguments()[0]).add(msg);
            ((Map<String,SuppressedMessage>)invocation.getArguments()[2]).remove(msg.getId());
            return null;
        }).when(timeOutService).suppressMessage(any(Queue.class),any(Message.class),any(Map.class));

        Message msg=produceAndConsumeMessage(queueUrl,"body");
        Assert.assertNotNull("First Message ",msg);
        verify(timeOutService, atLeast(1)).suppressMessage(
                any(Queue.class),any(Message.class),any(Map.class));
        PullMessageResponse pullMessageResponse = service.pull(new PullMessageRequest().withQueueUrl(queueUrl));
        Assert.assertTrue("Should have more then one messages",pullMessageResponse.getMessages().size()>1);
    }

    private Message produceAndConsumeMessage(String url,Object body){
        service.push(new PushMessageRequest().withUrl(url).withBody(body));
        PullMessageResponse messageResponse = service.pull(new PullMessageRequest().withQueueUrl(url));
        Optional<Message> message = messageResponse.getMessages().stream().findFirst();
        return message.get();
    }
}
