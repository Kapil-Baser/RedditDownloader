package com.redditapp.redditdownloader;

public interface DownloadCompleteListener {
    void onDownloadComplete(boolean shouldMerge);
}
