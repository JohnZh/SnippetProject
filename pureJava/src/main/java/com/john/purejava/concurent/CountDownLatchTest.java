package com.john.purejava.concurent;

import java.util.concurrent.CountDownLatch;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public class CountDownLatchTest {

    static class Worker implements Runnable {

        CountDownLatch startSignal;
        CountDownLatch doneSignal;
        private int id;

        public Worker(int id, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                System.out.println("Thread " + id + " is working....");
                Thread.sleep(5000);
                System.out.println("Thread " + id + " is done");
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute() {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker(i, startSignal, doneSignal)).start();
        }

        System.out.println("Main thread say: start!");
        startSignal.countDown();
        System.out.println("Main thread is still here");
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread is done!");
    }
}
