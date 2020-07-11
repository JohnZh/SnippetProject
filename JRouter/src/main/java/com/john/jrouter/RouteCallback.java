package com.john.jrouter;

/**
 * Created by JohnZh on 2020/7/8
 *
 * <p>路由结果</p>
 */
public interface RouteCallback {
    void onLost(RouteMsg msg);

    void onFound(RouteMsg msg);

    void onArrived(RouteMsg msg);
}
