package com.redditapp.redditdownloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RedditPostFetcher {
    public static RedditActualData getRedditPost(String jsonString) {
        // Find out the correct type of returned Json and then make POJO out of that
        Type listType = new TypeToken<List<RedditListing>>() {}.getType();
        List<RedditListing> lists = new Gson().fromJson(jsonString, listType);
        return lists.getFirst().getData().getChildren().getFirst().getData();
    }
}
