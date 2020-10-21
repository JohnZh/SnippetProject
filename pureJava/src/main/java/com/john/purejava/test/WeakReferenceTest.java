package com.john.purejava.test;

import java.lang.ref.WeakReference;

/**
 * Created by JohnZh on 2020/9/25
 *
 * <p></p>
 */
public class WeakReferenceTest {
    static WeakReference<B> reference;

    public static void main(String[] args) {
        test();
        System.gc();
        if (reference != null) {
            B b = reference.get();
            if (b != null) {
                System.out.println(b);
            } else {
                System.out.println("gc");
            }
        }

    }

    static class A {
        B b = new B();
    }

    static class B {
        @Override
        public String toString() {
            return "B{}";
        }
    }

    private static void test() {
        A a = new A();
        reference = new WeakReference<>(a.b);
        return;
    }
}
