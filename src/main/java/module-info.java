module com.redditapp.redditdownloader {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.redditapp.redditdownloader to javafx.fxml;
    exports com.redditapp.redditdownloader;
}