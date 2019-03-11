package com.liteapps.hackernews.network;

import com.liteapps.hackernews.models.CommentItem;
import com.liteapps.hackernews.models.Item;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsService {

    @GET("topstories.json")
    Call<ArrayList<Integer>> getTopStories();

    @GET("item/{itemId}.json")
    Call<Item> getStoryDetails(@Path("itemId") String itemId);

    @GET("item/{itemId}.json")
    Call<CommentItem> getComments(@Path("itemId") String itemId);

}
