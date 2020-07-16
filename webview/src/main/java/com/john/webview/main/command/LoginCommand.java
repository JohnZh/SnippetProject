package com.john.webview.main.command;

import android.os.RemoteException;

import com.john.webview.CallbackFromService;
import com.john.webview.main.Command;

/**
 * Created by JohnZh on 2020/7/15
 *
 * <p></p>
 */
public class LoginCommand implements Command {
    @Override
    public String getName() {
        return "login";
    }

    @Override
    public void execute(String jsonParams, CallbackFromService call) {
        //JRouter.get().message("/mine/login").route();
        // get data from static map or vars
        try {
            call.onResponse(SUCCESS, getName(), "{phone:13567124531, name:\"Johnzhang\"}");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
