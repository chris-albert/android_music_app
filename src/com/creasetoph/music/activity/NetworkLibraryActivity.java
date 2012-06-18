package com.creasetoph.music.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.creasetoph.music.R;
import com.creasetoph.music.adapter.LibraryAdapter;
import com.creasetoph.music.controller.LibraryController;
import com.creasetoph.music.item.LibraryItem;
import com.creasetoph.music.model.MusicModelFactory;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.object.Library;
import com.creasetoph.music.util.Logger;

import java.util.ArrayList;


public class NetworkLibraryActivity extends LibraryActivity {

    protected MusicModelFactory.Type getLibraryType() {
        return MusicModelFactory.Type.network;
    }

}