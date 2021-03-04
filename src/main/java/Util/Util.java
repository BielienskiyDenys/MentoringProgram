package Util;

import java.util.List;
import java.util.Random;

public class Util {
    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public static boolean getRandomBoolean() {
        Random r = new Random();
        return r.nextBoolean();
    }

    public static void startAndJoin(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            safeJoin(t);
        }
    }

    public static void safeJoin(Thread timer) {
        try {
            timer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
