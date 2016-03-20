package com.eo.videoremote.network;

import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.eo.videoremote.interfaces.ThreadCallback;
import com.eo.videoremote.interfaces.VideoList;
import com.eo.videoremote.models.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tom on 18.3.2016.
 */
class MockVideoList implements VideoList {
    private static final String HOME_DIRECTORY = System.getProperty("user.home");
    private final Random mRandom = new Random();
    private final Handler mHandler;

    private List<Video> mVideoList;

    public MockVideoList(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void fetchVideoList(final String path, final ThreadCallback threadCallback) {
        final Handler callingHandler = new Handler();
        threadCallback.workerStart();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                final File dir = TextUtils.isEmpty(path) ? Environment.getExternalStorageDirectory() : new File(path);
                File[] files = dir.listFiles();

                if (files == null) {
                    callingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            threadCallback.workerEnd(null, new IllegalStateException("Could not get directory list from path: " + dir));
                        }
                    });
                } else {
                    final List<Video> videoList = new ArrayList<>(files.length);
                    for (File file : files) {
                        if (file.isDirectory() && file.listFiles().length==0) // dont add empty folders. This will only work if next folder is empty. If next next folder is empty, then the first one will still be returned.
                            continue;

                        videoList.add(new Video(file));
                    }

                    callingHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            threadCallback.workerEnd(videoList, null);
                        }
                    }, 300 + mRandom.nextInt(1000)); // to better mock world scenario
                }
            }
        });


    }
}
