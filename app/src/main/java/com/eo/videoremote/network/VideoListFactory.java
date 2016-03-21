package com.eo.videoremote.network;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import com.eo.videoremote.App;
import com.eo.videoremote.interfaces.VideoList;


/**
 * Created by tom on 18.3.2016.
 */
public class VideoListFactory {
    private static boolean MOCK = true;

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
        Context context = App.getContext();

        if (MOCK)
            sInstance = new MockVideoList(context, handler);
        else
            sInstance = new NetworkVideoList(context, handler);
    }
}
