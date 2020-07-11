package com.john.common;

import android.app.Application;

import com.john.jrouter.JRouter;

/**
 * Created by JohnZh on 2020/7/9
 *
 * <p></p>
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        JRouter.get().init(this);
    }
}
