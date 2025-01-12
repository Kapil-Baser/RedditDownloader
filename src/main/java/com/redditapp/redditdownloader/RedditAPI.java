package com.redditapp.redditdownloader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.client.UserAgentBuilder;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditPost;
import org.jsoup.Connection;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class RedditAPI {
    private Reddit4J client;
    private RedditPost redditPost;
    private Map<String, Object> redditVideoMap;

    public RedditAPI(Properties props) {
        authenticateToReddit(props);
    }

    private void authenticateToReddit(Properties props) {
        this.client = Reddit4J.rateLimited().setUsername(props.getProperty("USERNAME"))
                .setPassword(props.getProperty("PASSWORD"))
                .setClientId(props.getProperty("CLIENT_ID"))
                .setClientSecret(props.getProperty("CLIENT_SECRET"))
                .setUserAgent(new UserAgentBuilder()
                        .appname("RDown")
                        .author("GoGo")
                        .version("1.0"));
        // If successful then we acquire the token.
        try {
            this.client.connect();
            //System.out.println("Authenticated successfully, token acquired");
        } catch (IOException | InterruptedException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    private String trimURL(String URL) {
        return String.join("", URL.split("https://www.reddit.com"));
    }

    private String getJsonAsString(String redditURL) {
        try {
            return this.client.getRawJson(redditURL, Connection.Method.GET, true);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRedditPostID(String jsonString) {
        // Find out the correct type of returned Json and then make POJO out of that
        Type listType = new TypeToken<List<RedditListing>>() {}.getType();
        List<RedditListing> lists = new Gson().fromJson(jsonString, listType);
        return lists.getFirst().getData().getChildren().getFirst().getData().getName();
    }

    private void getRedditPostData(String postID) {
        try {
            Optional<RedditPost> post = client.getPost(postID);
            post.ifPresent(value -> this.redditPost = value);       // First time using lambda expression, it's cool but also a bit intimidating.
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMediaInfoFromRedditPost() {
        try {
            Object mediaObject = this.redditPost.getMedia();
            if (!(mediaObject instanceof Map)) {
                throw new ClassCastException("Media object is not a Map");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> mediaMap = (Map<String, Object>) mediaObject;

            Object redditVideoObject = mediaMap.get("reddit_video");
            if (!(redditVideoObject instanceof Map)) {
                throw new ClassCastException("Reddit video object is not a Map");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> redditVideoMap = (Map<String, Object>) redditVideoObject;
            this.redditVideoMap = redditVideoMap;

        } catch (ClassCastException e) {
            System.err.println("Error casting media object: " + e.getMessage());
        }
    }

    public RedditVideoInfo fetchVideoInfo(String URL) {
        String redditURL = trimURL(URL);
        String jsonString = getJsonAsString(redditURL);

        // Extracting the reddit post ID
        String postID = getRedditPostID(jsonString);

        // Extracting the reddit post itself with all the required information
        getRedditPostData(postID);

        getMediaInfoFromRedditPost();

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
    }
}
