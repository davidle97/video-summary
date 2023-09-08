package helper;

import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionCanceledEventArgs;
import com.microsoft.cognitiveservices.speech.util.EventHandler;
import java.util.concurrent.CountDownLatch;


public class CanceledHandler implements EventHandler<SpeechRecognitionCanceledEventArgs> {
  private final CountDownLatch _countDownLatch;

  public CanceledHandler(CountDownLatch countDownLatch) {
    _countDownLatch = countDownLatch;
  }

  /**
   * This method is called when a speech recognition canceled event is received.
   * @param sender the event sender
   * @param e the event arguments containing the cancel event details
   */
  @Override
  public void onEvent(Object sender, SpeechRecognitionCanceledEventArgs e) {
    CancellationReason reason = e.getReason();
    if (reason.equals(CancellationReason.EndOfStream)) {
      System.out.println("Transcription completed successfully!");
    } else {
      System.out.println("An error happened. Please retry again. Error: " + e.getErrorDetails());
    }
    _countDownLatch.countDown();
  }
}
