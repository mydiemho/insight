/**
 * Created by myho on 3/5/15.
 */
public class WordCount {

    public static void main(String[] args)
    {
        InputOutputProcessor inputOutputProcessor = new InputOutputProcessor();
        inputOutputProcessor.processInputFiles();
        inputOutputProcessor.writeOutputToFiles();
    }
}
