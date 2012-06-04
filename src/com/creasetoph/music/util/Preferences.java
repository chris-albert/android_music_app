package com.creasetoph.music.util;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.creasetoph.music.activity.Creasetoph_musicActivity;

/**
 * Helper for dealing with preferences
 */
public class Preferences {

    //Preferences available to this app
    public enum Name {
        json_url,
        stream_url,
        media_location
    }

    //Instance of self
    private static Preferences _instance = null;
    //Where pref for this app live
    private SharedPreferences _sharedPreferences = null;

    /**
     * Gets the instance of preferences, and if it doesn't exist it creates it
     * @return The preferences instance
     */
    public static Preferences getInstance() {
        if(_instance == null) {
            _instance = new Preferences();
        }
        return _instance;
    }

    /**
     * Private constructor sets sharedPreferences with default for app
     */
    private Preferences() {
        _sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Creasetoph_musicActivity.appContext);
    }

    /**
     * Gets the sharedPreferences for this object
     * @return Our SharedPreferences object
     */
    private SharedPreferences getSharedPreferences() {
        return _sharedPreferences;
    }

    /**
     * Gets a string preference for our app
     * @param name Name of preference to find
     * @param defaultValue Value to return if no preference found
     * @return The preference value
     */
    public static String getString(Name name,String defaultValue) {
        return getInstance().getSharedPreferences().getString(name.toString(),defaultValue);
    }

    /**
     * Gets a string preference for out app
     * Sets a defaultValue of a string that hopefully will make debugging easier
     * @param name Name of preference to find
     * @return The preference value
     */
    public static String getString(Name name) {
        return getString(name,"No " + name.toString() + " found");
    }

}
