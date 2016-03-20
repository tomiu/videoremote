package com.eo.videoremote.utils;

import android.content.Context;

import com.eo.videoremote.App;
import com.eo.videoremote.BuildConfig;


/**
 * Created by tomiurankar on 16/09/14.
 */
public class MyToast extends android.widget.Toast {

    private static android.widget.Toast sToast;

    /**
     * Construct an empty MyToast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *

     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     */
    public MyToast(Context context) {
        super(context);
    }

    public static void showToast(String msg) {
        showToast(msg, android.widget.Toast.LENGTH_SHORT);
    }

    public static void showToast(final CharSequence msg, final int length) {
        App.getUiHandler().post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = android.widget.Toast.makeText(App.getContext(), msg, MyToast.LENGTH_SHORT);
                } else {
                    sToast.setText(msg);
                    sToast.setDuration(length);
                }
                sToast.show();
            }
        });
    }

    public static void showToastAndLogError(String TAG, String msg) {
        showToastAndLogError(TAG, msg, null);
    }

    public static void showToastAndLogError(String TAG, String msg, Throwable e) {
        Logger.error(TAG, msg, e);
        if (BuildConfig.DEBUG)
            showToast(msg + "\n" + e, android.widget.Toast.LENGTH_LONG);
    }
}
