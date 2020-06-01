package com.john.newtest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.john.bindlib.BindLib;
import com.john.bindlib.annotation.BindView;
import com.john.newtest.service.RemoteService;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.textView)
    Button mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BindLib.bind(this);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(Main2Activity.this, RemoteService.class), mConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("Temp", "onServiceConnected: " ); // todo remove later
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
