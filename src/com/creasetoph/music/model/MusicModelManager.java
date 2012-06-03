package com.creasetoph.music.model;

public class MusicModelManager {

    public static final String LOCAL = "local";
    public static final String NETWORK = "network";

    private static MusicModel _model = null;

    public static MusicModel fetchModel() {
        if (_model != null) {
            return _model;
        }
        throw new RuntimeException("Cant fetch model before initializing it");
    }

    public static void initializeModel(String type) {
        if (type.equals(NETWORK)) {
            _model = NetworkMusicModel.getInstance();
        } else if (type.equals(LOCAL)) {
            //model = new LocalMusicModel();
        }
    }
}
