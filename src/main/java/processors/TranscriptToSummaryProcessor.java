package processors;

import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import model.ChatCompletionRequestBody;
import model.ChatCompletionResponseBody;
import model.Message;
import model.Role;

import javax.swing.text.AbstractDocument;


public class TranscriptToSummaryProcessor {
  final String _apiKey;
  final HttpClient _httpClient;
  final Gson _gson;
  final static String END_POINT = "https://api.openai.com/v1/chat/completions";
  final static String MODEL = "gpt-3.5-turbo";
  final static String PROMPT_PREFIX = "Please summarize the following text:\n";
  final static String CONTENT_TYPE = "Content-Type";
  final static String JSON = "application/json";
  final static String AUTHORIZATION = "Authorization";
  final static String BEARER = "Bearer ";

  public TranscriptToSummaryProcessor(String apiKey) {
    _apiKey = apiKey;
    _httpClient = HttpClient.newHttpClient();
    _gson = new Gson();
  }

  public void summarize(List<String> transcript) throws URISyntaxException {
    String textToSummarize = generateTextToSummarizeFromList(transcript);
    String prompt = PROMPT_PREFIX + textToSummarize;

    // construct request body
    Message message = new Message(Role.user, prompt);
    List<Message> messages = new ArrayList<>();
    messages.add(message);
    ChatCompletionRequestBody chatCompletionRequestBody = new ChatCompletionRequestBody(MODEL, messages);

    HttpRequest request = HttpRequest.newBuilder(new URI(END_POINT))
        .header(CONTENT_TYPE, JSON)
        .header(AUTHORIZATION, BEARER + _apiKey)
        .POST(HttpRequest.BodyPublishers.ofString(_gson.toJson(chatCompletionRequestBody)))
        .build();

    CompletableFuture<HttpResponse<String>> responseFuture = _httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    // Handle the response when it arrives
    responseFuture.thenAccept(response -> {
      if (response.statusCode() == 200) {
        String responseBody = response.body();
        ChatCompletionResponseBody chatCompletionResponseBody =
                _gson.fromJson(responseBody, ChatCompletionResponseBody.class);

        if (chatCompletionResponseBody.getChoices().size() == 0) {
          System.out.println("Please try again. can't generate summary: " + response.body());
        } else {
          System.out.println("Summary:\n" + chatCompletionResponseBody.getChoices().get(0).getMessage().getContent());
        }
      } else {
        System.out.println("Please try again. Video summary request failed: " + response.body());
      }
    }).join();
  }

  private String generateTextToSummarizeFromList(List<String> transcript) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < transcript.size(); i++) {
      sb.append(i == 0 ? transcript.get(i) : " " + transcript.get(i));
    }
    return sb.toString();
  }
}
