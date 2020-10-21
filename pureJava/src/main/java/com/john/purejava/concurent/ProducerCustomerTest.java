package com.john.purejava.concurent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JohnZh on 2020/9/13
 *
 * <p></p>
 */
public class ProducerCustomerTest {

    public static void execute() {
        Store store = new Store(new Producer());
        for (int i = 0; i < 3; i++) {
            store.welcome(new Customer(i));
        }
    }

    static class Goods {
        int id;

        public Goods(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Goods(" + id + ")";
        }
    }

    static class Store {
        List<Goods> goodList = new LinkedList<>();
        Producer producer;
        int MAX_GOODS_SIZE = 5;
        ExecutorService service = Executors.newCachedThreadPool();
        boolean closed;

        public Store(Producer producer) {
            this.producer = producer;
            this.producer.store = this;
            this.service.execute(producer);
        }

        public void welcome(Customer customer) {
            customer.store = this;
            service.execute(customer);
        }

        public boolean isClosed() {
            return closed;
        }

        public void close() {
            closed = true;
            service.shutdownNow();
        }

        public void add(Goods goods) {
            goodList.add(goods);
        }

        public boolean isEmpty() {
            return goodList.size() == 0;
        }

        public boolean isFull() {
            return goodList.size() == MAX_GOODS_SIZE;
        }

        @Override
        public String toString() {
            return "Store goods[" + goodList.size() + "]";
        }

        public Goods get() {
            return goodList.remove(0);
        }
    }

    static class Producer implements Runnable {
        Store store;
        int count;

        @Override
        public void run() {
            while (true) {
                synchronized (store) {
                    if (!store.isFull()) {
                        Goods goods = new Goods(count++);
                        store.add(goods);
                        System.out.println("Producer add " + goods + " | " + store);
                        store.notifyAll();
                    } else {
                        try {
                            store.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (count == 20) {
                        System.out.println("Producer finish today.");
                        store.close();
                        return;
                    }
                } // sync

                if ((count & 1) == 0) {
                    Thread.yield();
                }
            }
        }
    }

    static class Customer implements Runnable {
        Store store;
        int id;

        public Customer(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Customer(" + id + ")";
        }

        @Override
        public void run() {
            while (true) {
                synchronized (store) {
                    if (!store.isEmpty()) {
                        Goods goods = store.get();
                        System.out.println(this + " consume " + goods + " | " + store);
                        store.notifyAll();
                    } else if (store.isClosed()) {
                        System.out.println("Store closed. " + this + " leave.");
                        return;
                    } else {
                        System.out.println(this + " wait.");
                        try {
                            store.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                } // sync
                Thread.yield();
            }
        }
    }
}


