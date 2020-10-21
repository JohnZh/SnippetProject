package com.john.purejava.concurent;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JohnZh on 2020/9/13
 *
 * <p></p>
 */
public class ProducerCustomerSyncTest {

    public static void run() {
        ExecutorService service  = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());

        ConcurrentStore<String> concurrentStore = new ConcurrentStore();

        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 2; j++) {
                        System.out.println(concurrentStore.get());
                    }
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    concurrentStore.put(Thread.currentThread().getName() + " " + j);
                }
            });
        }

        //testThreads(concurrentStore);
    }

    private static void testThreads(ConcurrentStore<String> concurrentStore) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    System.out.println(concurrentStore.get());
                }
            }, "C" + i).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    concurrentStore.put(Thread.currentThread().getName() + " " + j);
                }
            }, "P" + i).start();
        }
    }

    static class ConcurrentStore<T> {
        LinkedList<T> list;
        private int MAX = 5;
        private int count;

        public ConcurrentStore() {
            this.list = new LinkedList();
        }

        public synchronized void put(T t) {
            while (list.size() == MAX) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            list.add(t);
            count++;
            System.out.println("count: " + count);
            this.notifyAll();
        }

        public synchronized T get() {
            while (list.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T remove = list.remove();
            count++;
            System.out.println("count: " + count);
            this.notifyAll();
            return remove;
        }
    }
}
