package com.redditapp.redditdownloader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    private Utils() {}

    public static String getFileName(String fileURL) {
        try {
            return Paths.get(new URI(fileURL).getPath()).getFileName().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String trimURL(String URL) {
        return String.join("", URL.split("https://www.reddit.com"));
    }

    public static void cleanupAfterMerge(String videoFile, String audioFile) {
        try {
            Files.deleteIfExists(Paths.get(videoFile));
            Files.deleteIfExists(Paths.get(audioFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
