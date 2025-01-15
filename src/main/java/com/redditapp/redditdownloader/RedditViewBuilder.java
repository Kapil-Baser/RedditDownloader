package com.redditapp.redditdownloader;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

public class RedditViewBuilder implements Builder<Region> {
    private final RedditModel model;
    private final Runnable downloadTaskConsumer;
    private final Consumer<Stage> browseHandler;

    public RedditViewBuilder (RedditModel model, Runnable downloadTaskConsumer, Consumer<Stage> browseHandler) {
        this.model = model;
        this.downloadTaskConsumer = downloadTaskConsumer;
        this.browseHandler = browseHandler;
    }

    @Override
    public Region build() {
        return (Region) createContent();
    }

    private Parent createContent() {
        VBox results = new VBox(20, createInputRow(), createOutputLabel(), createFileDirRow());
        results.setAlignment(Pos.CENTER);
        results.getStylesheets().add(Objects.requireNonNull(this.getClass()
                .getResource("/com/redditapp/redditdownloader/css/buttonStyle.css"))
                .toExternalForm());
        return results;
    }

    private Node createInputRow() {
        HBox inputRow = new HBox(createInputTextField(), createDownloadButton());
        inputRow.setSpacing(6);
        inputRow.setAlignment(Pos.CENTER);
        return inputRow;
    }

    private Node createFileDirRow() {
        HBox hBox = new HBox(createFilePathTextField(), createBrowseButton());
        hBox.setSpacing(6);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private Node createBrowseButton() {
        Button button = new Button("Browse");
        button.getStyleClass().add("prefWidth-button");
        button.setOnAction(event -> {
            Stage stage = (Stage) button.getScene().getWindow();
            browseHandler.accept(stage);
        });
        return button;
    }

    private Node createInputTextField() {
        TextField textField = new TextField();
        textField.setPrefWidth(400);
        textField.setPromptText("Reddit URL");
        textField.textProperty().bindBidirectional(this.model.redditURLProperty());
        return textField;
    }

    private Node createDownloadButton() {
        Button button = new Button("Download");
        button.getStyleClass().add("prefWidth-button");
        // Disable the download button unless there is anything in text field.
        button.disableProperty().bind(this.model.redditURLProperty().isEmpty());
        button.setOnAction(event -> {
            downloadTaskConsumer.run();
        });
        return button;
    }

    private Node createFilePathTextField() {
        TextField textField = new TextField();
        textField.setPrefWidth(400);
        textField.textProperty().bind(this.model.directoryPathProperty());
        this.model.setDirectoryPath(Path.of(System.getProperty("user.dir")).toString());
        return textField;
    }

    private Node createOutputLabel() {
        Label outputLabel = new Label("");
        outputLabel.textProperty().bind(this.model.outputLabelProperty());
        outputLabel.textFillProperty().bind(this.model.outputLabelColorProperty());
        return outputLabel;
    }
}
