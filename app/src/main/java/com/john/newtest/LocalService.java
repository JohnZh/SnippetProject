package com.john.newtest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Modified by john on 2020/4/17
 * <p>
 * Description:
 */
public class LocalService extends Service {

    LocalBinder mLocalBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }
}
