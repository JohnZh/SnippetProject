package com.john.purejava.concurent;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by JohnZh on 2020/9/13
 *
 * <p></p>
 */
public class ProducerCustomerLockTest {
    static class Store<T> {
        LinkedList<T> linkedList = new LinkedList();
        ReentrantLock lock = new ReentrantLock();
        Condition producerCon = lock.newCondition();
        Condition customerCon = lock.newCondition();
        private static final int MAX = 5;

        public void put(T t) {
            try {
                lock.lock();
                while (linkedList.size() == MAX) {
                    try {
                        producerCon.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " put " + t);
                linkedList.add(t);
                customerCon.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public T get() {
            try {
                lock.lock();
                while (linkedList.isEmpty()) {
                    try {
                        customerCon.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                T t = linkedList.removeFirst();

                System.out.println(Thread.currentThread().getName() + "GET: " + t);

                producerCon.signalAll();
                return t;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void run() {
        ExecutorService service = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());

        Store<String> store = new Store();

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    store.put("ppp " + finalI + " " + j);
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                for (int j = 0; j < 2; j++) {
                    String s = store.get();
                }
            });
        }
    }

}
