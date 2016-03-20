package com.eo.videoremote.network;

import android.os.Handler;

import com.eo.videoremote.interfaces.ThreadCallback;
import com.eo.videoremote.interfaces.VideoList;

/**
 * Created by tom on 18.3.2016.
 */
class DefaultVideoList implements VideoList {
    private final Handler mHandler;

    public DefaultVideoList(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void fetchVideoList(String path, ThreadCallback threadCallback) {

    }
}
