package com.john.jrouter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by JohnZh on 2020/7/7
 *
 * <p></p>
 */
public class JRouter {

    private static JRouter sRouter;

    private Context mAppContext;
    private Handler mHandler;

    public static JRouter get() {
        if (sRouter == null) {
            synchronized (JRouter.class) {
                if (sRouter == null) {
                    sRouter = new JRouter();
                }
            }
        }
        return sRouter;
    }

    public void init(Application application) {
        mAppContext = application;
        mHandler = new Handler(mAppContext.getMainLooper());
        Register.init(mAppContext);
    }

    /**
     * 确保运行在主线程
     *
     * @param runnable
     */
    private void runInMainThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public RoutePath path(@NonNull String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("path cannot be empty");
        }
        return new RoutePath(path);
    }

    void route(final RoutePath routePath, final Context context, final int requestCode, final RouteCallback callback) {
        final Context curContext = context == null ? mAppContext : context;

        RouteRecord record = Register.findRecord(routePath);
        if (record != null) {
            routePath.setClazz(record.getClazz());
            routePath.setType(record.getType());
            if (callback != null) {
                callback.onFound(routePath);
            }
        } else {
            if (callback != null) {
                callback.onLost(routePath);
            }
            return;
        }

        switch (routePath.getType()) {
            case ACTIVITY:
                final Intent intent = new Intent(curContext, routePath.getClazz());
                if (routePath.getBundle() != null) {
                    intent.putExtras(routePath.getBundle());
                }

                // flag
                if (routePath.getFlag() != -1) { // custom flag
                    intent.setFlags(routePath.getFlag());
                } else if (!(curContext instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent, curContext, requestCode);
                        if (callback != null) {
                            callback.onArrived(routePath);
                        }
                    }
                });
                break;

            case FRAGMENT:
                Fragment fragment = getFragment(routePath);
                if (routePath.getBundle() != null) {
                    fragment.setArguments(routePath.getBundle());
                }
                if (fragment != null && callback != null && callback instanceof RouteCallbackWithInstance) {
                    ((RouteCallbackWithInstance) callback).onInstance(fragment);
                }
                break;

            case CLASS:
                Class clazz = record.getClazz();
                Object obj = getInstance(clazz);
                if (obj != null && callback != null && callback instanceof RouteCallbackWithInstance) {
                    ((RouteCallbackWithInstance) callback).onInstance(obj);
                }
                break;

            default:
                break;
        }
    }

    private Object getInstance(Class clazz) {
        try {
            Object o = clazz.newInstance();
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Fragment getFragment(RoutePath routePath) {
        Class clazz = routePath.getClazz();
        try {
            Object newInstance = clazz.newInstance();
            return (Fragment) newInstance;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void startActivity(Intent intent, Context context, int requestCode) {
        if (requestCode >= 0) {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                throw new RuntimeException("requestCode is only used for Activity");
            }
        } else {
            context.startActivity(intent);
        }
    }
}
