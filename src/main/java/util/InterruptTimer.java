package util;

import java.util.List;

public class InterruptTimer implements Runnable{
    private int secondsToSleep;
    private List<Thread> toInterrupt;

    public InterruptTimer(int secondsToSleep, List<Thread> toInterrupt) {
        this.secondsToSleep = secondsToSleep;
        this.toInterrupt = toInterrupt;
    }

    @Override
    public void run() {
        System.out.println("TIMER starts");
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Thread t : toInterrupt) {
            t.interrupt();
        }
        System.out.println("TIMER ends");
    }
}
