package com.creasetoph.music.activity;

import com.creasetoph.music.activity.LibraryActivity;
import com.creasetoph.music.model.MusicModelManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Creasetoph_musicActivity extends Activity {
	
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
        new GetMediaTask().execute(MusicModelManager.NETWORK);
    }
    
    private void getMediaFromDisk() {
        _progressDialog = ProgressDialog.show(this,"","Loading from disk, Please Wait...",true);
        new GetMediaTask().execute(MusicModelManager.LOCAL);
    }
    
    private class GetMediaTask extends AsyncTask<String,Void,String> {
        
        protected String doInBackground(String... params) {
        	MusicModelManager.initializeModel(params[0]);
        	return null;
        }
        
        protected void onPostExecute(String json) {
            onMediaFetch();
        }
    }
}
