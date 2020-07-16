package com.john.purejava.concurent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public class CyclicBarrierTest {

    static class Worker implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is working...");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " await()");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(11, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " last await()");
            }
        });

        System.out.println(Thread.currentThread().getName() + " is working...");

        for (int i = 0; i < 10; i++) {
            new Thread(new Worker(cyclicBarrier)).start();
        }

        try {
            System.out.println(Thread.currentThread().getName() + " await()");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " finish");
    }
}
