package com.creasetoph.music;

import java.util.concurrent.Callable;

import android.os.AsyncTask;

public class HttpMediaModel extends MediaModel{
    
    private static HttpMediaModel _instance = null;
    
    public Callable<Object> _callback = null;
    
    public static final String DEFAULT_BASE_JSON_URL   = "http://music.creasetoph.com:1338/music/json";

    public static HttpMediaModel getInstance() {
        if(_instance == null) {
            _instance = new HttpMediaModel();
        }
        return _instance;
    }
    
    private class DownloadTask extends AsyncTask<String,Void,String> {
        
        protected String doInBackground(String... params) {
            return HttpUtil.httpGet(DEFAULT_BASE_JSON_URL);
        }
        
        protected void onPostExecute(String json) {
            try {
                _callback.call();
            }catch(Exception e) {
                Logger.log(e);
            }
        }
    }

    public void getMedia(Callable<Object> callback) {
        _callback = callback;
        new DownloadTask().execute("");
    }
}
