package com.creasetoph.music;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LibraryActivity extends Activity {
    
    private static final int ALBUM_ITEM = R.layout.album_item;
    
    private ListView          _listView;
    private LibraryAdapter    _adapter;
    private LibraryController _controller;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _controller = LibraryController.getInstance();
        createListView(_controller.getLibrary());
	}
	
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.view_playlist:
                showPlaylist();
                return true;
            case R.id.view_player:
                showPlayer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void onListItemClick(AdapterView<?> parent, View view, int position,long id) {
        if(view.getId() == R.id.artist_item) {
            TextView tv = (TextView) view.findViewById(R.id.artist_name);
            String selection = tv.getText().toString();
            if(_controller.getLibrary().get(position + 1).getType() == "album") {
                _controller.removeAlbums(selection,position);
            }else {
                _controller.addAlbums(selection,position);
            }
            updateListView();
        }else if(view.getId() == R.id.album_item){
            TextView tv = (TextView) view.findViewById(R.id.album_name);
            String selection = tv.getText().toString();
            _controller.addAlbumToPlaylist(selection,position);
        }
    }
    
    public void showPlaylist() {
        Intent intent = new Intent(this,PlaylistActivity.class);
        startActivity(intent);
    }
    
    public void showPlayer() {
        Intent intent = new Intent(this,PlayerActivity.class);
        startActivity(intent);
    }
    
    public void createListView(ArrayList<LibraryItem> list) {
        _listView = new ListView(this);
        _listView.setTextFilterEnabled(true);
        _adapter = new LibraryAdapter(this,ALBUM_ITEM,list);
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                onListItemClick(parent,view,position,id);
            }
        }); 
        setContentView(_listView);
    }
    
    public void updateListView() {
        _adapter.notifyDataSetChanged();
    }
}

