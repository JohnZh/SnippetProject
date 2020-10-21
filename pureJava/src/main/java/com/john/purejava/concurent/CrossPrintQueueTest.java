package com.john.purejava.concurent;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by JohnZh on 2020/9/14
 *
 * <p></p>
 */
public class CrossPrintQueueTest extends CrossPrintTest {
    SynchronousQueue<Integer> queue = new SynchronousQueue<>();

    @Override
    public Runnable getTaskA() {
        return () -> {
            int count = 0;
            while (count < 26) {
                try {
                    queue.put(count);
                    Integer take = queue.take();
                    System.out.print(count = take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };
    }

    @Override
    public Runnable getTaskB() {
        return () -> {
            Integer take = null;
            while (take == null || take < 26) {
                try {
                    take = queue.take();
                    System.out.print((char) ('A' + take));
                    queue.put(++take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };
    }
}
