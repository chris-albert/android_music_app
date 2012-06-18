package com.creasetoph.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.creasetoph.music.model.MusicModelFactory;

public class LocalLibraryActivity extends LibraryActivity {

    protected MusicModelFactory.Type getLibraryType() {
        return MusicModelFactory.Type.local;
    }
}