package processors;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import helper.CanceledHandler;
import helper.RecognizedHandler;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class SpeechToTextProcessor {
  private final SpeechConfig _speechConfig;


  public SpeechToTextProcessor(String region, String key) {
    _speechConfig = SpeechConfig.fromSubscription(key, region);
  }

  public List<String> processVideo(List<String> locales, String filePath) {
    // this is to block the main thread while waiting for the transcription to complete
    final CountDownLatch countDownLatch = new CountDownLatch(1);

    // open the file
    AudioConfig audioConfig = AudioConfig.fromWavFileInput(filePath);
    SpeechRecognizer speechRecognizer = new SpeechRecognizer(_speechConfig, audioConfig);

    // register events handlers
    RecognizedHandler recognizedHandler = new RecognizedHandler();
    CanceledHandler canceledHandler = new CanceledHandler(countDownLatch);
    speechRecognizer.recognized.addEventListener(recognizedHandler);
    speechRecognizer.canceled.addEventListener(canceledHandler);

    // stream
    speechRecognizer.startContinuousRecognitionAsync();
    final boolean isProcessFinishedWithinTime;
    try {
      isProcessFinishedWithinTime = countDownLatch.await(5, TimeUnit.MINUTES);
      // use get to block stopContinuousRecognitionAsync op to prevent early releases of audioConfig & speechRecognizer
      // in the following that could cause exceptions
      speechRecognizer.stopContinuousRecognitionAsync().get();
    } catch (InterruptedException | ExecutionException e) {
      String message = "Thread interrupted during transcript processing. Error: " + e.getMessage();
      System.out.println(message);
      throw new RuntimeException(message);
    } finally {
      // clean up resources regardless of catching exceptions or not
      audioConfig.close();
      speechRecognizer.close();
    }

    if (!isProcessFinishedWithinTime) {
      String error = "Transcription timed out.";
      System.out.println(error);
      throw new RuntimeException(error);
    }

    return recognizedHandler.getRecognizedResults();
  }
}
