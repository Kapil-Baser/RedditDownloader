package com.redditapp.redditdownloader;

import masecla.reddit4j.objects.RedditPost;

import java.util.Map;

public class RedditMediaInfoExtractor {
    public static Map<String, Object> extractRedditVideoMap(RedditPost redditPost) {
        try {
            Object redditVideoObject = getObject(redditPost);

            @SuppressWarnings("unchecked")
            Map<String, Object> redditVideoMap = (Map<String, Object>) redditVideoObject;
            return redditVideoMap;

        } catch (ClassCastException e) {
            throw new RuntimeException("Error casting media object: " + e.getMessage());
        }
    }

    private static Object getObject(RedditPost redditPost) {
        Object mediaObject = redditPost.getMedia();
        if (!(mediaObject instanceof Map)) {
            throw new ClassCastException("Media object is not a Map");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> mediaMap = (Map<String, Object>) mediaObject;

        Object redditVideoObject = mediaMap.get("reddit_video");
        if (!(redditVideoObject instanceof Map)) {
            throw new ClassCastException("Reddit video object is not a Map");
        }
        return redditVideoObject;
    }
}
