package com.redditapp.redditdownloader;

public class RedditInteractor {
    private RedditModel model;
    private RedditAPI redditAPI;

    public RedditInteractor(RedditModel model) {
        this.model = model;
        this.redditAPI = new RedditAPI(ConfigLoader.getProperties());
    }

    public void start() {
        RedditVideoInfo videoInfo = redditAPI.fetchVideoInfo(this.model.getRedditURL());
        Downloader downloader = new Downloader();
        downloader.download(videoInfo, this.model.getDirectoryPath());
    }
}
