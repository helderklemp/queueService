package com.example.model;

import java.util.concurrent.ScheduledFuture;

public class SuppressedMessage {
    private ScheduledFuture futureAction;
    Runnable action;

    public ScheduledFuture getFutureAction() {
        return futureAction;
    }

    public void setFutureAction(ScheduledFuture futureAction) {
        this.futureAction = futureAction;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }
}
