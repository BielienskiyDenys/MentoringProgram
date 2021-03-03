package Task03;

public class MessageConsumer implements Runnable {
    private MessageQueue messageQueue;
    private Theme theme;

    public MessageConsumer(MessageQueue messageQueue, Theme theme) {
        this.messageQueue = messageQueue;
        this.theme = theme;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(theme.name());
        while (!Thread.currentThread().isInterrupted()) {
            Message message = messageQueue.popMessage(theme);
            if (message != null) {
                System.out.println(Thread.currentThread().getName() +": '" + message.getBody() + "'");
            }
            Task3Util.safeSleep(300);
        }
    }
}
