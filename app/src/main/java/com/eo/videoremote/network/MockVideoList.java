package com.eo.videoremote.network;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.eo.videoremote.interfaces.ThreadCallback;
import com.eo.videoremote.interfaces.VideoInput;
import com.eo.videoremote.interfaces.VideoList;
import com.eo.videoremote.models.Video;
import com.eo.videoremote.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tom on 18.3.2016.
 */
class MockVideoList implements VideoList {
    private static final String HOME_DIRECTORY = System.getProperty("user.home");
    private static final java.lang.String TAG = MockVideoList.class.getSimpleName();
    private final Random mRandom = new Random();
    private final Handler mHandler;

    public MockVideoList(Context context, Handler handler) {
        mHandler = handler;
    }

    @Override
    public void fetchVideoList(final String path, final ThreadCallback threadCallback) {
        final Handler callingHandler = new Handler();
        threadCallback.workerStart();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Video> videoList = getVideos(path);
                    callingHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            threadCallback.workerEnd(videoList, null);
                        }
                    }, 300 + mRandom.nextInt(1000)); // to better mock world scenario

                } catch (final IOException e) {
                    callingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            threadCallback.workerEnd(null, e);
                        }
                    });
                }
            }
        });
    }

    /**
     * Gets list of videos
     * @param path If empty or null, will default to Phone's external storage
     * @return
     * @throws IOException
     */
    private List<Video> getVideos(String path) throws IOException {
        final File dir = TextUtils.isEmpty(path) ? Environment.getExternalStorageDirectory() : new File(path);
        File[] files = dir.listFiles();

        if (files == null)
            throw new IOException("Could not get directory list from path: " + dir);

        final List<Video> videoList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory() && file.listFiles().length == 0) // dont add empty folders. This will only work if next folder is empty. If next next folder is empty, then the first one will still be returned.
                continue;
            videoList.add(new Video(wrap(file)));
        }

        return videoList;
    }


    /**
     * Wraps it into common interface, VideoInput
     *
     * @param file
     * @return
     */
    private VideoInput wrap(final File file) {
        return new VideoInput() {
            @Override
            public String getPath() {
                return file.getPath();
            }

            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public boolean isDirectory() throws IOException {
                return file.isDirectory();
            }
        };
    }
}
