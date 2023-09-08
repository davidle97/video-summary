package model;

public class Message {
  private final Role role;
  private final String content;

  public Message(Role role, String content) {
    this.role = role;
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}

