package com.example;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.example.model.PullMessageRequest;
import com.example.model.PushMessageRequest;
import com.example.model.PullMessageResponse;
import com.example.model.PushMessageResponse;

import java.util.Collection;

public class SqsQueueService implements QueueService {
  //
  // Task 4: Optionally implement parts of me.
  //
  // This file is a placeholder for an AWS-backed implementation of QueueService.  It is included
  // primarily so you can quickly assess your choices for method signatures in QueueService in
  // terms of how well they map to the implementation intended for a production environment.
  //

  public SqsQueueService(AmazonSQSClient sqsClient) {
  }


  @Override
  public Collection<String> listQueues() {
    return null;
  }

  @Override
  public PushMessageResponse push(PushMessageRequest request) {
    return null;
  }

  @Override
  public PullMessageResponse pull(PullMessageRequest request) {
    return null;
  }

  @Override
  public void deleteMessage(String messageId) {

  }

}
