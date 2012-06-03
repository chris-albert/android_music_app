package com.creasetoph.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.creasetoph.music.R;


/**
 * Activity for viewing and setting preferences
 */
public class PreferencesActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}