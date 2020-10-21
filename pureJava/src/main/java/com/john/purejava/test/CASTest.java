package com.john.purejava.test;

import com.john.purejava.optimize.ReUnsafe;

import sun.misc.Unsafe;

/**
 * Created by JohnZh on 2020/9/17
 *
 * <p></p>
 */
public class CASTest {
    volatile int lock;
    static long LOCK_OFFSET;
    static Unsafe unsafe = ReUnsafe.getUnsafe();

    static {
        try {
            LOCK_OFFSET = unsafe.objectFieldOffset(CASTest.class.getDeclaredField("lock"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        boolean cas = false;
        int count = 0;
        boolean stopLoop = false;

        for (; ; ) {
            if (stopLoop) {
                System.out.println("stop loop");
                System.out.println("count:: " + count);
                if (++count == 10) {
                    break;
                }
            } else if (!cas) {
                System.out.println("no cas");
                cas = true;
            } else if (unsafe.compareAndSwapInt(this, LOCK_OFFSET, 0, 1)) {
                try {
                    System.out.println("count: " + count);
                    if (++count == 5) {
                        stopLoop = true;
                    }
                } finally {
                    lock = 0;
                }
                cas = false;
            }
        }
    }
}
