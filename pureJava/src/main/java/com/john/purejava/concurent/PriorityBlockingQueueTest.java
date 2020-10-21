package com.john.purejava.concurent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JohnZh on 2020/9/21
 *
 * <p></p>
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE,
                2, TimeUnit.SECONDS, new PriorityBlockingQueue<>(2));
        executor.allowCoreThreadTimeOut(true);

        List<Task> tasks = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            tasks.add(new Task(i));
        }
        Collections.sort(tasks);
        for (Task task : tasks) {
            System.out.print(task);
            System.out.print(" ");
        }

        System.out.println();

        Collections.reverse(tasks);
        for (Task task : tasks) {
            System.out.print(task);
            System.out.print(" ");
        }

        for (Task task : tasks) {
            executor.execute(task);
        }
    }

    static class Task implements Runnable, Comparable<Task> {

        @Override
        public String toString() {
            return "Task{" +
                    "priority=" + priority +
                    '}';
        }

        private int priority;

        public Task(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " with priority : " + priority);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int compareTo(Task task) {
            return priority - task.priority;
        }
    }
}
