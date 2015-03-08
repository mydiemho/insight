import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.lang.String.format;

/**
 * Created by myho on 3/5/15.
 */
public class InputOutputProcessor {

    private static final Splitter SPACE_SPLITTER = Splitter.on(' ')
            .omitEmptyStrings()
            .trimResults();

    private static final String ROOT_PATH = "/Users/myho/dev/java/insight/";
    private static final String INPUT_DIRECTORY_PATH = ROOT_PATH + "wc_input/";
    private static final String OUTPUT_DIRECTORY_PATH = ROOT_PATH + "wc_output/";
    private static final File INPUT_DIRECTORY = new File(INPUT_DIRECTORY_PATH);

    private final List<String> inputFileNames;
    private final HistogramProcessor histogramProcessor;
    private final MedianProcessor medianProcessor;

    public InputOutputProcessor() {

        // read all files from directory
        inputFileNames = new LinkedList<String>();
        medianProcessor = new MedianProcessor();
        histogramProcessor = new HistogramProcessor();
    }

    public void processInputFiles() {
        getAllFileNamesInSortedOrder();
        readAllInputFiles();
    }

    private void getAllFileNamesInSortedOrder() {

        listFilesForFolder(INPUT_DIRECTORY);

        if (inputFileNames.size() > 0) {
            Collections.sort(inputFileNames, CASE_INSENSITIVE_ORDER);
        }
    }

    private void readFile(String fileName) {
        List<String> lines;
        // Read the lines of a UTF-8 text file
        try {
            lines = Files.asCharSource(new File(fileName), Charsets.UTF_8)
                    .readLines();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (lines.isEmpty()) {
            return;
        }

        for (String line : lines) {

            Iterable<String> words = SPACE_SPLITTER.split(line);

            int wordCount = 0;
            for (String word : words) {
                wordCount += 1;

                // get rid of punctuations
                word = word.replaceAll("\\W", "");
                word = word.toLowerCase();

                histogramProcessor.add(word);
            }

            medianProcessor.add(wordCount);
        }
    }

    private void readAllInputFiles() {
        for (String fileName : inputFileNames) {
            readFile(fileName);
        }
    }

    public void writeOutputToFiles() {
        writeHistogramToFile();
        writeMediansToFile();
    }

    private void listFilesForFolder(final File folder) {
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    inputFileNames.add(fileEntry.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private void writeHistogramToFile() {

        String fileName = OUTPUT_DIRECTORY_PATH + "wc_result.txt";
        Map<String, Integer> wordFrequency = histogramProcessor.getWordFrequency();
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                fileWriter.write(format("%-25s    %1d\n", entry.getKey(), entry.getValue()));
            }

            fileWriter.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private void writeMediansToFile() {

        String fileName = OUTPUT_DIRECTORY_PATH + "med_result.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            List<Double> runningMedians = medianProcessor.getRunningMedians();
            for (double median : runningMedians) {
                fileWriter.write(format("%.1f\n", median));
            }

            fileWriter.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}
