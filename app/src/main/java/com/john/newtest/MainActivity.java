package com.john.newtest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.john.newtest.aidl.People;
import com.john.newtest.aidl.PeopleAidl;
import com.john.newtest.databinding.ActivityMainBinding;
import com.john.newtest.service.RemoteService;

import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RemoteServiceClient";

    private PeopleAidl mPeopleAidl;

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        bindService();

        mBinding.getPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPeopleAidl != null) {
                    try {
                        List<People> list = mPeopleAidl.getList();
                        if (list.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Empty list, add People", Toast.LENGTH_SHORT).show();
                            int num = new Random().nextInt(100);
                            People people = new People("John " + num, num);
                            mPeopleAidl.addPerson(people);
                        } else {
                            People people = list.get(list.size() - 1);
                            String string = mBinding.output.getText().toString();
                            string += "\n" + people.toString();
                            mBinding.output.setText(string);
                            list.clear();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mPeopleAidl = PeopleAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            mPeopleAidl = null;
        }
    };

    private void bindService() {
        Intent intent = new Intent(this, RemoteService.class);
        //intent.setComponent(new ComponentName("com.john.newtest.service", "com.john.newtest.service.RemoteService"));
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}
