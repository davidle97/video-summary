import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import processors.SpeechToTextProcessor;
import processors.TranscriptToSummaryProcessor;


public class Main {
  public static void main(String[] args) throws URISyntaxException {
    // load and verify args
    if (args.length < 3) {
      System.out.println("Incorrect number of arguments provided");
      throw new IllegalStateException("Incorrect number of arguments provided");
    }

    final String key = args[0];
    final String region = args[1];
    final String openAIKey = args[2];

    SpeechToTextProcessor speechToTextProcessor = new SpeechToTextProcessor(region, key);
    TranscriptToSummaryProcessor transcriptToSummaryProcessor = new TranscriptToSummaryProcessor(openAIKey);
    System.out.println("enter the path to the video file or 0 to exit.");
    String path = new Scanner(System.in).nextLine();
    while (!path.equals("0")) {
      List<String> results = speechToTextProcessor.processVideo(new ArrayList<>(), path);
      transcriptToSummaryProcessor.summarize(results);

      System.out.println("enter the path to the video file or 0 to exit.");
      path = new Scanner(System.in).nextLine();
    }

    System.out.println("Finishing demo.");
    System.exit(0);
  }
}