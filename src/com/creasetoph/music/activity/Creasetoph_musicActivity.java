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

public class Creasetoph_musicActivity extends Activity {

    protected ProgressDialog _progressDialog;
    private boolean mediaFetched = false;
    public static Context appContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.info("On create");
        appContext = getApplicationContext();
        if(!mediaFetched) {
            askMediaLocation();
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

    private void askMediaLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Where would you like to get your media?")
                .setCancelable(false)
                .setPositiveButton("Local", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getMediaFromDisk();
                    }
                })
                .setNegativeButton("Network", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getMediaFromNetwork();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getMediaFromNetwork() {
        _progressDialog = ProgressDialog.show(this, "", "Loading from network, Please Wait...", true);
        new GetMediaTask().execute(MusicModelManager.Type.Network);
    }

    private void getMediaFromDisk() {
        _progressDialog = ProgressDialog.show(this, "", "Loading from disk, Please Wait...", true);
        new GetMediaTask().execute(MusicModelManager.Type.Local);
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
