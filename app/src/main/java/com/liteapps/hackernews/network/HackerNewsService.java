package com.liteapps.hackernews.network;

import com.liteapps.hackernews.models.FeedItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsService {

    @GET("topstories.json")
    Call<ArrayList<Integer>> getTopStories();

    @GET("item/{itemId}.json")
    Call<FeedItem> getStoryDetails(@Path("itemId") String itemId);

}
