package com.creasetoph.music.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.creasetoph.music.util.Logger;

public class MusicModelManager {

    private static MusicModelManager _instance = null;
    private MusicModel networkMusicModel;
    private MusicModel localMusicModel;
    private Handler.Callback networkCallback = null;
    private Handler.Callback localCallback = null;
    private boolean networkModelDone = false;
    private boolean localModelDone = false;

    public static MusicModelManager getInstance() {
        if(_instance == null) {
            _instance = new MusicModelManager();
        }
        return _instance;
    }

    private MusicModelManager() {
        fetchModels();
    }

    private void fetchModels() {
        Logger.info("Fetching models");
        new GetMediaTask().execute(MusicModelFactory.Type.network);
        new GetMediaTask().execute(MusicModelFactory.Type.local);
    }

    public MusicModel getMusicModel(MusicModelFactory.Type type) {
        switch(type) {
            case network:
                return networkMusicModel;
            case local:
                return localMusicModel;
        }
        return null;
    }

    public void setMusicModel(MusicModelFactory.Type type,MusicModel musicModel) {
        switch(type) {
            case network:
                networkMusicModel = musicModel;
                networkModelDone = true;
                if(networkCallback != null) {
                    networkCallback.handleMessage(new Message());
                }
                break;
            case local:
                localMusicModel = musicModel;
                localModelDone = true;
                if(localCallback != null) {
                    localCallback.handleMessage(new Message());
                }
                break;
        }
    }

    public void setCallback(MusicModelFactory.Type type, Handler.Callback callback) {
        switch(type) {
            case network:
                networkCallback = callback;
                if(networkModelDone && networkCallback != null) {
                    networkCallback.handleMessage(new Message());
                }
                break;
            case local:
                localCallback = callback;
                if(localModelDone && localCallback != null) {
                    localCallback.handleMessage(new Message());
                }
                break;
        }
    }

    private class GetMediaTask extends AsyncTask<MusicModelFactory.Type, Void, MusicModel> {

        private MusicModelFactory.Type type;

        protected MusicModel doInBackground(MusicModelFactory.Type... params) {
            type = params[0];
            return MusicModelFactory.getMusicModel(type);
        }

        protected void onPostExecute(MusicModel musicModel) {
            Logger.info("Fetching model (" + type + ") finished");
            setMusicModel(type, musicModel);
        }
    }
}
