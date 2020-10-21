package com.john.purejava.optimize;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Created by JohnZh on 2020/9/17
 *
 * <p></p>
 */
public class ReUnsafe {
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
