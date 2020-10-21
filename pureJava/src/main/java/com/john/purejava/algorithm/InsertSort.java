package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/5
 *
 * <p></p>
 */
public class InsertSort implements Sortable {
    @Override
    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int element = array[i];
            int insertIndex = binarySearchIndex(array, element, i);
            if (insertIndex != i) {
                moveElements(array, insertIndex, i);
                array[insertIndex] = element;
            }
        }
    }

    private void moveElements(int[] array, int insertIndex, int end) {
        for (int i = end; i > insertIndex; i--) {
            array[i] = array[i - 1];
        }
    }

    private int binarySearchIndex(int[] array, int element, int len) {
        int low = 0;
        int high = len - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] > element) {
                high = mid - 1;
            } else if (array[mid] < element) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return low;
    }
}
