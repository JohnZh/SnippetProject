package com.john.webview;

import android.webkit.WebView;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p></p>
 */
public interface UrlLoadingListener {

    boolean processMyself();

    void onOverrideUrlLoading(WebView view, String url);
}
