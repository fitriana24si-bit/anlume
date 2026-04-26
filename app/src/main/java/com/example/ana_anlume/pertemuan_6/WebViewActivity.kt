package com.example.ana_anlume.pertemuan_6

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ana_anlume.R

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // =========================
        // TOOLBAR
        // =========================
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Web Bina Desa"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // =========================
        // WEBVIEW
        // =========================
        val webView = findViewById<WebView>(R.id.webView)

        // Biar tidak buka browser luar
        webView.webViewClient = WebViewClient()

        // Untuk handle JavaScript & UI web
        webView.webChromeClient = WebChromeClient()

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadsImagesAutomatically = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        // 🔥 Penting untuk beberapa website
        settings.userAgentString =
            "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 Chrome/90.0 Safari/537.36"

        // 🔥 Load Web kamu
        webView.loadUrl("https://ana-sic.alwaysdata.net/")
    }

    // Tombol back toolbar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}