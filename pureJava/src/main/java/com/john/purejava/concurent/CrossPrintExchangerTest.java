package com.john.purejava.concurent;

import java.util.concurrent.Exchanger;

/**
 * Created by JohnZh on 2020/9/14
 *
 * <p></p>
 */
public class CrossPrintExchangerTest extends CrossPrintTest {
    Exchanger<String> exchanger = new Exchanger();

    @Override
    public Runnable getTaskA() {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    try {
                        System.out.print(i + 1);
                        exchanger.exchange("ok");
                        exchanger.exchange("ok");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public Runnable getTaskB() {
        return new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    try {
                        exchanger.exchange("ok");
                        System.out.print(((char) ('A' + i)));
                        exchanger.exchange("ok");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
