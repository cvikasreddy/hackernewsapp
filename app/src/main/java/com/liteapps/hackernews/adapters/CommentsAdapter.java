package com.liteapps.hackernews.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liteapps.hackernews.R;
import com.liteapps.hackernews.models.CommentItem;
import com.liteapps.hackernews.network.HackerNewsService;
import com.liteapps.hackernews.network.RetrofitInstance;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Integer> mCommentIds;

    private HackerNewsService service;

    public CommentsAdapter(Context context, ArrayList<Integer> commentIds) {
        mContext = context;
        mCommentIds = commentIds;
        service = RetrofitInstance.getRetrofitInstance().create(HackerNewsService.class);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new VHCommentItem(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vHolder, int position) {
        final VHCommentItem holder = (VHCommentItem) vHolder;

        holder.commentText.setText(mContext.getString(R.string.loading));
        holder.commentTime.setText("");

        Log.d("commentIds", ""+mCommentIds.get(position));

        Call<CommentItem> call = service.getComments(String.valueOf(mCommentIds.get(position)));
        call.enqueue(new Callback<CommentItem>() {
            @Override
            public void onResponse(Call<CommentItem> call, Response<CommentItem> response) {
                final CommentItem item = response.body();
                if(item != null && item.getText() != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holder.commentText.setText(Html.fromHtml(item.getText(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holder.commentText.setText(Html.fromHtml(item.getText()));
                    }

                    holder.commentTime.setText((new java.util.Date((long) item.getTime()*1000)).toString().split("GMT")[0]);
                }
            }

            @Override
            public void onFailure(Call<CommentItem> call, Throwable t) {

            }
        });

    }

    private class VHCommentItem extends RecyclerView.ViewHolder {

        private TextView commentText;
        private TextView commentTime;

        private VHCommentItem(View commentView) {
            super(commentView);

            commentText = commentView.findViewById(R.id.item_title);
            commentTime = commentView.findViewById(R.id.item_time);
        }
    }

    @Override
    public int getItemCount() {
        return mCommentIds.size();
    }

    public void updateDataList(ArrayList<Integer> commentIds) {
        mCommentIds.addAll(commentIds);
        notifyDataSetChanged();
    }


}
