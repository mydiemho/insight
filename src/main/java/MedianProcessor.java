import com.google.common.collect.MinMaxPriorityQueue;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by myho on 3/7/15.
 */
public class MedianProcessor {

    private final MinMaxPriorityQueue<Integer> minHeap;
    private final MinMaxPriorityQueue<Integer> maxHeap;
    private final List<Double> runningMedians;

    // # of lines read so far, used to keep track of values in both heap
    private int lineCount = 0;

    public MedianProcessor() {
        minHeap =  MinMaxPriorityQueue.create();
        maxHeap =  MinMaxPriorityQueue.create();
        runningMedians = new LinkedList<Double>();
    }

    public void add(int wordCount) {
        insert(wordCount);
        double median = calculateMedian();
        runningMedians.add(median);
    }

    private void insert(int wordCount) {
        // even number of elements before insertion, adding an element won't violate size restriction
        if (lineCount % 2 == 0) {
            // insert into maxHeap
            maxHeap.add(wordCount);
            lineCount += 1;

            // do nothing if minHeap is empty
            if (minHeap.size() == 0) {
                return;
            }

            // newly inserted element violated order requirement, has to exchange the root element
            if (maxHeap.peekLast() > minHeap.peekFirst()) {
                int toMin = maxHeap.removeLast();
                int toMax = minHeap.removeFirst();
                minHeap.add(toMin);
                maxHeap.add(toMax);
            }
        } else {
            maxHeap.add(wordCount);
            int toMin = maxHeap.removeLast();
            minHeap.add(toMin);
            lineCount += 1;
        }
    }


    private double calculateMedian() {
        if (lineCount % 2 == 0) {
            return (maxHeap.peekLast() + minHeap.peekFirst()) / 2.0;
        } else {
            return maxHeap.peekLast();
        }
    }

    public List<Double> getRunningMedians() {
        return runningMedians;
    }
}
