package util;

import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanTimer implements Runnable{
    private int secondsToSleep;
    private AtomicBoolean stop;

    public BooleanTimer(int secondsToSleep, AtomicBoolean stop) {
        this.secondsToSleep = secondsToSleep;
        this.stop = stop;
    }

    @Override
    public void run() {
        System.out.println("TIMER starts");
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop.set(!stop.get());
        System.out.println("TIMER ends");
    }
}
