package com.example.util;

import com.example.model.Message;
import com.example.model.SuppressedMessage;

import java.util.Map;
import java.util.Queue;

public interface MessageTimeOutService {
    void suppressMessage(Queue<Message> queue,Message msg,Map<String,SuppressedMessage> processedMessages);
}
