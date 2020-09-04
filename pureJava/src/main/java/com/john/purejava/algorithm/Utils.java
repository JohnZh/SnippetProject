package com.john.purejava.algorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by JohnZh on 2020/9/4
 *
 * <p></p>
 */
public class Utils {

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            System.out.printf("%d ", array[i]);
        }
        System.out.println(array[array.length - 1]);
    }

    public static void initRandomArray(int[] ar) {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < ar.length; ) {
            int anInt = random.nextInt(100);
            if (set.add(anInt)) {
                ar[i++] = anInt;
            }
        }
    }
}
