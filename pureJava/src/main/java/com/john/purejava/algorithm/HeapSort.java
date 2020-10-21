package com.john.purejava.algorithm;

/**
 * Created by JohnZh on 2020/9/4
 *
 * <p>从树的最后 array.length / 2 - 1 开始构造大顶堆，每次对子堆进行调整的时候，与子堆顶交互的子节点都需要递归继续调整</p>
 * <p>一次调整之后，根必然为最大的数，和末尾数据交互位置，长度减一，重复上面的流程</p>
 */
public class HeapSort implements Sortable {
    @Override
    public void sort(int[] array) {
        int len = array.length;
        int i;
        while ((i = len / 2 - 1) >= 0) {
            for (; i >= 0; i--) {
                adjustHeap(array, i, len);
            }
            swap(array, 0, len - 1);
            len--;
        }
    }

    private void adjustHeap(int[] array, int i, int len) {
        int leftChild = i * 2 + 1;
        int rightChild = leftChild + 1;

        if (leftChild >= len) return; // leaf

        int adjustedChild = leftChild;
        if (rightChild < len && array[leftChild] < array[rightChild]) {
            adjustedChild = rightChild;
        }
        if (array[i] < array[adjustedChild]) {
            swap(array, i, adjustedChild);
            adjustHeap(array, adjustedChild, len);
        }
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
