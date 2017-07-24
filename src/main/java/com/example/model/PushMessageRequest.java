package com.example.model;

public class PushMessageRequest {
    private String queueUrl;
    private Object body;

    public PushMessageRequest() {
    }
    public PushMessageRequest(String url, Object body) {
        this.queueUrl = url;
        this.body = body;
    }
    public PushMessageRequest withUrl(String url) {
        this.setQueueUrl(url);
        return this;
    }
    public PushMessageRequest withBody(Object body) {
        this.setBody(body);
        return this;
    }
    public String getQueueUrl() {
        return queueUrl;
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
