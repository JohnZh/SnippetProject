package com.john.webview.main;

import com.john.webview.CallbackFromService;
import com.john.webview.RemoteServiceProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JohnZh on 2020/7/14
 *
 * <p></p>
 */
public class CommandManager extends RemoteServiceProxy.Stub {

    private static CommandManager sCommandManager;
    private Map<String, Command> commandMap;

    public CommandManager() {
        this.commandMap = new HashMap<>();
    }

    public static CommandManager get() {
        if (sCommandManager == null) {
            sCommandManager = new CommandManager();
        }
        return sCommandManager;
    }

    public void registerCmd(Command command) {
        commandMap.put(command.getName(), command);
    }

    @Override
    public void onDispatch(String cmd, String jsonParams, CallbackFromService call) {
        Command command = commandMap.get(cmd);
        if (command != null) {
            command.execute(jsonParams, call);
        }
    }
}
