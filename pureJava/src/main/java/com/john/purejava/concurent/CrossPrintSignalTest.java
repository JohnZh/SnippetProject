package com.john.purejava.concurent;

/**
 * Created by JohnZh on 2020/9/13
 *
 * <p></p>
 */
public class CrossPrintSignalTest extends CrossPrintTest {

    private int count;
    private volatile boolean started;
    private Object lock = new Object();

    @Override
    public Runnable getTaskA() {
        return () -> {
            synchronized (lock) {
                if (!started) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (count <= 26) {
                    System.out.print(count);
                    lock.notify();
                    if (count == 26) {
                        return;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public Runnable getTaskB() {
        return () -> {
            synchronized (lock) {
                if (!started) started = true;
                while (count < 26) {
                    char a = (char) ('A' + count++);
                    System.out.print(a);
                    lock.notify();
                    if (count == 26) {
                        return;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
