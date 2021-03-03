package Task03;

public class Producer implements Runnable {
    private MessageQueue messageQueue;

    public Producer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;

    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            messageQueue.addMessage(Task3Util.getRandomMessage());
//            String s = messageQueue.addMessage(Task3Util.getRandomMessage())?"added; ":"tried; ";
//            System.out.print(Thread.currentThread().getName()+ " " + messageQueue.queue.size() + " " + s);
            Task3Util.safeSleep(100);
        }
    }
}
