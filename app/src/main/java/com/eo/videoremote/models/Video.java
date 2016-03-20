package com.eo.videoremote.models;

import java.io.File;

/**
 * Created by tom on 17.3.2016.
 */
public class Video {
    private String path;
    private boolean isDirectory;
    private String name;

    public Video(File file) {
        path = file.getPath();
        isDirectory = file.isDirectory();
        name = file.getName();
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public String toString() {
        return "Video{" +
                "path='" + path + '\'' +
                ", isDirectory=" + isDirectory +
                '}';
    }
}
