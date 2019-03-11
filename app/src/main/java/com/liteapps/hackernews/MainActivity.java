package com.liteapps.hackernews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.liteapps.hackernews.adapters.FeedAdapter;
import com.liteapps.hackernews.network.HackerNewsService;
import com.liteapps.hackernews.network.RetrofitInstance;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> mFeedItemIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        final FeedAdapter mFeedAdapter = new FeedAdapter(this, mFeedItemIds);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mFeedAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        HackerNewsService service = RetrofitInstance.getRetrofitInstance().create(HackerNewsService.class);

        Call<ArrayList<Integer>> call = service.getTopStories();
        call.enqueue(new Callback<ArrayList<Integer>>() {
            @Override
            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                mFeedItemIds = response.body();
                mFeedAdapter.updateDataList(mFeedItemIds);
            }

            @Override
            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {

            }
        });
    }

}
