package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/5
 *
 * <p></p>
 */
public class SelectionSort implements Sortable {
    @Override
    public void sort(int[] array) {
        selectionSort(array, 0, array.length);
    }

    private void selectionSort(int[] array, int start, int length) {
        if (start + 1 == length) return;

        int min = array[start];
        int j = start;
        for (int i = start + 1; i < length; i++) {
            if (min > array[i]) {
                min = array[i];
                j = i;
            }
        }
        if (j != start) {
            swap(array, start, j);
        }

        selectionSort(array, start + 1, length);
    }

    private void swap(int[] array, int i, int i1) {
        int tmp = array[i];
        array[i] = array[i1];
        array[i1] = tmp;
    }
}
