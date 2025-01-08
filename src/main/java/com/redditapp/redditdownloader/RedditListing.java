package com.redditapp.redditdownloader;

import com.google.gson.annotations.SerializedName;

public class RedditListing {
    @SerializedName("kind")
    public String kind;
    private RedditDataFromListing data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RedditDataFromListing getData() {
        return data;
    }
}
