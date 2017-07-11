package com.example.administrator.videolistplay;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class VideoInfo {
    private String title;
    private String path;
    private long duration;
    private long size;

    public VideoInfo(String title, String path, long duration, long size) {
        this.title = title;
        this.path = path;
        this.duration = duration;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
