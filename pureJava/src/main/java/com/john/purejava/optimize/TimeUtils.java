package com.john.purejava.optimize;

/**
 * Created by JohnZh on 2020/9/7
 *
 * <p></p>
 */
public class TimeUtils {

    public static long getCurrentTime(boolean nano) {
        if (nano) {
            return System.nanoTime();
        }
        return System.currentTimeMillis();
    }
}

