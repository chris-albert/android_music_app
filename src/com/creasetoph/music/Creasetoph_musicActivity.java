package com.creasetoph.music;

import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

public class Creasetoph_musicActivity extends Activity {
	
	//Preferences constants
	public static final String PREF_NAME       = "CreasetophMusicPrefs";
	public static final String BASE_JSON_URL   = "BaseJsonUrl";
	public static final String BASE_STREAM_URL = "BaseStreamUrl";
	//Default preferences
	public static final String DEFAULT_BASE_JSON_URL   = "http://music.creasetoph.com:1338/music/json";
	public static final String DEFAULT_BASE_STREAM_URL = "http://music.creasetoph.com:1338/music/stream";
	
	//Preferences 
	SharedPreferences settings;
	
	protected ProgressDialog _progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askMediaLocation();
    }
    
    private void onMediaFetch() {
        Intent intent = new Intent(this,LibraryActivity.class);
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
            .setPositiveButton("Local",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    getMediaFromDisk();
                }
            })
            .setNegativeButton("Network",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    getMediaFromNetwork();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }
    
    private void getMediaFromNetwork() {
        _progressDialog = ProgressDialog.show(this,"","Loading from network, Please Wait...",true);
        HttpMediaModel.getInstance().getMedia(new Callable<Object>() {
            public Object call() throws Exception {
                onMediaFetch();
                return null;
            }
        });
    }
    
    private void getMediaFromDisk() {
        _progressDialog = ProgressDialog.show(this,"","Loading from disk, Please Wait...",true);
        LocalMediaModel.getInstance().getMedia(new Callable<Object>() {
            public Object call() throws Exception {
                onMediaFetch();
                return null;
            }
        });
    }
}
