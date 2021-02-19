package com.john.webview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.john.jrouter.annotation.Route;
import com.john.webview.databinding.ActivityWebViewBinding;

import androidx.appcompat.app.AppCompatActivity;


@Route("/web/webview")
public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        processIntent(getIntent());

        WebViewFragment webViewFragment = (WebViewFragment) getSupportFragmentManager()
                .findFragmentById(R.id.webViewFragment);

        SecureWebView secureWebView = webViewFragment.getSecureWebView();
        secureWebView.setWebCallback(new WebCallback() {
            @Override
            public void onReceiveTitle(String title) {
                binding.title.setText(title);
            }

            @Override
            public void onProgressChanged(int newProgress) {
                binding.progressBar.setProgress(newProgress);
            }

            @Override
            public void onPageStarted(String url) {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(String url) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        if (TextUtils.isEmpty(this.url)) {
            secureWebView.loadUrl("file:///android_asset/page/test.html");
        } else {
            secureWebView.loadUrl(this.url);
        }
    }

    private void processIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            this.url = url;
            Log.d("TEMP", "processIntent: " + url);
        }
    }
}
