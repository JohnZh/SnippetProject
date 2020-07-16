package com.john.webview.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.john.webview.main.command.LoginCommand;
import com.john.webview.main.command.StartCommand;
import com.john.webview.main.command.ToastCommand;

import androidx.annotation.Nullable;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public class HandleWebService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        CommandManager.get().registerCmd(new ToastCommand());
        CommandManager.get().registerCmd(new StartCommand());
        CommandManager.get().registerCmd(new LoginCommand());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Temp", "onBind: "); // todo remove later
        return CommandManager.get();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("Temp", "onUnbind: "); // todo remove later
        return super.onUnbind(intent);
    }
}
