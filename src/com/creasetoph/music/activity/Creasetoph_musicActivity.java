package com.creasetoph.music.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import com.creasetoph.music.R;
import com.creasetoph.music.model.MusicModelManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.creasetoph.music.receiver.HeadsetReceiver;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.util.Preferences;

public class Creasetoph_musicActivity extends Activity {

    public static Context appContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences,false);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        HeadsetReceiver headsetReceiver = new HeadsetReceiver();
        registerReceiver(headsetReceiver,intentFilter);
        appContext = getApplicationContext();
        openTabs();
    }

    private void openTabs() {
        Intent intent = new Intent(this,MainTabActivity.class);
        startActivity(intent);
    }

    protected void onStop() {
        super.onStop();
    }
}
