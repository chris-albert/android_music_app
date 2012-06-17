package com.creasetoph.music.model;


public class MusicModelFactory {

    public enum Type {
        local,network
    }

    public static MusicModel getMusicModel(Type type) {
        switch (type) {
            case network:
                return new NetworkMusicModel();
//                return NetworkMusicModel.getInstance();
            case local:
                return new LocalMusicModel();
//                return LocalMusicModel.getInstance();
        }
        return null;
    }

}
