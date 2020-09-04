package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/4
 *
 * <p></p>
 */
public class SortTest {
    private Sortable sortable;

    public SortTest(Sortable sortable) {
        this.sortable = sortable;
    }

    public void test() {
        int[] array = new int[20];
        Utils.initRandomArray(array);

        Utils.printArray(array);
        sortable.sort(array);
        Utils.printArray(array);
    }
}
