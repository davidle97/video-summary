package model;

import java.util.List;

public class ChatCompletionResponseBody {
    private final String model;
    private final List<Choice> choices;

    public ChatCompletionResponseBody(String model, List<Choice> choices) {
        this.model = model;
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
