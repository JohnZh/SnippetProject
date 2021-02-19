package com.john.jrouter;

/**
 * Created by JohnZh on 2020/7/8
 *
 * <p>路由结果</p>
 */
public interface RouteCallback {
    void onLost(RoutePath path);

    void onFound(RoutePath path);

    void onArrived(RoutePath path);
}
