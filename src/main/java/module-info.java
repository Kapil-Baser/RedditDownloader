module com.redditapp.redditdownloader {
    requires javafx.controls;
    requires javafx.fxml;
    requires Reddit4J.master.d0472546d0;
    requires org.jsoup;
    requires com.google.gson;

    opens com.redditapp.redditdownloader to com.google.gson;
    //opens com.redditapp.redditdownloader to javafx.fxml;
    exports com.redditapp.redditdownloader;
}