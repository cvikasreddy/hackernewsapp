package com.liteapps.hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liteapps.hackernews.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;

public class FeedDetailActivity extends AppCompatActivity {

    private ProgressBar mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.TITLE);
        String url = intent.getStringExtra(Constants.URL);
        String type = intent.getStringExtra(Constants.TYPE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        if(type.equals(Constants.STORY)) {
            WebView mWebview = findViewById(R.id.web_view);
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.getSettings().setAppCacheEnabled(false);
            mWebview.getSettings().setBuiltInZoomControls(true);
            mWebview.getSettings().setDisplayZoomControls(false);

            mLoader = findViewById(R.id.loader);

            mWebview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    mLoader.setProgress(progress);
                    if (progress == 100) {
                        mLoader.setVisibility(View.GONE);
                    } else {
                        mLoader.setVisibility(View.VISIBLE);
                    }
                }
            });

            mWebview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });

            mWebview.loadUrl(url);
        }
    }

}
