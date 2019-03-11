package com.liteapps.hackernews.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CommentItem implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("text")
    private String text;

    @SerializedName("time")
    private Integer time;

    @SerializedName("type")
    private String type;

    @SerializedName("kids")
    private ArrayList<Integer> kids;

    public CommentItem(Integer id, String text, String type, Integer time, ArrayList<Integer> kids) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.time = time;
        this.kids = kids;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getTime() {
        return time;
    }

    public String getType() {
        return type;
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
