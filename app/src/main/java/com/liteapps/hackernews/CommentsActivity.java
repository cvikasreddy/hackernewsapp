package com.liteapps.hackernews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liteapps.hackernews.adapters.CommentsAdapter;
import com.liteapps.hackernews.utils.Constants;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsActivity extends AppCompatActivity {

    private ArrayList<Integer> mCommentItemIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.TITLE);

        mCommentItemIds = (ArrayList<Integer>) intent.getSerializableExtra(Constants.KIDS);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        TextView noComments = findViewById(R.id.no_comments);

        if(mCommentItemIds != null && mCommentItemIds.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            final CommentsAdapter mCommentAdapter = new CommentsAdapter(this, mCommentItemIds);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mCommentAdapter);
            recyclerView.setNestedScrollingEnabled(false);
        } else {
            noComments.setVisibility(View.VISIBLE);
        }

    }

}
