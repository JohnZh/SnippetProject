package com.john.webview.main.command;

import com.google.gson.Gson;
import com.john.jrouter.JRouter;
import com.john.webview.CallbackFromService;
import com.john.webview.main.Command;

/**
 * Created by JohnZh on 2020/7/15
 *
 * <p></p>
 */
public class StartCommand implements Command {

    public static class Path {
        public String path;
    }

    @Override
    public String getName() {
        return "start_activity";
    }

    @Override
    public void execute(String jsonParams, CallbackFromService call) {
        Path path = new Gson().fromJson(jsonParams, Path.class);
        JRouter.get().message(path.path).route();
    }
}
