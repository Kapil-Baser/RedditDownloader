package com.redditapp.redditdownloader;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class RedditInteractor implements DownloadCompleteListener{
    private RedditModel model;
    //private RedditAPI redditAPI;
    private RedditVideoService service;

    public RedditInteractor(RedditModel model) {
        this.model = model;
        //this.redditAPI = new RedditAPI(ConfigLoader.getProperties());
        this.service = new RedditVideoService(ConfigLoader.getProperties());
    }

    public void start() {
        RedditVideoInfo videoInfo = this.service.getRedditVideoInfo(this.model.getRedditURL());
//        RedditVideoInfo videoInfo = redditAPI.fetchVideoInfo(this.model.getRedditURL());
//        if (videoInfo == null) {
//            throw new RuntimeException("Given URL does not contain a video");
//        }
        Downloader downloader = new Downloader(this);
        downloader.download(videoInfo, this.model.getDirectoryPath() + "\\");
    }

    @Override
    public void onDownloadComplete(boolean shouldMerge, String videoSavePath, String audioSavePath) {
        // Merging
        if (shouldMerge) {
            String outputPath = this.model.getDirectoryPath() + "\\" + Utils.getFileName(this.model.getRedditURL()) + ".mp4";
            Merge mergeFiles = new Merge(videoSavePath, audioSavePath, outputPath);
            mergeFiles.merge();
        }
    }

    public void chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        var startDir = Path.of(this.model.getDirectoryPath());
        directoryChooser.setInitialDirectory(startDir.toFile());
        Optional<File> selected = Optional.ofNullable(directoryChooser.showDialog(stage));
        selected.ifPresent(value -> this.model.setDirectoryPath((value.toString())));
    }

    public void updateStatus(String status, Color color) {
        Platform.runLater(() -> {
            this.model.setOutputLabelColor(color);
            this.model.setOutputLabel(status);
        });
    }
}
