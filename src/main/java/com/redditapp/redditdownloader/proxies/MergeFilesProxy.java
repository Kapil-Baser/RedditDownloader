package com.redditapp.redditdownloader.proxies;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

public class MergeFilesProxy implements VideoMergeProxy {
    @Override
    public void merge(String videoFile, String audioFile, String savePath) {
        mergeFiles(videoFile, audioFile, savePath);
    }

    private void mergeFiles(String videoFile, String audioFile, String savePath) {
        try {
//            var props = ConfigLoader.getProperties();
            FFmpeg ffmpeg = new FFmpeg(FFmpeg.DEFAULT_PATH);
            FFprobe ffprobe = new FFprobe();

            FFmpegBuilder builder = new FFmpegBuilder()
                    .addInput(videoFile)
                    .addInput(audioFile)
                    .addOutput(savePath)
                    .addExtraArgs("-c", "copy")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            // Run a one-pass encode
            executor.createJob(builder).run();
            // Or run a two-pass encode (which is better quality at the cost of being slower)
            //executor.createTwoPassJob(builder).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
