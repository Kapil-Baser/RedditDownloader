package com.redditapp.redditdownloader;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.nio.file.Path;

public class RedditModel {
    private final StringProperty redditURL = new SimpleStringProperty("");
    private final StringProperty directoryPath = new SimpleStringProperty("");
    private final StringProperty outputLabel = new SimpleStringProperty("");
    private final ObjectProperty<File> selectedDirectory = new SimpleObjectProperty<>();
    private final ObjectProperty<Color> outputLabelColor = new SimpleObjectProperty<>();

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

    public ObjectProperty<File> selectedDirectoryProperty() {
        return selectedDirectory;
    }

    public ObjectProperty<Color> outputLabelColorProperty() {
        return outputLabelColor;
    }

    public void setOutputLabelColor(Color color) {
        this.outputLabelColor.set(color);
    }
}
