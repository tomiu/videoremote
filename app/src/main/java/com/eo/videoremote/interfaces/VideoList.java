package com.eo.videoremote.interfaces;

import com.eo.videoremote.models.Video;

import java.util.List;

/**
 * Created by tom on 18.3.2016.
 */
public interface VideoList {

    /**
     * Fetches the video list asynchronously
     * @param path Folder from where to fetch the video list, or null to use defaults
     * @param threadCallback
     */
    void fetchVideoList(String path, ThreadCallback threadCallback);
}
