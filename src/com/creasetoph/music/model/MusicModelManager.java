package com.creasetoph.music.model;

/**
 * Manager for music models
 */
public class MusicModelManager {

    /**
     * MusicModelManager types
     */
    public enum Type {
        local,network
    }

    //Music model currently in use
    private static MusicModel _model = null;

    /**
     * Gets the current model in use
     * @return The current MusicModel
     */
    public static MusicModel fetchModel() {
        if (_model != null) {
            return _model;
        }
        throw new RuntimeException("Cant fetch model before initializing it");
    }

    /**
     * Initializes the music model for the given type
     * @param type Type of music model to create
     */
    public static void initializeModel(Type type) {
        switch(type) {
            case network:
                _model = NetworkMusicModel.getInstance();
                break;
            case local:
                _model = LocalMusicModel.getInstance();
        }
    }
}
