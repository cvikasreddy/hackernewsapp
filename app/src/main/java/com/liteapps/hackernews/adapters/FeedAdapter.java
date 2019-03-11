package com.liteapps.hackernews.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liteapps.hackernews.FeedDetailActivity;
import com.liteapps.hackernews.R;
import com.liteapps.hackernews.models.FeedItem;
import com.liteapps.hackernews.network.HackerNewsService;
import com.liteapps.hackernews.network.RetrofitInstance;
import com.liteapps.hackernews.utils.Constants;
import com.liteapps.hackernews.utils.TimeAgo;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Integer> mFeedIds;

    private HackerNewsService service;

    public FeedAdapter(Context context, ArrayList<Integer> feedIds) {
        mContext = context;
        mFeedIds = feedIds;
        service = RetrofitInstance.getRetrofitInstance().create(HackerNewsService.class);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new VHFeedItem(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vHolder, int position) {
        final VHFeedItem holder = (VHFeedItem) vHolder;

        holder.feedItemTitle.setText(mContext.getString(R.string.loading));
        holder.feedItemScore.setText("");
        holder.feedItemTime.setText("");
        holder.feedItemLayout.setOnClickListener(null);

        Call<FeedItem> call = service.getStoryDetails(String.valueOf(mFeedIds.get(position)));
        call.enqueue(new Callback<FeedItem>() {
            @Override
            public void onResponse(Call<FeedItem> call, Response<FeedItem> response) {
                final FeedItem feedItem = response.body();

                if(feedItem != null) {
                    holder.feedItemTitle.setText(String.valueOf(feedItem.getTitle()));
                    holder.feedItemScore.setText(String.format(mContext.getString(R.string.points), ""+feedItem.getScore()));
                    holder.feedItemTime.setText(TimeAgo.toDuration(feedItem.getTime()));

                    holder.feedItemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, FeedDetailActivity.class);
                            intent.putExtra(Constants.ID, feedItem.getId());
                            intent.putExtra(Constants.TITLE, feedItem.getTitle());
                            intent.putExtra(Constants.URL, feedItem.getUrl());
                            intent.putExtra(Constants.TYPE, feedItem.getType());
                            intent.putExtra(Constants.TIME, feedItem.getTime());
                            intent.putExtra(Constants.SCORE, feedItem.getScore());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FeedItem> call, Throwable t) {

            }
        });

    }

    private class VHFeedItem extends RecyclerView.ViewHolder {

        private RelativeLayout feedItemLayout;
        private TextView feedItemTitle;
        private TextView feedItemScore;
        private TextView feedItemTime;

        private VHFeedItem(View feedView) {
            super(feedView);

            feedItemLayout = feedView.findViewById(R.id.item_layout);
            feedItemTitle = feedView.findViewById(R.id.item_title);
            feedItemScore = feedView.findViewById(R.id.item_score);
            feedItemTime = feedView.findViewById(R.id.item_time);
        }
    }

    @Override
    public int getItemCount() {
        return mFeedIds.size();
    }

    public void updateDataList(ArrayList<Integer> feedIds) {
        mFeedIds.addAll(feedIds);
        notifyDataSetChanged();
    }


}
