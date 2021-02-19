package com.john.jrouter;

/**
 * Created by JohnZh on 2020/7/11
 *
 * <p></p>
 */
public abstract class RouteCallbackWithInstance<T> implements RouteCallback {

    @Override
    public void onLost(RoutePath path) {

    }

    @Override
    public void onFound(RoutePath path) {

    }

    @Override
    public void onArrived(RoutePath path) {

    }

    public abstract void onInstance(T instance);
}
