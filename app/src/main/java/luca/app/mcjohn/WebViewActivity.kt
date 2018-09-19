package luca.app.mcjohn

import android.webkit.WebViewClient
import android.webkit.WebView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val websiteAddress = "website_address"

        val url = intent.getStringExtra(websiteAddress)
        if (url == null || url.isEmpty()) finish()

        setContentView(R.layout.internal_browser)
        val webView: WebView = findViewById(R.id.webview)
        //webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
}