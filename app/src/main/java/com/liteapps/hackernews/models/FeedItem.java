package com.liteapps.hackernews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedItem implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("score")
    private Integer score;

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private String type;

    @SerializedName("time")
    private Integer time;

    public FeedItem(Integer id, String title, Integer score, String url, String type, Integer time) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.url = url;
        this.type = type;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

}
