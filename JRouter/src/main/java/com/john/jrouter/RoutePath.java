package com.john.jrouter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by JohnZh on 2020/7/7
 *
 * <p>路由消息</p>
 */
public class RoutePath extends RouteRecord {
    private Bundle bundle;
    private int flag;

    public RoutePath(String path) {
        super(path);
        flag = -1;
    }

    public String getPath() {
        return path;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public int getFlag() {
        return flag;
    }

    private void initBundle() {
        if (bundle == null) {
            bundle = new Bundle();
        }
    }

    public RoutePath putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
        initBundle();
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    public RoutePath putParcelable(String key, Parcelable parcelable) {
        initBundle();
        bundle.putParcelable(key, parcelable);
        return this;
    }

    public RoutePath putInt(String key, int value) {
        initBundle();
        bundle.putInt(key, value);
        return this;
    }

    public RoutePath putLong(String key, long value) {
        initBundle();
        bundle.putLong(key, value);
        return this;
    }

    public RoutePath putDouble(String key, double value) {
        initBundle();
        bundle.putDouble(key, value);
        return this;
    }

    public RoutePath putString(String key, String value) {
        initBundle();
        bundle.putString(key, value);
        return this;
    }

    public RoutePath putBoolean(String key, boolean value) {
        initBundle();
        bundle.putBoolean(key, value);
        return this;
    }

    public RoutePath putStringArray(String key, String[] value) {
        initBundle();
        bundle.putStringArray(key, value);
        return this;
    }

    public RoutePath putBundle(String key, Bundle bundle) {
        initBundle();
        bundle.putBundle(key, bundle);
        return this;
    }

    public RoutePath setFlags(int flag) {
        initBundle();
        this.flag = flag;
        return this;
    }

    public RoutePath addFlags(int flag) {
        initBundle();
        this.flag |= flag;
        return this;
    }

    public void route() {
        route(null, -1, null);
    }

    public void route(Context context) {
        route(context, null);
    }

    public void route(RouteCallback callback) {
        route(null, callback);
    }

    public void route(Context context, RouteCallback callback) {
        route(context, -1, callback);
    }

    public void route(Context context, int requestCode) {
        route(context, requestCode, null);
    }

    public void route(Context context, int requestCode, RouteCallback callback) {
        JRouter.get().route(this, context, requestCode, callback);
    }
}
