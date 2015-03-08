import java.util.Map;
import java.util.TreeMap;

/**
 * Created by myho on 3/7/15.
 */
public class HistogramProcessor {

    private final Map<String, Integer> wordFrequency;

    public HistogramProcessor() {
        wordFrequency = new TreeMap<String, Integer>();
    }

    public void add(String word) {
        // Get the current count of this word, add one, and then store the new count
        int count = getCount(word) + 1;
        wordFrequency.put(word, count);
    }

    private int getCount(String word) {
        if (wordFrequency.containsKey(word)) {
            // The word has occurred before, so get its count from the map
            return wordFrequency.get(word);
        } else {  // No occurrences of this word
            return 0;
        }
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }
}
