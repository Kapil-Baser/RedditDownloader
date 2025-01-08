package com.redditapp.redditdownloader;

public record RedditVideoInfo(
        int bitrate,
        String fallbackURL,
        boolean hasAudio,
        int height,
        int width,
        int duration,
        String dashURL,
        String hlsURL,
        boolean isGif
) { }
