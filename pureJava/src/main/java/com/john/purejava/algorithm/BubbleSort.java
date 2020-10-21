package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/5
 *
 * <p></p>
 */
public class BubbleSort implements Sortable {
    @Override
    public void sort(int[] array) {
        bubbleSort(array, 0, array.length);
    }

    private void bubbleSort(int[] array, int start, int length) {
        if (length == 1) return;

        for (int i = start; i < length - 1; i++) {
            if (array[i] > array[i + 1]) {
                swap(array, i, i + 1);
            }
        }
        bubbleSort(array, 0, length - 1);
    }

    private void swap(int[] array, int i, int i1) {
        int tmp = array[i];
        array[i] = array[i1];
        array[i1] = tmp;
    }
}
