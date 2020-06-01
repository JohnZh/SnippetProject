package com.john.purejava;

import com.john.purejava.annoprocesser.TableUtils;
import com.john.purejava.designpattern.PatternTester;
import com.john.purejava.designpattern.builder.Product;
import com.john.purejava.designpattern.proxy.Image;
import com.john.purejava.designpattern.proxy.ImageInvocationHandler;
import com.john.purejava.designpattern.proxy.ImageProxy;
import com.john.purejava.designpattern.proxy.LocalImage;
import com.john.purejava.model.User;
import com.john.purejava.model.UserOrder;

import java.lang.reflect.Proxy;

/**
 * Modified by john on 2020/3/20
 * <p>
 * Description:
 */
public class MyCode {

    public static void main(String[] args) {
        PatternTester.testDynamicProxy();
    }

    private static void testAnnotation() {
        TableUtils.ConnectionSource source = new TableUtils.ConnectionSource();
        TableUtils.createTable(source, User.class);
        TableUtils.createTable(source, UserOrder.class);
    }
}
