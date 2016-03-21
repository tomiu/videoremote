package com.eo.videoremote.network;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.eo.videoremote.interfaces.ThreadCallback;
import com.eo.videoremote.interfaces.VideoInput;
import com.eo.videoremote.interfaces.VideoList;
import com.eo.videoremote.models.Video;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

/**
 * Created by tom on 18.3.2016.
 */
class NetworkVideoList implements VideoList {
    private static final java.lang.String TAG = NetworkVideoList.class.getSimpleName();

    private static final String START_IN_PATH = "smb://192.168.64.2/d/torrents/";
    private final Handler mHandler;
    private final Context mContext;

    public NetworkVideoList(Context context, Handler handler) {
        mContext = context;
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
                    callingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            threadCallback.workerEnd(videoList, null);
                        }

                    });
                } catch (final Exception e) {
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
     * // https://lists.samba.org/archive/jcifs/2007-September/007465.html
     * Gets list of videos
     *
     * @param path If empty or null, will default to samba share N1/d/torrents
     * @return
     * @throws IOException
     */
    private List<Video> getVideos(String path) throws IOException {
        final List<Video> videoList = new ArrayList<>();
        String source = START_IN_PATH;
        if (!TextUtils.isEmpty(path))
            source = path;

        String[] authentication = getUserAuthentication();
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, authentication[0], authentication[1]);
        SmbFile sourceFile = new SmbFile(source, auth);

        SmbFile[] files = sourceFile.listFiles();
        for (SmbFile smbFile : files) {
            videoList.add(new Video(wrap(smbFile)));
        }

        return videoList;
    }

    /**
     * @return String array. An array position 0 is username, on 1 is password
     * @throws IOException
     */
    private String[] getUserAuthentication() throws IOException {
        String[] fileList = {"passwords.properties"};
        Properties prop = new Properties();
        for (int i = fileList.length - 1; i >= 0; i--) {
            String file = fileList[i];
            InputStream fileStream = mContext.getAssets().open(file);
            prop.load(fileStream);
            fileStream.close();
        }
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        return new String[]{username, password};
    }

    /**
     * Wraps it into common interface, VideoInput
     *
     * @param file
     * @return
     */
    private VideoInput wrap(final SmbFile file) {
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