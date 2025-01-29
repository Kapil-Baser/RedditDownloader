package com.redditapp.redditdownloader;

import masecla.reddit4j.objects.RedditPost;

public interface RedditAPIProxy {
    void connect();
    String getJsonAsString(String url);
    RedditPost getPost(String postID);
}
