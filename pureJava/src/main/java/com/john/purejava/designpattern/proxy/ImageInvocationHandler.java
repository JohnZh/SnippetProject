package com.john.purejava.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by JohnZh on 2020/5/27
 *
 * <p></p>
 */
public class ImageInvocationHandler implements InvocationHandler {

    private Object obj;

    public ImageInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        System.out.println(method);
        return method.invoke(obj, objects);
    }
}
