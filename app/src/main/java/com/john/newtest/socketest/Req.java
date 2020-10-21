package com.john.newtest.socketest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JohnZh on 2020/10/13
 *
 * <p></p>
 */
public class Req {

    private int code;
    private String data;

    public Req(int code) {
        this.code = code;
    }

    public Req(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("code", code);
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private static JSONObject convertJSONObject(String s) {
        JSONObject object = null;
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Req getReq(String json) {
        JSONObject object = convertJSONObject(json);
        if (object != null) {
            int code = object.optInt("code");
            if (code != 0) {
                String data = object.optString("data");
                return new Req(code, data);
            }
        }
        return null;
    }
}
