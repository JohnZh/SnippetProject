package com.john.purejava.designpattern;

import com.john.purejava.designpattern.builder.Product;
import com.john.purejava.designpattern.proxy.Image;
import com.john.purejava.designpattern.proxy.ImageInvocationHandler;
import com.john.purejava.designpattern.proxy.ImageProxy;
import com.john.purejava.designpattern.proxy.LocalImage;

import java.lang.reflect.Proxy;

/**
 * Created by JohnZh on 2020/6/1
 *
 * <p></p>
 */
public class PatternTester {

    public static void testDynamicProxy() {
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
}
