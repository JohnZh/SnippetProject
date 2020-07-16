package com.john.webview.main.command;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.john.webview.CallbackFromService;
import com.john.webview.WebApp;
import com.john.webview.main.Command;

/**
 * Created by JohnZh on 2020/7/15
 *
 * <p></p>
 */
public class ToastCommand implements Command {
    @Override
    public String getName() {
        return "toast";
    }

    @Override
    public void execute(String jsonParams, CallbackFromService call) {
        Log.d("Temp", "execute: "); // todo remove later
        Handler handler = new Handler(WebApp.getContext().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebApp.getContext(), jsonParams, Toast.LENGTH_LONG).show();
            }
        });
    }
}
