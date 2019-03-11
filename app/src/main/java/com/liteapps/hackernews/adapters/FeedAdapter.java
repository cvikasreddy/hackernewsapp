package com.liteapps.hackernews.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liteapps.hackernews.CommentsActivity;
import com.liteapps.hackernews.FeedDetailActivity;
import com.liteapps.hackernews.R;
import com.liteapps.hackernews.models.Item;
import com.liteapps.hackernews.network.HackerNewsService;
import com.liteapps.hackernews.network.RetrofitInstance;
import com.liteapps.hackernews.utils.Constants;

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

        Call<Item> call = service.getStoryDetails(String.valueOf(mFeedIds.get(position)));
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                final Item item = response.body();

                if(item != null) {
                    holder.feedItemTitle.setText(String.valueOf(item.getTitle()));
                    holder.feedItemScore.setText(String.format(mContext.getString(R.string.points), ""+ item.getScore()));
                    holder.feedItemTime.setText((new java.util.Date((long) item.getTime()*1000)).toString().split("GMT")[0]);

                    if(item.getKids() != null) {
                        holder.feedItemCommentsCount.setText(String.format(mContext.getString(R.string.comments), "" + item.getKids().size()));

                        holder.feedItemCommentsCount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext, CommentsActivity.class);
                                intent.putExtra(Constants.ID, item.getId());
                                intent.putExtra(Constants.TITLE, item.getTitle());
                                intent.putExtra(Constants.KIDS, item.getKids());
                                mContext.startActivity(intent);
                            }
                        });
                    }

                    holder.feedItemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent;
                            if(item.getType().equals(Constants.STORY)) {
                                intent = new Intent(mContext, FeedDetailActivity.class);
                            } else {
                                intent = new Intent(mContext, CommentsActivity.class);
                            }

                            intent.putExtra(Constants.ID, item.getId());
                            intent.putExtra(Constants.TITLE, item.getTitle());
                            intent.putExtra(Constants.URL, item.getUrl());
                            intent.putExtra(Constants.TYPE, item.getType());
                            intent.putExtra(Constants.TIME, item.getTime());
                            intent.putExtra(Constants.SCORE, item.getScore());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

    }

    private class VHFeedItem extends RecyclerView.ViewHolder {

        private RelativeLayout feedItemLayout;
        private TextView feedItemTitle;
        private TextView feedItemScore;
        private TextView feedItemTime;
        private TextView feedItemCommentsCount;

        private VHFeedItem(View feedView) {
            super(feedView);

            feedItemLayout = feedView.findViewById(R.id.item_layout);
            feedItemTitle = feedView.findViewById(R.id.item_title);
            feedItemScore = feedView.findViewById(R.id.item_score);
            feedItemTime = feedView.findViewById(R.id.item_time);
            feedItemCommentsCount = feedView.findViewById(R.id.item_comments_count);
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
