package com.john.purejava.optimize;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.john.purejava.optimize.TimeUtils.getCurrentTime;

/**
 * Created by JohnZh on 2020/9/7
 *
 * <p>除了头部插入 linkedlist 性能会远高于 ArrayList</p>
 * <p>其他插入情况，ArrayList 性能要么和他持平，要么稍差点毫秒之内，要么远高于</p>
 */
public class ArrayVsList implements Testable {

    @Override
    public void test() {
        testWithDataSizeInMid(500, false);
    }

    private void testWithDataSizeInMid(int dataSize, boolean nano) {
        System.out.println("Add in mid " + dataSize + " data: ");
        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();

        long start;
        long end;
        long arrayListDuration;
        long linkedListDuration;

        start = getCurrentTime(nano);
        for (int i = 0; i < dataSize; i++) {
            arrayList.add(arrayList.size() / 2, new Object());
        }
        end = getCurrentTime(nano);
        arrayListDuration = end - start;
        printDuration(ArrayList.class, arrayListDuration, nano);

        start = getCurrentTime(nano);
        for (int i = 0; i < dataSize; i++) {
            linkedList.add(linkedList.size() / 2, new Object());
        }
        end = getCurrentTime(nano);
        linkedListDuration = end - start;
        printDuration(LinkedList.class, linkedListDuration, nano);

        printFasterOne(arrayListDuration, linkedListDuration);
        System.out.println();
    }

    private void testWithDataSizeInTail(int dataSize, boolean nano) {
        System.out.println("Add in tail" + dataSize + " data: ");
        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();

        long start;
        long end;
        long arrayListDuration;
        long linkedListDuration;

        start = getCurrentTime(nano);
        for (int i = 0; i < dataSize; i++) {
            arrayList.add(new Object());
        }
        end = getCurrentTime(nano);
        arrayListDuration = end - start;
        printDuration(ArrayList.class, arrayListDuration, nano);

        start = getCurrentTime(nano);
        for (int i = 0; i < dataSize; i++) {
            linkedList.add(new Object());
        }
        end = getCurrentTime(nano);
        linkedListDuration = end - start;
        printDuration(LinkedList.class, linkedListDuration, nano);

        printFasterOne(arrayListDuration, linkedListDuration);
        System.out.println();
    }

    private void printFasterOne(long arrayListDuration, long linkedListDuration) {
        String fasterOne;
        if (arrayListDuration < linkedListDuration) {
            fasterOne = "ArrayList";
        } else if (arrayListDuration > linkedListDuration) {
            fasterOne = "LinkedList";
        } else {
            fasterOne = "Same";
        }
        System.out.println("Faster one: " + fasterOne + ", time diff(al - ll): " + (arrayListDuration - linkedListDuration));
    }

    private void printDuration(Class clazz, long duration, boolean nano) {
        String msg = clazz.getSimpleName() + " duration: " + duration + " in millis";
        if (nano) {
            msg = clazz.getSimpleName() + " duration: " + duration + " in nano";
        }
        System.out.println(msg);
    }
}
