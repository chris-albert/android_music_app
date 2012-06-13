package com.creasetoph.music.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.creasetoph.music.*;
import com.creasetoph.music.adapter.PlaylistAdapter;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.item.PlaylistItem;
import com.creasetoph.music.util.Logger;

public class PlaylistActivity extends Activity {

    private static final int PLAYLIST_ITEM = R.layout.playlist_item;
    //Color of current playlist track
    private static final int CURRENT_COLOR = 0xAA000088; //Dark Blue

    private ListView _listView;
    private PlaylistAdapter _adapter;
    private PlaylistController _controller;
    private int currentlySelected = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("PlaylistActivity onCreate");
        _controller = PlaylistController.getInstance();
        createListView();
    }

    public void onResume() {
        super.onResume();
        Logger.debug("PlaylistActivity onResume");
        setActive();
    }

    public void onStart()  {
        super.onStart();
        Logger.debug("PlaylistActivity onStart");
    }

    private ArrayList<PlaylistItem> getPlaylistList() {
        return _controller.getPlaylistItems();
    }

    public void setActive() {
        setActive(_controller.getCurrentTrack());
    }

    public void setActive(int index) {
        Logger.info("Setting active: " + index);
        deactivateItem(currentlySelected);
        activateItem(index);
    }

    private View getViewListItem(int itemIndex) {
        return _listView.getChildAt(itemIndex);
    }

    private void setViewItemBackgroundColor(int itemIndex,int color) {
        View v = getViewListItem(itemIndex);
        if(v != null) {
            v.setBackgroundColor(color);
        }
    }

    private void activateItem(int itemIndex) {
        currentlySelected = itemIndex;
        setViewItemBackgroundColor(itemIndex,CURRENT_COLOR);
    }

    private void deactivateItem(int itemIndex) {
        if(itemIndex != -1) {
            setViewItemBackgroundColor(itemIndex,0);
        }
    }

    private void createListView() {
        _listView = new ListView(this);
        _listView.setTextFilterEnabled(true);
        _adapter = new PlaylistAdapter(this, PLAYLIST_ITEM, getPlaylistList());
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
        setContentView(_listView);
    }

    private void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        Logger.info("Playlist clicked: " + position);
        setActive(position);
        _controller.selectTrack(position);
    }
}

