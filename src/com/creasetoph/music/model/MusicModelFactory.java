package com.creasetoph.music.model;


public class MusicModelFactory {

    public enum Type {
        local,network
    }

    public static MusicModel getMusicModel(Type type) {
        switch (type) {
            case network:
                return NetworkMusicModel.getInstance();
            case local:
                return LocalMusicModel.getInstance();
        }
        return null;
    }

}
