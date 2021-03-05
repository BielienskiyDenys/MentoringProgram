package task_04;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

/**
 * Pool that block when it has not any items or it full
 */
public class BlockingObjectPool {
    private final int size;
    private int index;
    private Object[] storage;

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size, AtomicBoolean stop) {
        this.size = size;
        this.storage = new Object[size];
        for (int i = 0; i < size; i++) {
            storage[i] = new Object(){
                    Random random = new Random();
                    String s = String.valueOf(random.nextInt(1000));
                @Override
                public String toString() {
                    return s;
                }
            };
        }
        index = size - 1;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() {
        while (true) {
            synchronized (this) {
                if (index >= 0 && index < size) {
                    Object object = storage[index];
                    storage[index] = null;
                    index--;
                    notifyAll();
                    return object;
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public void take(Object object) {
        while (true) {
            synchronized (this) {
                if (index < size - 1) {
                    index++;
                    storage[index] = object;
                    notifyAll();
                    return;
                } else {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {

                    }
                }
            }
        }
    }
}
