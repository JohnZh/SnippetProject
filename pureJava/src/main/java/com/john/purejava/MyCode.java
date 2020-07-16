package com.john.purejava;

import com.john.purejava.annoprocesser.TableUtils;
import com.john.purejava.model.User;
import com.john.purejava.model.UserOrder;

/**
 * Modified by john on 2020/3/20
 * <p>
 * Description:
 */
public class MyCode {

    public static void main(String[] args) {
        //PatternTester.testChainOfResponsibilityVariety();

        //CountDownLatchTest.execute();
        //CyclicBarrierTest.execute();
    }

    private static void testAnnotation() {
        TableUtils.ConnectionSource source = new TableUtils.ConnectionSource();
        TableUtils.createTable(source, User.class);
        TableUtils.createTable(source, UserOrder.class);
    }

}
