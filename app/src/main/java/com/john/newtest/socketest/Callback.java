package com.john.newtest.socketest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by JohnZh on 2020/10/17
 *
 * <p></p>
 */
public abstract class Callback<T> {

    protected abstract void onCallback(T data);

    public Type getGenericType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        return parameterizedType.getActualTypeArguments()[0];
    }
}
