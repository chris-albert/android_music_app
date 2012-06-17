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


public class NetworkLibraryActivity extends Activity {

    private static final int ALBUM_ITEM = R.layout.album_item;

    private ListView          _listView;
    private LibraryAdapter    _adapter;
    private LibraryController _controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("NetworkLibraryActivity onCreate");
        fetchLibrary();
    }

    private void fetchLibrary() {
        showLibraryLoading();
        MusicModelManager.getInstance().setCallback(MusicModelFactory.Type.network,new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                modelFinished();
                return true;
            }
        });
    }

    private void showLibraryLoading() {
        setContentView(R.layout.library_loading);
    }

    private void showLibraryFailed() {
        setContentView(R.layout.library_fail);
    }

    public void retryOnClick(View view) {
        Logger.info("retry clicked");
        showLibraryLoading();
        MusicModelManager.getInstance().retryModelFetch(MusicModelFactory.Type.network);
    }

    private void modelFinished() {
        Logger.info("modelFinished");
        _controller = new LibraryController(MusicModelFactory.Type.network);
        if(_controller.libraryEmpty()) {
            showLibraryFailed();
        }else {
            createListView(_controller.getLibrary());
        }
    }

    public void onResume() {
        Logger.debug("NetworkLibraryActivity onResume");
        super.onResume();
//        updateListView();
    }

    private void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.getId() == R.id.artist_item) {
            TextView tv = (TextView) view.findViewById(R.id.artist_name);
            String selection = tv.getText().toString();
            Logger.info("Size: " + _controller.getLibrarySize());
            Logger.info("Position: " + position);
            if (_controller.getLibrarySize() > position + 1 &&
                    _controller.getLibrary().get(position + 1).getType() == LibraryItem.Type.Album) {
                _controller.removeAlbums(selection, position);
            } else {
                _controller.addAlbums(selection, position);
            }
            _adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.album_item) {
            TextView tv = (TextView) view.findViewById(R.id.album_name);
            String selection = tv.getText().toString();
            _controller.clearPlaylist();
            _controller.addAlbumToPlaylist(selection, position);
        }
    }

    public void createListView(ArrayList<LibraryItem> list) {
        _listView = new ListView(this);
        _listView.setTextFilterEnabled(true);
        _adapter = new LibraryAdapter(this, ALBUM_ITEM, list);
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
        setContentView(_listView);
    }

    public void updateListView() {
        _adapter.setItems(_controller.getLibrary());
    }
}