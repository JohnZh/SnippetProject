package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/4
 *
 * <p>分治思想：随机选择一个轴心，其左边全部都是比它小的数，其右边都是比它大的数，左右游标相遇点就是轴心新位置</p>
 * <p>此时轴心的左边和右边分别为另外的两个待排序数列，对其左边右边分别再次执行分治</p>
 * <p>结束条件，数列长度为 1，该数列必然有序，递归结束</p>
 */
public class QuickSort implements Sortable {

    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int start, int end) {
        if (start >= end) return;

        int left = start;
        int right = end;
        int pivotV = array[start];

        while (left < right) {
            while (left < right && array[right] > pivotV ) {
                right--;
            }

            if (left < right && array[right] < pivotV) {
                array[left++] = array[right];
            }

            while (left < right && array[left] < pivotV) {
                left++;
            }

            if (left < right && array[left] > pivotV) {
                array[right--] = array[left];
            }
        }

        if (left == right) {
            array[left] = pivotV;
            quickSort(array, start, left - 1);
            quickSort(array, left + 1, end);
        }
    }
}
