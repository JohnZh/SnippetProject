package com.john.purejava.test;

import org.openjdk.jol.info.ClassLayout;

/**
 * Created by JohnZh on 2020/9/17
 *
 * <p></p>
 */
public class JOLTest {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(S[].class).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
    }

    static class S {

    }
}
