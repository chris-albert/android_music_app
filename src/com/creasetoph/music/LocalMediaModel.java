package com.creasetoph.music;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import android.os.AsyncTask;

public class LocalMediaModel {

    private static LocalMediaModel _instance = null;
    
    public Callable<Object> _callback = null;
    
    public static final String DISK_PATH   = "/sdcard/Music";

    public static LocalMediaModel getInstance() {
        if(_instance == null) {
            _instance = new LocalMediaModel();
        }
        return _instance;
    }
    
    private class DownloadTask extends AsyncTask<String,Void,Map<String,Object>> {
        
        protected Map<String,Object> doInBackground(String... params) {
            getMediaListFromDisk();
            return getMediaListFromDisk();
        }
        
        protected void onPostExecute(Map<String,Object> map) {
            try { 
                MediaData.getInstance().setMap(map);
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
    
    private Map<String,Object> getMediaListFromDisk() {
        return pathToMap(DISK_PATH);
    }
    
    private Map<String,Object> pathToMap(String path) {
        File[] files = new File(path).listFiles();
        Map<String,Object> map = new HashMap<String,Object>();
        for(File file: files) {
            if(file.isDirectory()) {
                map.put(file.getName(),pathToMap(path + "/" + file.getName()));
            }else {
                map.put(file.getName(),"file");
            }
        }
        return map;
    }
}
