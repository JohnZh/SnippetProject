package com.john.webview;

import android.os.RemoteException;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public class CommandDispatcher {

    public interface ResponseListener {
        void onResponse(int code, String cmd, String response);
    }

    private static CommandDispatcher executor;

    private RemoteServiceProxy remoteServiceProxy;
    private ResponseListener responseListener;

    public static CommandDispatcher get() {
        if (executor == null) {
            executor = new CommandDispatcher();
        }
        return executor;
    }

    public void setRemoteServiceProxy(RemoteServiceProxy remoteServiceProxy) {
        this.remoteServiceProxy = remoteServiceProxy;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void dispatch(String cmd, String jsonParams) {
        if (remoteServiceProxy != null) {
            try {
                remoteServiceProxy.onDispatch(cmd, jsonParams, new CallbackFromService.Stub() {
                    @Override
                    public void onResponse(int code, String cmd, String response) {
                        if (responseListener != null) {
                            responseListener.onResponse(code, cmd, response);
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
