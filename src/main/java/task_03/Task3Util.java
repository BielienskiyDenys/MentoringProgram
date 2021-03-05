package task_03;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Task3Util {
    private static List<String> bodies = Arrays.asList("Breaking news! Yesterday mr.Freeman announced...",
                "Our life will never be the same after statement...",
                "Today we will talk about cost-efficiency in...",
                "Cheers love, cavalry's here! Lena Oxton interview...",
                "Who's mysterious mr.Sandman, and why entering dreams...",
                "Why should Sammy spread his wings and fly away...",
                "Bicycle riding opens up new era of modern world...",
                "Will friends be friends up to the end...",
                "Radio tells us: 'Ga-ga!' Why should we listen to...",
                "She keeps her Moet et Chandon in her pretty cabinet...",
                "All I want to say is that they don't really care...",
                "I know that the spades are the swords of a soldier...",
                "I know that the clubs are weapons of war...",
                "I know that diamonds mean money for this art...");

    public static void safeSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static Message getRandomMessage() {
        return new Message(getRandomTheme(), getRandomMessageBody());
    }

    public static String getRandomMessageBody() {
        Random random = new Random();
        return bodies.get(random.nextInt(bodies.size()));
    }

    public static Theme getRandomTheme() {
        Random random = new Random();
        return Theme.values()[random.nextInt(Theme.values().length)];
    }
}
