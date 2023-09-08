package model;

import java.util.List;


public class ChatCompletionRequestBody {
  private final String model;
  private final List<Message> messages;

  public ChatCompletionRequestBody(String model, List<Message> messages) {
    this.model = model;
    this.messages = messages;
  }
}


