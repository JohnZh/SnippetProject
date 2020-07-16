package com.john.webview.client;

import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.john.webview.SecureWebView;
import com.john.webview.WebCallback;
import com.john.webview.jscode.JsCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p>自定义 WebChromeClient 定义 JS Alert, 文件打开（And5.0）title progress 回调</p>
 */
public class SecureWebChromeClient extends WebChromeClient {

    private static final int LOAD_JS_THRESHOLD = 80;
    private boolean injectJsCode;
    private String jsCode;
    private WebCallback webCallback;

    public void setWebCallback(WebCallback webCallback) {
        this.webCallback = webCallback;
    }

    public void setInjectJsCode(boolean injectJsCode) {
        this.injectJsCode = injectJsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (view instanceof SecureWebView && message.startsWith(JsCode.SCHEME)) {
            if (processMessageByReflection(message, ((SecureWebView) view).getJsInterfaceMap())) {
                result.confirm();
            } else {
                result.cancel();
            }
            return true;
        }
        return false;
    }

    static class MessageObj {
        String object;
        String method;
        String[] args;
    }

    private boolean processMessageByReflection(String message, Map<String, Object> jsInterfaceMap) {
        String objStr = message.substring(JsCode.SCHEME.length());
        MessageObj messageObj = new Gson().fromJson(objStr, MessageObj.class);
        Class[] parameterTypes = new Class[messageObj.args.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = String.class;
        }
        Object jsInterfaceObj = jsInterfaceMap.get(messageObj.object);
        Class clazz = jsInterfaceObj.getClass();
        try {
            Method method = clazz.getMethod(messageObj.method, parameterTypes);
            method.invoke(jsInterfaceObj, messageObj.args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (webCallback != null) {
            webCallback.onReceiveTitle(title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (injectJsCode && newProgress >= LOAD_JS_THRESHOLD && !TextUtils.isEmpty(jsCode)) {
            view.loadUrl(jsCode);
            injectJsCode = false;
        }

        if (webCallback != null) {
            if (newProgress < 10) {
                newProgress = 10;
            }
            webCallback.onProgressChanged(newProgress);
        }
    }
}
