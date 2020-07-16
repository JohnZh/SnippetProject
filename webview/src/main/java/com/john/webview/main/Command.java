package com.john.webview.main;

import com.john.webview.CallbackFromService;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public interface Command {

    int SUCCESS = 200;
    int FAILURE = 500;

    String getName();

    void execute(String jsonParams, CallbackFromService call);
}
