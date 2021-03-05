package task_01;

import util.Util;

import java.util.Map;

/**
 * Das Experiment
 * Cost: 1 point.
 * <p>
 * Create HashMap<Integer, Integer>. The first thread adds elements into the map,
 * the other go along the given map and sum the values. Threads should work before
 * catching ConcurrentModificationException. Try to fix the problem with ConcurrentHashMap
 * and Collections.synchronizedMap(). What has happened after simple Map implementation
 * exchanging? How it can be fixed in code? Try to write your custom ThreadSafeMap with
 * synchronization and without. Run your samples with different versions
 * of Java (6, 8, and 10, 11) and measure the performance.
 * Provide a simple report to your mentor.
 */

public class ThreadsWithoutSync {
    Map<Integer, Integer> map;
    Integer result = 0;
    Thread writer = new Writer();
    Thread summarizer = new Summarizer();
    Integer sum = 0;

    public ThreadsWithoutSync(Map<Integer, Integer> map) {
        this.map = map;
    }

    private class Writer extends Thread {
        @Override
        public void run() {
            System.out.println("WRITER start");
            fillMap();
            System.out.println("WRITER end");
        }
    }

    private void fillMap() {
        for (int i = 0; i < 1000000; i++) {
            map.put(i, Util.getRandomNumberInRange(0, 100));
        }
    }

    private class Summarizer extends Thread {
        @Override
        public void run() {
            System.out.println("SUMMARIZER start");
            result = sumMap(map);
            System.out.println(result);
            System.out.println("SUMMARIZER end");
        }
    }


    private Integer sumMap(Map<Integer, Integer> map) {
        map.entrySet().forEach(e -> sum = e.getValue() + sum);
        return sum;
    }


}
