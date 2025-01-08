package com.redditapp.redditdownloader;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.nio.file.Path;

public class RedditModel {
    private final StringProperty redditURL = new SimpleStringProperty("");
    private final StringProperty directoryPath = new SimpleStringProperty("");
    private final StringProperty outputLabel = new SimpleStringProperty("");

    public RedditModel() {
        this.directoryPath.set(Path.of(System.getProperty("user.dir")).toString());
    }

    public String getRedditURL() {
        return redditURL.get();
    }

    public StringProperty redditURLProperty() {
        return redditURL;
    }

    public void setRedditURL(String redditURL) {
        this.redditURL.set(redditURL);
    }

    public String getDirectoryPath() {
        return directoryPath.get();
    }

    public StringProperty directoryPathProperty() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath.set(directoryPath);
    }

    public String getOutputLabel() {
        return outputLabel.get();
    }

    public StringProperty outputLabelProperty() {
        return outputLabel;
    }

    public void setOutputLabel(String output) {
        this.outputLabel.set(output);
    }
}
