package com.example.model;

import com.google.common.base.Objects;

import java.util.Map;

public class Message {
    private String id;
    private Object body;
    private Map<String,String> headers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equal(id, message.id) &&
                Objects.equal(body, message.body);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, body);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
