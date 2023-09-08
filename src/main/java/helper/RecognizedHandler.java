package helper;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.util.EventHandler;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link EventHandler} for Speech Recognition Events
 * This class stores the raw results for further processing
 */
public class RecognizedHandler implements EventHandler<SpeechRecognitionEventArgs> {
  private final List<String> _recognizedResults = new ArrayList<>();

  public RecognizedHandler() {}

  /**
   * Triggered when a speech recognition event is received.
   * @param sender the sender
   * @param e the event argument that contain the result
   */
  @Override
  public void onEvent(Object sender, SpeechRecognitionEventArgs e) {
    SpeechRecognitionResult result = e.getResult();
    ResultReason reason = result.getReason();
    if (ResultReason.RecognizedSpeech.equals(reason)) {
      // filter out when the result text is empty.
      String text = result.getText();
      if (!text.isEmpty()) {
        System.out.println(text);
        _recognizedResults.add(text);
      }
    }
  }

  /**
   * get the list of raw recognized results
   * @return the list of raw recognized results
   */
  public List<String> getRecognizedResults() {
    return _recognizedResults;
  }
}

