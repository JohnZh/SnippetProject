package com.john.webview;

import android.os.Bundle;
import android.view.View;

import com.john.jrouter.annotation.Route;
import com.john.webview.databinding.ActivityWebViewBinding;

import androidx.appcompat.app.AppCompatActivity;


@Route("/web/webview")
public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        secureWebView.loadUrl("file:///android_asset/page/test.html");
    }
}
