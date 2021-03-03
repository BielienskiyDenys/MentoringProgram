package Task03;

import java.util.List;

public class Timer implements Runnable{
    private int secondsToSleep;
    private List<Thread> toInterrupt;

    public Timer(int secondsToSleep, List<Thread> toInterrupt) {
        this.secondsToSleep = secondsToSleep;
        this.toInterrupt = toInterrupt;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Thread t : toInterrupt) {
            t.interrupt();
        }

    }
}
