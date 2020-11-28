package com.yura.tutbyrssreader.web_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yura.tutbyrssreader.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String url;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getData().toString();

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                url = request.getUrl().toString();
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.webViewBack) webView.goBack();
        if(item.getItemId() == R.id.webViewForward) webView.goForward();
        if(item.getItemId() == R.id.webViewRefresh) webView.reload();
        if(item.getItemId() == R.id.webViewStop) webView.stopLoading();
        if(item.getItemId() == R.id.webViewLaunch) startActivity(
                new Intent(Intent.ACTION_VIEW, Uri.parse(url))
        );

        return true;
    }
}