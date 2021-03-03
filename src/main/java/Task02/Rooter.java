package Task02;

public class Rooter implements Runnable{
    ListForThreads listForThreads;

    public Rooter(ListForThreads listForThreads) {
        this.listForThreads = listForThreads;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (listForThreads) {
                if (listForThreads.getLastRooterIndex() < listForThreads.getLastWriterIndex()) {
                    listForThreads.setLastRooterIndex(listForThreads.getLastRooterIndex() + 1);
                    listForThreads.setSumOfSquares(listForThreads.getSumOfSquares() + (listForThreads.getList().get(listForThreads.getLastRooterIndex()) * listForThreads.getList().get(listForThreads.getLastRooterIndex())));
                    System.out.println("sqrtOfSumOfSqures = " + Math.sqrt(listForThreads.getSumOfSquares()));
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
