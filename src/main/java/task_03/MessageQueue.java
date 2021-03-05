package task_03;

import java.util.LinkedList;


public class MessageQueue {
    public LinkedList<Message> queue = new LinkedList<>();
    private final int maxMessagesCount = 10;
    private final int minMessagesCount = 0;
    private int index = 0;

    public synchronized boolean addMessage(Message message) {
        if (index < maxMessagesCount) {
            queue.add(message);
            index++;
        } else {
            Task3Util.safeSleep(500);
            return false;
        }
        return true;
    }

    public synchronized Message popMessage(Theme consumersTheme) {
        while (true) {
            if (index > minMessagesCount && consumersTheme.equals(queue.getFirst().getTheme())) {
                index--;
                return queue.removeFirst();
            } else {
                return null;
            }
        }
    }

}
