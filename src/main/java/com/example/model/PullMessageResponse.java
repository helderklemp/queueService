package com.example.model;

import java.util.ArrayList;
import java.util.Collection;

public class PullMessageResponse {

    private Collection<Message> messages=new ArrayList<>();

    public PullMessageResponse withMessages(Collection<Message> messages)
    {
        this.setMessages(messages);
        return this;
    }
    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }
}
