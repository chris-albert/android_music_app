package com.creasetoph.music;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LibraryView {
    
    private static final int ALBUM_ITEM = R.layout.album_item;
    
    private ListView _listView;
    private LibraryAdapter _adapter;
    private ArrayList<LibraryItem> _currentLibrary = new ArrayList<LibraryItem>();

    public void createListView(Activity activity) {
        _listView = new ListView(activity);
        _listView.setTextFilterEnabled(true);
        _adapter = new LibraryAdapter(activity,ALBUM_ITEM,_currentLibrary);
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                //onListItemClick(parent,view,position,id);
            }
        });
    }
}
