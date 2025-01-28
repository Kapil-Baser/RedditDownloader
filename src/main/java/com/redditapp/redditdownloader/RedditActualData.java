package com.redditapp.redditdownloader;

public class RedditActualData {
    private String subreddit;
    private String name;
    private String author_fullname;
    private String subreddit_id;
    private String author;
    private boolean is_self;
    private String url;
    private boolean is_video;
    private Object secure_media;

    public String getSubreddit() {
        return subreddit;
    }

    public String getName() {
        return name;
    }

    public String getAuthorFullName() {
        return author_fullname;
    }

    public String getSubredditID() {
        return subreddit_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public boolean isSelf() {
        return is_self;
    }

    public boolean isVideo() {
        return is_video;
    }
}
