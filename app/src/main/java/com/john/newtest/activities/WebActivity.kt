package com.john.newtest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.john.common.utils.ToastUtil
import com.john.jrouter.annotation.Route
import com.john.newtest.R
import kotlinx.android.synthetic.main.activity_web.*

@Route("/web/webpage")
class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        var url = intent.getStringExtra("url")

        //val url = "https://www.mcdonalds.co.jp/media_library/6523/file.pdf"

        ToastUtil.show(url)

        val finalUrl = "https://docs.google.com/gview?embedded=true&url=$url"

        text.text = finalUrl

        webView.loadUrl(finalUrl)
    }
}