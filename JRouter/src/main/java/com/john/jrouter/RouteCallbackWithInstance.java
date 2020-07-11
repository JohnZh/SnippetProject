package com.john.jrouter;

/**
 * Created by JohnZh on 2020/7/11
 *
 * <p></p>
 */
public abstract class RouteCallbackWithInstance<T> implements RouteCallback {

    @Override
    public void onLost(RouteMsg msg) {

    }

    @Override
    public void onFound(RouteMsg msg) {

    }

    @Override
    public void onArrived(RouteMsg msg) {

    }

    public abstract void onInstance(T instance);
}
