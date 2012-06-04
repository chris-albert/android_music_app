package com.creasetoph.music.activity;

import android.content.Context;
import com.creasetoph.music.model.MusicModelManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.util.Preferences;

public class Creasetoph_musicActivity extends Activity {

    protected ProgressDialog _progressDialog;
    private static boolean mediaFetched = false;
    public static Context appContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.info("On create");
        appContext = getApplicationContext();
        if(!mediaFetched) {
            setUpMediaLocation();
        }else {
            openLibrary();
        }
    }

    public void onStart() {
        super.onStart();
        Logger.info("On start");
    }

    private void onMediaFetch() {
        mediaFetched = true;
        openLibrary();
    }

    private void openLibrary() {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }

    protected void onStop() {
        super.onStop();
        _progressDialog.dismiss();
    }

    private void setUpMediaLocation() {
        MusicModelManager.Type location;
        location = MusicModelManager.Type.valueOf(Preferences.getString(Preferences.Name.media_location));
        _progressDialog = ProgressDialog.show(this, "", "Loading from (" + location + "), Please Wait...", true);
        new GetMediaTask().execute(location);
    }

    private class GetMediaTask extends AsyncTask<MusicModelManager.Type, Void, String> {

        protected String doInBackground(MusicModelManager.Type... params) {
            MusicModelManager.initializeModel(params[0]);
            return null;
        }

        protected void onPostExecute(String json) {
            onMediaFetch();
        }
    }
}
