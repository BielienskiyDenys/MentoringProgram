package Util;

import java.util.Random;

public class Util {
    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
