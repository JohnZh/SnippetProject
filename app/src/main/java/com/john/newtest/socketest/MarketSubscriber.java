package com.john.newtest.socketest;

import com.google.gson.Gson;
import com.john.common.socket.SocketConnector;

import java.lang.reflect.Type;

/**
 * Created by JohnZh on 2020/10/14
 *
 * <p></p>
 */
public class MarketSubscriber {
    public static final int HEART = 200;
    public static final int SERVER_HEART = 201;

    public static final int SUB = 101;
    public static final int UNSUB = 102;

    public static final int SUB_ALL = 103;
    public static final int UNSUB_ALL = 104;

    private SocketConnector connector;
    private Callback callback;
    private Gson gson;

    public MarketSubscriber() {
        connector = new SocketConnector();
        connector.setCallback(s -> {
            if (callback != null) {
                Object o = convertToObj(s, callback.getGenericType());
                callback.onCallback(o);
            }
        });
        gson = new Gson();
    }

    private Object convertToObj(String s, Type genericType) {
        return gson.fromJson(s, genericType);
    }

    public static volatile MarketSubscriber sSubscriber;

    public static MarketSubscriber get() {
        if (sSubscriber == null) {
            synchronized (MarketSubscriber.class) {
                if (sSubscriber == null) {
                    sSubscriber = new MarketSubscriber();
                }
            }
        }
        return sSubscriber;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void subscribe(String pcode) {
        Req req = new Req(SUB);
        req.setData(pcode);
        connector.send(req.toJSON());
    }

    public void subscribeAll() {

    }

    public void unsubscribeAll() {

    }

    public void unsubscribe(String pcode) {
        Req req = new Req(UNSUB);
        req.setData(pcode);
        connector.send(req.toJSON());
    }

    public void close() {
        connector.shutdown();
    }
}
