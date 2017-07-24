package com.example;

import com.example.model.PullMessageRequest;
import com.example.model.PushMessageRequest;
import com.example.model.PullMessageResponse;
import com.example.model.PushMessageResponse;

import java.util.Collection;

public interface QueueService {

  //
  // Task 1: Define me.
  //
  // This interface should include the following methods.  You should choose appropriate
  // signatures for these methods that prioritise simplicity of implementation for the range of
  // intended implementations (in-memory, file, and SQS).  You may include additional methods if
  // you choose.
  //
  // - push
  //   pushes a message onto a queue.
  // - pull
  //   retrieves a single message from a queue.
  // - delete
  //   deletes a message from the queue that was received by pull().
  //
  Collection<String> listQueues();
  PushMessageResponse push(PushMessageRequest request);

  PullMessageResponse pull(PullMessageRequest request);

  void deleteMessage(String messageId);


}
