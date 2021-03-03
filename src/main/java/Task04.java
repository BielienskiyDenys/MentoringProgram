/** Cost: 1 point.

        Create simple object pool with support for multithreaded environment. No any extra inheritance, polymorphism or generics needed here, just implementation of simple class: */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** * Pool that block when it has not any items or it full */

public class Task04 {
    /** * Creates filled pool of passed size *
     * * @param size of pool */
    // public BlockingObjectPool(int size) { ... }
    /** * Gets object from pool or blocks if pool is empty * * @return object from pool */
    // ublic Object get() { ... } /** * Puts object to pool or blocks if pool is full * * @param object to be taken back to pool */
    // public void take(Object object) { ... }
    public static void main(String[] args) {


        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        System.out.println(nowAsISO);
    }
}
