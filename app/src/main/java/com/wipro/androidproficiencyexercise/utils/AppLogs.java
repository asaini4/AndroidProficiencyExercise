package com.wipro.androidproficiencyexercise.utils;

import android.util.Log;

import com.wipro.androidproficiencyexercise.BuildConfig;

public class AppLogs {

    public static void logV (String tag, String logMessage) {

        if (BuildConfig.DEBUGGABLE && BuildConfig.LOG_V) {
            Log.v(tag, logMessage);
        }
    }

    public static void logE (String tag, String logMessage) {

        if (BuildConfig.DEBUGGABLE && BuildConfig.LOG_E) {
            Log.e(tag, logMessage);
        }
    }

    public static void logD (String tag, String logMessage) {

        if (BuildConfig.DEBUGGABLE && BuildConfig.LOG_D) {
            Log.d(tag, logMessage);
        }
    }

    public static void logI (String tag, String logMessage) {

        if (BuildConfig.DEBUGGABLE && BuildConfig.LOG_I) {
            Log.i(tag, logMessage);
        }
    }

    public static void logW (String tag, String logMessage) {

        if (BuildConfig.DEBUGGABLE && BuildConfig.LOG_W) {
            Log.w(tag, logMessage);
        }
    }
}