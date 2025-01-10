package com.redditapp.redditdownloader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Utils {

    public static String getFileName(String fileURL) {
        try {
            return Paths.get(new URI(fileURL).getPath()).getFileName().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
