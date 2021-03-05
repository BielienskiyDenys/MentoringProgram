package task_02;

/**
 * Deadlocks
 * Cost: 1 point.
 * <p>
 * Create three threads:
 * 1st thread is infinitely writing random number to the collection;
 * 2nd thread is printing sum of the numbers in the collection;
 * 3rd is printing square root of sum of squares of all numbers in the collection.
 * Make these calculations thread-safe using synchronization block. Fix the possible deadlock.
 */

public class Task02 {

    public static void main(String[] args) {
        ListForThreads listForThreads = new ListForThreads();

        Thread writer = new Thread(new Writer(listForThreads));
        Thread summarizer = new Thread(new Summarizer(listForThreads));
        Thread rooter = new Thread(new Rooter(listForThreads));

        writer.start();
        summarizer.start();
        rooter.start();

    }
}
