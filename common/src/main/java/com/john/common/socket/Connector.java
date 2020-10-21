package com.john.common.socket;

/**
 * Created by JohnZh on 2020/10/13
 *
 * <p></p>
 */
public interface Connector {
    void connect();

    void shutdown();

    boolean isConnected();

    void send(String s);

    void setCallback(Callback callback);
}
