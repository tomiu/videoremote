package com.eo.videoremote.network;

import android.os.*;
import android.os.Process;

import com.eo.videoremote.Consts;
import com.eo.videoremote.interfaces.VideoList;


/**
 * Created by tom on 18.3.2016.
 */
public class VideoListFactory {
    private static VideoList sInstance;

    private VideoListFactory(){
    };

    public static synchronized VideoList getInstance() {
        if (sInstance == null)
            createInstance();

        return sInstance;
    }

    private static void createInstance() {
        HandlerThread handlerThread = new HandlerThread("networkThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        if (Consts.mock)
            sInstance = new MockVideoList(handler);
        else
            sInstance = new DefaultVideoList(handler);
    }
}
