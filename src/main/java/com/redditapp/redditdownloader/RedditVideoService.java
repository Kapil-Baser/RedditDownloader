package com.redditapp.redditdownloader;

import masecla.reddit4j.objects.RedditPost;

import java.util.Map;
import java.util.Properties;

public class RedditVideoService {
    private final RedditAPIProxy client;
    private RedditActualData data;

    public RedditVideoService(Properties props) {
        this.client = new Reddit4JProxy(props);
    }

    private boolean validatePost() {
        return data.isVideo();
    }

    private void getRedditPostData(String jsonString) {
        this.data = RedditPostFetcher.getRedditPost(jsonString);
    }

    public RedditVideoInfo getRedditVideoInfo(String redditURL) {
        String url = Utils.trimURL(redditURL);
        String jsonString = client.getJsonAsString(url);
        getRedditPostData(jsonString);
        // Checking if post has any video at all
        if (validatePost()) {
            RedditPost redditPost = client.getPost(data.getName());
            Map<String, Object> redditVideoMap = RedditMediaInfoExtractor.extractRedditVideoMap(redditPost);
            return new RedditVideoInfo(
                    (Double) redditVideoMap.get("bitrate_kbps"),
                    (String) redditVideoMap.get("fallback_url"),
                    (Boolean) redditVideoMap.get("has_audio"),
                    (Double) redditVideoMap.get("height"),
                    (Double) redditVideoMap.get("width"),
                    (Double) redditVideoMap.get("duration"),
                    (String) redditVideoMap.get("dash_url"),
                    (String) redditVideoMap.get("hls_url"),
                    (boolean) redditVideoMap.get("is_gif")
            );
        } else {
            throw new RuntimeException("Given URL doesn't have a video");
        }
    }
}
