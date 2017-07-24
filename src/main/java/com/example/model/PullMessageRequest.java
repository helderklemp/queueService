package com.example.model;

public class PullMessageRequest {
    private String queueUrl;

    public String getQueueUrl() {
        return queueUrl;
    }

    public PullMessageRequest withQueueUrl(String queueUrl) {
        this.setQueueUrl(queueUrl);
        return this;
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
    }
}
