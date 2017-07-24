package com.example.model;

import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.time.LocalTime;

public class PushMessageResponse {
    private String id;
    private LocalTime receivedTime;

    public PushMessageResponse() {
        super();
    }
    public PushMessageResponse(String id) {
        this.id = id;
    }
    public PushMessageResponse(String id,LocalTime receivedTime) {
        this.id = id;
        this.receivedTime=receivedTime;
    }

    public PushMessageResponse withId(String id) {
        this.setId(id);
        return this;
    }
    public PushMessageResponse withReceivedTime(LocalTime receivedTime) {
        this.setReceivedTime(receivedTime);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(LocalTime receivedTime) {
        this.receivedTime = receivedTime;
    }
}
