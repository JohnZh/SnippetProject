package com.john.common;

import android.app.Application;
import android.content.Context;

import com.john.jrouter.JRouter;

/**
 * Created by JohnZh on 2020/7/9
 *
 * <p></p>
 */
public class BaseApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        JRouter.get().init(this);
    }
}
