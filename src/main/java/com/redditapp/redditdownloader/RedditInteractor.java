package com.redditapp.redditdownloader;

public class RedditInteractor implements DownloadCompleteListener{
    private RedditModel model;
    private RedditAPI redditAPI;

    public RedditInteractor(RedditModel model) {
        this.model = model;
        this.redditAPI = new RedditAPI(ConfigLoader.getProperties());
    }

    public void start() {
        RedditVideoInfo videoInfo = redditAPI.fetchVideoInfo(this.model.getRedditURL());
        Downloader downloader = new Downloader(this);
        downloader.download(videoInfo, this.model.getDirectoryPath());
    }

    @Override
    public void onDownloadComplete(boolean shouldMerge, String videoSavePath, String audioSavePath) {
        // Merging
    }
}
