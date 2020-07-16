package com.john.webview;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public interface WebCallback {

    void onReceiveTitle(String title);

    void onProgressChanged(int newProgress);

    void onPageStarted(String url);

    void onPageFinished(String url);
}
