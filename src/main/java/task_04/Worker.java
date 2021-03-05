package task_04;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class Worker implements Runnable {
    private AtomicBoolean stop;
    BlockingObjectPool blockingObjectPool;

    public Worker(BlockingObjectPool blockingObjectPool, AtomicBoolean stop) {
        this.blockingObjectPool = blockingObjectPool;
        this.stop = stop;
    }


    @Override
    public void run() {
        while (!stop.get()) {
            System.out.println(Thread.currentThread().getName() + " going to get object;");
            Object object = blockingObjectPool.get();
            System.out.println(Thread.currentThread().getName() + " has taken Object " + object + " from the pool;");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " going to put object back;");
            blockingObjectPool.take(object);
            System.out.println(Thread.currentThread().getName() + " has returned Object " + object + " to the pool;");
        }
        System.out.println(Thread.currentThread().getName() + " closing.");
    }
}
