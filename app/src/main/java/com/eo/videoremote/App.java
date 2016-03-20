package com.eo.videoremote;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.eo.videoremote.utils.Logger;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by tomiurankar on 06/09/14.
 */
public class App extends android.app.Application {
    public static final String TAG = App.class.getSimpleName();
    private static Context sAppContext;
    private static final Gson sGson = new Gson();
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    static {
        Logger.error(TAG, "WHO GOES HERE");
        /*
        //http://stackoverflow.com/questions/4802887/gson-how-to-exclude-specific-fields-from-serialization-without-annotations/17733569#17733569
        sGson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.serialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .create();*/
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.warning(TAG, "time: " + new Date());
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
        sAppContext = this;

        final int versionCode = getVersionCode();
        final String deviceName = getDeviceName();
    }

    /**
     *
     * @return application context
     */
    public static Context getContext() {
        return sAppContext;
    }

    public static Gson getGson() {
        return sGson;
    }

    public static String getVersionName() {
        String versionName = "";
        try {
            PackageInfo pInfo = sAppContext.getPackageManager().getPackageInfo(sAppContext.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionName;
    }

    public static int getVersionCode() {
        int versionCode = -1;
        try {
            PackageInfo pInfo = sAppContext.getPackageManager().getPackageInfo(sAppContext.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return StringUtils.capitalize(model);
        } else {
            return StringUtils.capitalize(manufacturer) + " " + model;
        }
    }

    public static Handler getUiHandler() {
        return mHandler;
    }

    /**
     * Posts to UI thread
     * @param runnable
     */
    public static void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static android.content.SharedPreferences getSharedPreferences() {
        return getContext().getSharedPreferences(sAppContext.getPackageName(), MODE_PRIVATE);
    }

    private static final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        private static final String TAG = "ActivityLifecycle";

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logger.debug(TAG, "onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            Logger.debug(TAG, "onActivityDestroyed:" + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            Logger.debug(TAG, "onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            Logger.debug(TAG, "onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity,
                                                Bundle outState) {
            Logger.debug(TAG, "onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Logger.debug(TAG, "onActivityStarted:" + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            Logger.debug(TAG, "onActivityStopped:" + activity.getLocalClassName());
        }
    }
}
