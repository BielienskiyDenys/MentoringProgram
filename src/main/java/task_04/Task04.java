package task_04;



import util.BooleanTimer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** Cost: 1 point.

        Create simple object pool with support for multithreaded environment. No any extra inheritance, polymorphism or generics needed here, just implementation of simple class: */


public class Task04 {
    private static AtomicBoolean stop = new AtomicBoolean(false);
    public static void main(String[] args) {

        BlockingObjectPool blockingObjectPool = new BlockingObjectPool(2, stop);
        List<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(new Worker(blockingObjectPool, stop)));
        }

        Thread timer = new Thread(new BooleanTimer(2, stop));
        timer.start();

        util.Util.startAndJoin(threads);
        util.Util.safeJoin(timer);


    }
}
