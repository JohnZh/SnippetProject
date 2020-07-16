package com.john.webview.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.john.webview.UrlLoadingListener;
import com.john.webview.WebCallback;

import androidx.annotation.NonNull;
import androidx.webkit.WebResourceErrorCompat;
import androidx.webkit.WebViewClientCompat;

/**
 * Created by JohnZh on 2020/7/13
 *
 * <p>处理重定向，拦截，安全问题，页面加载</p>
 */
public class WebViewClient extends WebViewClientCompat {

    private static final String TEL = "tel:";
    private static final String SMS = "sms:";
    private static final String MAILTO = "mailto:";

    private WebCallback webCallback;
    private UrlLoadingListener loadingListener;

    public void setWebCallback(WebCallback webCallback) {
        this.webCallback = webCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (handleRequest(view, request.getUrl().toString())) {
                return true;
            }
            if (loadingListener != null && loadingListener.processMyself()) {
                loadingListener.onOverrideUrlLoading(view, request.getUrl().toString());
                return true;
            }
        }

        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (handleRequest(view, url)) {
            return true;
        }
        if (loadingListener != null && loadingListener.processMyself()) {
            loadingListener.onOverrideUrlLoading(view, url);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    private boolean handleRequest(WebView view, String url) {
        if (url.startsWith(TEL) || url.startsWith(MAILTO) || url.startsWith(SMS)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                view.getContext().startActivity(intent);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (webCallback != null) {
            webCallback.onPageStarted(url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (webCallback != null) {
            webCallback.onPageFinished(url);
        }
    }

    @Override
    public void onReceivedError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceErrorCompat error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }
}
