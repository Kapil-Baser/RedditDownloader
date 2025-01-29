package com.redditapp.redditdownloader;

import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.client.UserAgentBuilder;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.RedditPost;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Reddit4JProxy implements RedditAPIProxy {
    private Reddit4J client;
    private final Properties props;

    public Reddit4JProxy(Properties props) {
        this.props = props;
        connect();
    }

    @Override
    public void connect() {
        if (client == null) {
            this.client = Reddit4J.rateLimited().setUsername(props.getProperty("USERNAME"))
                    .setPassword(props.getProperty("PASSWORD"))
                    .setClientId(props.getProperty("CLIENT_ID"))
                    .setClientSecret(props.getProperty("CLIENT_SECRET"))
                    .setUserAgent(new UserAgentBuilder()
                            .appname("RDown")
                            .author("GoGo")
                            .version("1.0"));
            try {
                this.client.connect();
                //System.out.println("Token acquired");
            } catch (IOException | InterruptedException | AuthenticationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getJsonAsString(String redditURL) {
        try {
            return this.client.getRawJson(redditURL, Connection.Method.GET, true);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RedditPost getPost(String postID) {
        try {
            Optional<RedditPost> post = client.getPost(postID);
            if(post.isPresent()) {
                return post.get();
            } else {
                throw new RuntimeException("Reddit post with ID '" + postID + "' not found.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
