package com.john.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.john.webview.client.SecureWebChromeClient;
import com.john.webview.client.WebViewClient;
import com.john.webview.jscode.JsCode;
import com.john.webview.jscode.JsInterface;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created by JohnZh on 2020/7/12
 *
 * <p></p>
 */
public class SecureWebView extends WebView {

    private SecureWebChromeClient webChromeClient;
    private WebViewClient webViewClient;

    private Map<String, Object> jsInterfaceMap;

    public SecureWebView(Context context) {
        super(context);
        init();
    }

    public SecureWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initWebSettings(getSettings());

        setWebChromeClient(webChromeClient = new SecureWebChromeClient());
        setWebViewClient(webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (webChromeClient != null) {
                    String injectJsCode = JsCode.getInjectJsCode(jsInterfaceMap);
                    webChromeClient.setJsCode(injectJsCode);
                    webChromeClient.setInjectJsCode(true);
                }
            }
        });

        jsInterfaceMap = new HashMap<>();
        addJavascriptInterface(this, "webview");

        // fix secure issue
        removeRiskyJavaInterface();

        // download
        setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(intent);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterface(Object object, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // >= 4.2
            super.addJavascriptInterface(object, name);
        } else {
            jsInterfaceMap.put(name, object);
        }
    }

    @Override
    public void removeJavascriptInterface(@NonNull String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // >= 4.2
            super.removeJavascriptInterface(name);
        } else {
            jsInterfaceMap.remove(name);
        }
    }

    private void removeRiskyJavaInterface() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // >= 3.0 & < 4.4
            removeJavascriptInterface("searchBoxJavaBridge_");
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        }
    }

    private void initWebSettings(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSavePassword(false);
        //settings.setUserAgentString("AND_VER/1.0");
        settings.setAllowFileAccess(true);
        // performance improve
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setEnableSmoothTransition(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    public void setWebCallback(WebCallback webCallback) {
        webChromeClient.setWebCallback(webCallback);
        webViewClient.setWebCallback(webCallback);
    }

    public Map<String, Object> getJsInterfaceMap() {
        return jsInterfaceMap;
    }

    @JavascriptInterface
    @JsInterface
    public void post(String cmd, String jsonParams) {
        CommandDispatcher.get().dispatch(cmd, jsonParams);
    }
}
