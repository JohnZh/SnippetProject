package com.john.purejava.test;

import com.john.purejava.optimize.ReUnsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by JohnZh on 2020/9/17
 *
 * <p></p>
 */
public class ThreadLocalRandom {

    private static final sun.misc.Unsafe U = ReUnsafe.getUnsafe();
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;

    static {
        try {
            SEED = U.objectFieldOffset
                    (Thread.class.getDeclaredField("threadLocalRandomSeed"));
            PROBE = U.objectFieldOffset
                    (Thread.class.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = U.objectFieldOffset
                    (Thread.class.getDeclaredField("threadLocalRandomSecondarySeed"));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    /**
     * The increment for generating probe values.
     */
    private static final int PROBE_INCREMENT = 0x9e3779b9;

    /**
     * The increment of seeder per new instance.
     */
    private static final long SEEDER_INCREMENT = 0xbb67ae8584caa73bL;

    private static final AtomicInteger probeGenerator = new AtomicInteger();
    private static final AtomicLong seeder
            = new AtomicLong(mix64(System.currentTimeMillis()) ^
            mix64(System.nanoTime()));

    public static final void localInit() {
        int p = probeGenerator.addAndGet(PROBE_INCREMENT);
        int probe = (p == 0) ? 1 : p; // skip 0
        long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
        Thread t = Thread.currentThread();
        U.putLong(t, SEED, seed);
        U.putInt(t, PROBE, probe);
    }

    public static final int getProbe() {
        return U.getInt(Thread.currentThread(), PROBE);
    }

    private static long mix64(long z) {
        z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
        z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
        return z ^ (z >>> 33);
    }

    public static void main(String[] args) {
        int probe = ThreadLocalRandom.getProbe();
        System.out.println(probe);
        if (probe == 0) {
            ThreadLocalRandom.localInit();
            for (int i = 0; i < 10; i++) {
                probe = ThreadLocalRandom.getProbe();
                System.out.println(probe);
            }
        }
    }
}
