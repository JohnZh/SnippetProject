package com.john.purejava;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Modified by john on 2020/3/20
 * <p>
 * Description:
 */
public class MyCode {

    public static void main(String[] args) throws Exception {
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 5,
                5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5), new MyThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        */


    }

    private static class A {

    }
    private  static class B extends A {
    }
    private static class C extends B  {
    }


    static class MyThreadFactory implements ThreadFactory {

        private AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "Worker-" + atomicInteger.getAndIncrement());
        }
    }

//    private static void testAnnotation() {
//        TableUtils.ConnectionSource source = new TableUtils.ConnectionSource();
//        TableUtils.createTable(source, User.class);
//        TableUtils.createTable(source, UserOrder.class);
//    }
}
