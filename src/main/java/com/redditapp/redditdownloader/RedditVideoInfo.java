package com.redditapp.redditdownloader;

public record RedditVideoInfo(
        double bitrate,
        String fallbackURL,
        boolean hasAudio,
        double height,
        double width,
        double duration,
        String dashURL,
        String hlsURL,
        boolean isGif
) { }
