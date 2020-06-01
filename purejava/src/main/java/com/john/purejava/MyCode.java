package com.john.purejava;

import com.john.purejava.annoprocesser.TableUtils;
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
        testDynamicProxy();
    }

    private static void testDynamicProxy() {
        LocalImage localImage = new LocalImage("im1.png");
        Image imageProxy = (Image) Proxy.newProxyInstance(localImage.getClass().getClassLoader(),
                localImage.getClass().getInterfaces(),
                new ImageInvocationHandler(localImage));
        imageProxy.display();
        imageProxy.hashCode();
    }

    private static void testProxyPattern() {
        ImageProxy proxy = new ImageProxy("im.png");
        proxy.display();
    }

    private static void testBuilderPattern() {
        Product product = Product.builder()
                .buildPart1("part1")
                .buildPart2("part2")
                .bind();
        System.out.println(product);
    }

    private static void testAnnotation() {
        TableUtils.ConnectionSource source = new TableUtils.ConnectionSource();
        TableUtils.createTable(source, User.class);
        TableUtils.createTable(source, UserOrder.class);
    }
}
