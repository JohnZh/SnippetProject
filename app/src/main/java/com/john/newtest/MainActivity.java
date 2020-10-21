package com.john.newtest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;

import com.john.common.BaseActivity;
import com.john.jrouter.annotation.Route;
import com.john.newtest.aidl.SocketToggle;
import com.john.newtest.databinding.ActivityMainBinding;
import com.john.newtest.service.RemoteService;
import com.john.newtest.socketest.Callback;
import com.john.newtest.socketest.MarketSubscriber;
import com.john.newtest.socketest.Req;

@Route("/app/main")
public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;
    ServiceConnection connection;
    SocketToggle socketToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, getConn(), Service.BIND_AUTO_CREATE);

        mBinding.stop.setOnClickListener(v -> {
            MarketSubscriber.get().close();
        });
        mBinding.sub.setOnClickListener(v -> {
            MarketSubscriber.get().subscribe("Ag12");
        });
        mBinding.unsub.setOnClickListener(v -> {
            MarketSubscriber.get().unsubscribe("Ag12");
        });

        MarketSubscriber.get().setCallback(new Callback<Req>() {
            @Override
            protected void onCallback(Req data) {
                mBinding.text.setText(data.getData());
            }
        });
    }

    private ServiceConnection getConn() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                socketToggle = SocketToggle.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        return connection;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socketToggle.quit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(connection);
    }
}
