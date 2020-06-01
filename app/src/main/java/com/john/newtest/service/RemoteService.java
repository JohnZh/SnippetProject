package com.john.newtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.john.newtest.aidl.People;
import com.john.newtest.aidl.PeopleAidl;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Modified by john on 2020/4/17
 * <p>
 * 假设它是另外一个 app 的 Service
 */
public class RemoteService extends Service {

    private static final String TAG = "RemoteService";

    List<People> mList = new ArrayList<>();

    private IBinder mIBinder = new PeopleAidl.Stub() {
        @Override
        public List<People> getList() throws RemoteException {
            return mList;
        }

        @Override
        public void addPerson(People p) throws RemoteException {
            mList.add(p);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mIBinder ;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
