package com.liteapps.hackernews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Item implements Serializable {

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

    @SerializedName("kids")
    private ArrayList<Integer> kids;

    public Item(Integer id, String title, Integer score, String url, String type, Integer time, ArrayList<Integer> kids) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.url = url;
        this.type = type;
        this.time = time;
        this.kids = kids;
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

    public ArrayList<Integer> getKids() {
        return kids;
    }

    @NonNull
    @Override
    public String toString() {
        return "" + id;
    }
}
