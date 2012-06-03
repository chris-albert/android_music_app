package com.creasetoph.music.util;
import android.util.Log;

/**
 * Wrapper for android logging
 * Mostly just adds in the tag for each of the debug levels
 */
public class Logger {

    //Tag for logging messages
    private static final String TAG = "CreasetophMusicApp";

    public static void verbose(String str) {
        Log.v(TAG,str);
    }

    public static void debug(String str) {
        Log.d(TAG,str);
    }

    public static void info(String str) {
        Log.i(TAG,str);
    }

    public static void warn(String str) {
        Log.w(TAG,str);
    }

    public static void error(String str) {
        Log.e(TAG,str);
    }
    
    public static void log(Exception e) {
        error(e.getMessage());
    }
}
