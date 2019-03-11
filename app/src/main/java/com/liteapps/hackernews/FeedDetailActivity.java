package com.liteapps.hackernews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liteapps.hackernews.adapters.FeedAdapter;
import com.liteapps.hackernews.models.FeedItem;
import com.liteapps.hackernews.network.HackerNewsService;
import com.liteapps.hackernews.network.RetrofitInstance;
import com.liteapps.hackernews.utils.Constants;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedDetailActivity extends AppCompatActivity {

    private FeedItem feedItem;
    private ProgressBar mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra(Constants.ID, 0);
        String title = intent.getStringExtra(Constants.TITLE);
        String url = intent.getStringExtra(Constants.URL);
        String type = intent.getStringExtra(Constants.TYPE);
        String time = intent.getStringExtra(Constants.TIME);
        String score = intent.getStringExtra(Constants.SCORE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView itemTitleTextView = findViewById(R.id.title);
        itemTitleTextView.setText(title);

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

        if(type.equals(Constants.STORY)) {
            mWebview.loadUrl(url);
        }
    }

}
