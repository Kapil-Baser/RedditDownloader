package com.redditapp.redditdownloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Downloader {
    private final DownloadCompleteListener listener;

    public Downloader(DownloadCompleteListener listener) {
        this.listener = Objects.requireNonNull(listener, "Should not be null");
    }

    public void download(RedditVideoInfo videoInfo, String savePath) {
        if (videoInfo.hasAudio()) {
            downloadWithAudio(videoInfo.fallbackURL(), savePath);
            // Now I will need to merge the two files.
        } else {
            URL videoURL = getURL(videoInfo.fallbackURL());
            String videoSavePath = savePath + Utils.getFileName(videoInfo.fallbackURL());
            downloadFile(videoSavePath, videoURL);
        }
    }

    private static URL getURL(String fileURLString) {
        try {
            return new URI(fileURLString).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpURLConnection setConnection(URL fileURL) {
        try {
            HttpURLConnection tempConnection = (HttpURLConnection) fileURL.openConnection();
            tempConnection.setRequestMethod("GET");
            tempConnection.setConnectTimeout(5000);
            tempConnection.setReadTimeout(5000);
            return tempConnection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(HttpURLConnection connection) {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadWithAudio(String fileURL, String savePath) {
        // Getting the audio URL with regular expression
        String audioUrlString = fileURL.replaceAll("DASH_\\d+" , "DASH_AUDIO_128");

        // Converting the url string to URL object
        URL videoURL = getURL(fileURL);
        URL audioURL = getURL(audioUrlString);

        // Absolute path where files will be saved
        String videoSavePath = savePath + Utils.getFileName(fileURL);
        String audioSavePath = savePath + Utils.getFileName(audioUrlString);

        // Download the video and audio file
        downloadFile(videoSavePath, videoURL);
        downloadFile(audioSavePath, audioURL);

        // Notifying that a merging is needed
        this.listener.onDownloadComplete(true, videoSavePath, audioSavePath);
    }

    private void downloadFile(String savePath, URL fileURL) {
        // Setting up the connection to server
        HttpURLConnection connection = setConnection(fileURL);

        try (InputStream in = getInputStream(connection);
             OutputStream os = Files.newOutputStream(Path.of(savePath))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
