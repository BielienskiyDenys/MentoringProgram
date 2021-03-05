package task_01;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Das Experiment
 Cost: 1 point.

 Create HashMap<Integer, Integer>. The first thread adds elements into the map,
 the other go along the given map and sum the values. Threads should work before
 catching ConcurrentModificationException. Try to fix the problem with ConcurrentHashMap
 and Collections.synchronizedMap(). What has happened after simple Map implementation
 exchanging? How it can be fixed in code? Try to write your custom ThreadSafeMap with
 synchronization and without. Run your samples with different versions
 of Java (6, 8, and 10, 11) and measure the performance.
 Provide a simple report to your mentor.
 */

public class Task01 {

    public static void main(String[] args) {
        runTwoThreads(new HashMap<>());
        runTwoThreads(new ConcurrentHashMap<>());
        runTwoThreads(Collections.synchronizedMap(new HashMap<>()));

    }

    private static void runTwoThreads(Map<Integer, Integer> map) {
        ThreadsWithoutSync threadsWithoutSync = new ThreadsWithoutSync(map);
        threadsWithoutSync.writer.start();
        threadsWithoutSync.summarizer.start();
        try {
            Thread.currentThread().sleep(1000);
            threadsWithoutSync.writer.join();
            threadsWithoutSync.summarizer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int result = threadsWithoutSync.map.values().stream().mapToInt(e -> e).sum();
        System.out.println("Result: " + result);
        System.out.println();
    }

}
