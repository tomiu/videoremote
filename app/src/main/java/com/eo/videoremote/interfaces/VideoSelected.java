package com.eo.videoremote.interfaces;

import com.eo.videoremote.models.Video;

/**
 * Created by tom on 19.3.2016.
 */
public interface VideoSelected {
    void onVideoSelected(Video video);
    void setTitle(CharSequence title);
}
