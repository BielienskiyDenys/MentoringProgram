package Task03;

import java.util.LinkedList;
import java.util.List;

/**
 * Where’s Your Bus, Dude?
 * Cost: 1 point.
 * <p>
 * Implement message bus using Producer-Consumer pattern.
 * Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
 * Implement producer, which will generate and post randomly messages to the queue.
 * Implement consumer, which will consume messages on specific topic and log to the console message payload.
 * (Optional) Application should create several consumers and producers that run in parallel.
 */

public class Task03 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();
        List<Thread> threads = setupThreads(messageQueue);

        Thread timer = new Thread(new Timer(5, threads));
        timer.start();

        startAndJoin(threads);
        safeJoin(timer);


    }

    private static List<Thread> setupThreads(MessageQueue messageQueue) {
        List<Thread> threads = new LinkedList<>();
        threads.add(new Thread(new Producer(messageQueue)));
        threads.add(new Thread(new Producer(messageQueue)));
        threads.add(new Thread(new MessageConsumer(messageQueue, Theme.MOVIES)));
        threads.add(new Thread(new MessageConsumer(messageQueue, Theme.POLITICS)));
        threads.add(new Thread(new MessageConsumer(messageQueue, Theme.SPORT)));
        return threads;
    }

    private static void startAndJoin(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            safeJoin(t);
        }
    }

    private static void safeJoin(Thread timer) {
        try {
            timer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
