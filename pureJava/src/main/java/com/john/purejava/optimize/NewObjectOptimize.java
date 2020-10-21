package com.john.purejava.optimize;

import java.util.LinkedList;

/**
 * Created by JohnZh on 2020/9/7
 *
 * <p></p>
 */
public class NewObjectOptimize implements Testable {
    @Override
    public void test() {
        LinkedList list = new LinkedList();

        long start;
        long end;

        try {
            MyObj myObj = new MyObj();
            MyObj obj = (MyObj) myObj.clone();
            obj.id = 321321;
            System.out.println(myObj == obj);
            System.out.println(myObj.child == obj.child);
            System.out.println(myObj.child2 == obj.child2);
            System.out.println(myObj.id);
            System.out.println(obj.id);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

//        start = TimeUtils.getCurrentTime(false);
//        for (int i = 0; i < 100000; i++) {
//            list.addFirst(new MyObj());
//        }
//        end = TimeUtils.getCurrentTime(false);
//        System.out.println(end - start);
//
//
//        MyObj myObj = new MyObj();
//
//        try {
//            start = TimeUtils.getCurrentTime(false);
//            for (int i = 0; i < 100000; i++) {
//                list.addFirst(myObj.clone());
//            }
//            end = TimeUtils.getCurrentTime(false);
//            System.out.println(end - start);
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
    }

    private static class MyObj implements Cloneable {

        Child child;
        Child2 child2;
        int id;

        public MyObj() {
            child = new Child();
            child2 = new Child2();
            id = 123123;
            System.out.println("MyObj.MyObj");
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            MyObj obj = (MyObj) super.clone();
            obj.child2 = (Child2) child2.clone();
            return obj;
        }
    }

    private static class Child {

    }

    private static class Child2 implements Cloneable {

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
