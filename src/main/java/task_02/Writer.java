package task_02;

import util.Util;

public class Writer implements Runnable {
    private ListForThreads listForThreads;

    public Writer(ListForThreads listForThreads) {
        this.listForThreads = listForThreads;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (listForThreads) {
                listForThreads.setLastWriterIndex(listForThreads.getLastWriterIndex() + 1);
                listForThreads.getList().add(Util.getRandomNumberInRange(0,10));
                System.out.println("added new int");
                listForThreads.notifyAll();
//                try {
//                    sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                try {
                    listForThreads.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
