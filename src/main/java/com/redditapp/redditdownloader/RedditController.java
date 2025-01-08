package com.redditapp.redditdownloader;

import javafx.concurrent.Task;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class RedditController {
    private final Builder<Region> viewBuilder;
    private final RedditInteractor interactor;

    public RedditController() {
        RedditModel model = new RedditModel();
        interactor = new RedditInteractor(model);
        this.viewBuilder = new RedditViewBuilder(model, this::createDownloadTask);
    }

    private void createDownloadTask(Runnable fetchRedditData) {
        Task<Void> downloadTask = new Task<>() {
            @Override
            protected Void call() {
                interactor.start();
                return null;
            }
        };
        Thread fetchRedditDataThread = new Thread(downloadTask);
        fetchRedditDataThread.setDaemon(true);
        fetchRedditDataThread.start();
    }

    public Region getView() {
        return this.viewBuilder.build();
    }
}
