package com.creasetoph.music.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.creasetoph.music.*;
import com.creasetoph.music.adapter.PlaylistAdapter;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.util.Logger;

public class PlaylistActivity extends Activity {
    
    private static final int PLAYLIST_ITEM = R.layout.playlist_item;

    private ListView           _listView;
    private PlaylistAdapter _adapter;
    private PlaylistController _controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _controller = PlaylistController.getInstance();
        createListView();
    }
    
    public void onResume() {
        super.onRestart();
    }
    
    private ArrayList<PlaylistItem> getPlaylistList() {
        return _controller.getPlaylistItems();
    }
    
    private void createListView() {
        _listView = new ListView(this);
        _listView.setTextFilterEnabled(true);
        _adapter = new PlaylistAdapter(this,PLAYLIST_ITEM,getPlaylistList());
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                onListItemClick(parent,view,position,id);
            }
        }); 
        setContentView(_listView);
    }
    
    private void onListItemClick(AdapterView<?> parent, View view, int position,long id) {
        Logger.info("Track clicked");
        _controller.selectTrack(position);
        _adapter.notifyDataSetChanged();
    }
}

