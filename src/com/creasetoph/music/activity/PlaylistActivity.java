package com.creasetoph.music.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.creasetoph.music.*;
import com.creasetoph.music.adapter.PlaylistAdapter;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.object.Track;
import com.creasetoph.music.util.Logger;

public class PlaylistActivity extends Activity {

    private static final int PLAYLIST_ITEM = R.layout.playlist_item;

    private ListView _listView;
    private PlaylistAdapter _adapter;
    private PlaylistController _controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("PlaylistActivity onCreate");
        _controller = PlaylistController.getInstance();
        createListView();
    }

    public void onStart()  {
        super.onStart();
        Logger.debug("PlaylistActivity onStart");
    }

    public void onResume() {
        super.onResume();
        updateListView();
        Logger.debug("PlaylistActivity onResume");
    }

    private List<Track> getPlaylistList() {
        return _controller.getPlaylistItems();
    }

    public boolean isActive(int index) {
        return index == _controller.getCurrentTrack();
    }

    private void createListView() {
        _listView = new ListView(this);
        _listView.setTextFilterEnabled(true);
        _adapter = new PlaylistAdapter(this, PLAYLIST_ITEM,new ArrayList<Track>());
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
        setContentView(_listView);
    }

    private void updateListView() {
        List<Track> tracks = getPlaylistList();
        Logger.info("tracks: " + tracks.size());
        _adapter.setItems(tracks);
    }

    private void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        Logger.info("Playlist clicked: " + position);
        _controller.selectTrack(position);
        _adapter.notifyDataSetChanged();
    }
}

