package com.example.nationalparkliveviewerapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)

        // 获取从上一页面传递的 URL
        val url = intent.getStringExtra("URL")

        // WebView 设置
        webView.settings.javaScriptEnabled = true // allow JavaScript
        webView.settings.domStorageEnabled = true // 启用 DOM 存储
        webView.settings.loadsImagesAutomatically = true // 自动加载图片
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE // 不使用缓存

        // 防止跳转到外部浏览器
        webView.webViewClient = WebViewClient()

        // 加载网页
        url?.let {
            webView.loadUrl(it)
        }
    }
}