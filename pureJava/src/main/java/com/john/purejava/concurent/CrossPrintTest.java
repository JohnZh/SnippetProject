package com.john.purejava.concurent;

/**
 * Created by JohnZh on 2020/9/14
 *
 * <p></p>
 */
public abstract class CrossPrintTest {
    public static void run(CrossPrintTest test) {
        test.runThreads(test.getTaskA(), test.getTaskB());
    }

    public abstract Runnable getTaskA();

    public abstract Runnable getTaskB();

    private void runThreads(Runnable a, Runnable b) {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(a);
        threads[1] = new Thread(b);

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
