package Task02;

public class Summarizer implements Runnable {
    ListForThreads listForThreads;

    public Summarizer(ListForThreads listForThreads) {
        this.listForThreads = listForThreads;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (listForThreads) {
                if (listForThreads.getLastSummarizerIndex() < listForThreads.getLastWriterIndex()) {
                    listForThreads.setSum(listForThreads.getSum() + listForThreads.getList().get(listForThreads.getLastSummarizerIndex() + 1));
                    listForThreads.setLastSummarizerIndex(listForThreads.getLastSummarizerIndex() + 1);
                    System.out.println("sum = " + listForThreads.getSum());
                }
//                try {
//                    sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                listForThreads.notifyAll();
                try {
                    listForThreads.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
