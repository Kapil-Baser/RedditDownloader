package com.redditapp.redditdownloader.services;

import com.redditapp.redditdownloader.proxies.VideoMergeProxy;

public class MergeService {
    private final VideoMergeProxy merge;

    public MergeService(VideoMergeProxy videoMergeProxy) {
        this.merge = videoMergeProxy;
    }

    public void mergeFiles(String videoFile, String audioFile, String savePath) {
        merge.merge(videoFile, audioFile, savePath);
    }
}
