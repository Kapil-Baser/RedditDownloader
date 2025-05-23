package com.redditapp.redditdownloader;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Merge {
    private final String videoFile;
    private final String audioFile;
    private final String savePath;

    public Merge(String videoFile, String audioFile, String savePath) {
        this.videoFile = videoFile;
        this.audioFile = audioFile;
        this.savePath = savePath;
    }

    public void merge() {
        mergeFiles();
        // Deleting the files which we no longer need
        cleanupAfterMerge();
    }

    private void mergeFiles() {
        try {
            var props = ConfigLoader.getProperties();
            FFmpeg ffmpeg = new FFmpeg(FFmpeg.DEFAULT_PATH);
            FFprobe ffprobe = new FFprobe();

            FFmpegBuilder builder = new FFmpegBuilder()
                    .addInput(this.videoFile)
                    .addInput(this.audioFile)
                    .addOutput(this.savePath)
                    .addExtraArgs("-c", "copy")
                    .done();
//            FFmpegBuilder builder = new FFmpegBuilder()
//                    .addInput("https://v.redd.it/41whyivw2v471/HLSPlaylist.m3u8?a=1739891850%2CZDc2NTMzZTM0YzkwNTk5Mzg4YjMxODE2NzVkMmM5MDJmMDg3ZTk0NmUzYmZhNzRjMmEyYTM3ZWUwODdiY2FkOQ%3D%3D&amp;v=1&amp;f=sd")
//                    .addOutput(this.savePath)
//                    .addExtraArgs("-c", "copy")
//                    .done();
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            // Run a one-pass encode
            executor.createJob(builder).run();

            // Or run a two-pass encode (which is better quality at the cost of being slower)
            //executor.createTwoPassJob(builder).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanupAfterMerge() {
        try {
            Files.deleteIfExists(Paths.get(this.videoFile));
            Files.deleteIfExists(Paths.get(this.audioFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
