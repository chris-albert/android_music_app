package com.creasetoph.music.activity;

import com.creasetoph.music.model.MusicModelFactory;


public class NetworkLibraryActivity extends LibraryActivity {

    protected MusicModelFactory.Type getLibraryType() {
        return MusicModelFactory.Type.network;
    }

}