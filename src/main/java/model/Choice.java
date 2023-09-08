package model;

public class Choice {
    private final int index;
    private final Message message;

    public Choice(int index, Message message) {
        this.index = index;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
