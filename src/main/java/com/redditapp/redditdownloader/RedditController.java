package com.redditapp.redditdownloader;

import javafx.scene.layout.Region;
import javafx.util.Builder;

public class RedditController {
    private final Builder<Region> viewBuilder;
    private final RedditInteractor interactor;

    public RedditController() {
        RedditModel model = new RedditModel();
        interactor = new RedditInteractor(model);
        this.viewBuilder = new RedditViewBuilder(model, interactor);
    }

    public Region getView() {
        return this.viewBuilder.build();
    }
}
