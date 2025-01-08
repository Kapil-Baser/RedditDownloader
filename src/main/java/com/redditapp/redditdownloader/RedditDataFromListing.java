package com.redditapp.redditdownloader;

import java.util.List;

public class RedditDataFromListing {
    private Object after;
    private int dist;
    private Object modhash;
    private List<RedditChildData> children;
    private Object before;


    public int getDist() {
        return dist;
    }

    public Object getAfter() {
        return after;
    }

    public Object getBefore() {
        return before;
    }

    public List<RedditChildData> getChildren() {
        return children;
    }

    public Object getModhash() {
        return modhash;
    }
}
