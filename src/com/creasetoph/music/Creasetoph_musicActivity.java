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
	
	protected Activity self;
	protected ProgressDialog _progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _progressDialog = ProgressDialog.show(this,"","Loading, Please Wait...",true);
        setUpPrefs();
        self = this;   
        new DownloadJSONTask().execute("");
        HttpMediaModel hmm = HttpMediaModel.getInstance();
        hmm.getMedia(new Callable<Object>() {
            public Object call() throws Exception {
                Logger.log("Inside callback");
                return null;
            }
        });
    }
     
    public String getJSON() {
    	String json_url = settings.getString(BASE_JSON_URL, DEFAULT_BASE_JSON_URL);
    	return HttpUtil.httpGet(json_url);
    }
    
    public void setUpPrefs() {
    	settings = getSharedPreferences(PREF_NAME, 0);
    }
    
    protected void onStop() {
        super.onStop();
       _progressDialog.dismiss(); 
    }
    
    private class DownloadJSONTask extends AsyncTask<String,Void,String> {

		@Override
		protected String doInBackground(String... params) {
			return getJSON();
		}
		
		protected void onPostExecute(String json) {
			if(json != null) {
    			LibraryController.getInstance().setLibrary(json);
    			Intent intent = new Intent(self,LibraryActivity.class);
                startActivity(intent);
			}else { 
			    AlertDialog.Builder errorDialog = new AlertDialog.Builder(self);
			    errorDialog.setMessage("There was an error fetching JSON")
			        .setCancelable(false)
			        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            self.finish();
                            return;
                        }
                    });
			    AlertDialog ad = errorDialog.create();
			    ad.show();
			}
		}
    }
}
