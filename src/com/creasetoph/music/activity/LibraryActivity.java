package com.creasetoph.music.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import com.creasetoph.music.R;
import com.creasetoph.music.adapter.LibraryAdapter;
import com.creasetoph.music.controller.LibraryController;
import com.creasetoph.music.model.MusicModelFactory;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.object.*;
import com.creasetoph.music.util.Logger;

import java.util.ArrayList;
import java.util.Collections;

public abstract class LibraryActivity extends Activity {

    private static final int ALBUM_ITEM = R.layout.album_item;

    private ListView          _listView;
    private LibraryAdapter _adapter;
    private LibraryController _controller;

    public enum ActionOptions {
        add("Add to playlist"),
        replace("Replace playlist"),
        cancel("Cancel");

        private String _str;

        ActionOptions(String str) {
            _str = str;
        }

        public static CharSequence[] getCharSequence() {
            CharSequence[] cs = new CharSequence[values().length];
            for(int i = 0;i < values().length;i++) {
                cs[i] = values()[i].toString();
            }
            return cs;
        }

        public static ActionOptions get(int i) {
            return values()[i];
        }

        public String toString() {
            return _str;
        }
    }

    private MusicItem _actionSelected = null;

    private CharSequence[] _actionItems = {"Add to playlist","Replace playlist","Cancel"};

    protected abstract MusicModelFactory.Type getLibraryType();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug("LibraryActivity onCreate");
        fetchLibrary();
    }

    private void fetchLibrary() {
        showLibraryLoading();
        MusicModelManager.getInstance().setCallback(getLibraryType(),new Handler.Callback() {
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
        showLibraryLoading();
        MusicModelManager.getInstance().retryModelFetch(getLibraryType());
    }

    private void modelFinished() {
        _controller = new LibraryController(getLibraryType());
        if(_controller.libraryEmpty()) {
            showLibraryFailed();
        }else {
            createListView(_controller.getModelLibrary());
        }
    }

    public void onResume() {
        Logger.debug("LibraryActivity onResume");
        super.onResume();
    }

    public void createListView(Library library) {
        ArrayList<Artist> artists = library.getArtistList();
        Collections.sort(artists);
        _listView = new ListView(this);
        _listView.setDivider(new ColorDrawable(this.getResources().getColor(R.color.dark_gray)));
        _listView.setDividerHeight(1);
        _listView.setTextFilterEnabled(true);
        _adapter = new LibraryAdapter(this, ALBUM_ITEM,artists);
        _listView.setAdapter(_adapter);
        setContentView(_listView);
    }

    public void onActionClick(View view) {
        Logger.debug("Action Clicked");
        View v = (View) view.getParent();
        int id = v.getId();
        View itemView;
        if(id == R.id.artist_item) {
            itemView = v.findViewById(R.id.artist_name);
            Artist artist = (Artist) itemView.getTag(R.id.artist_item);
            _actionSelected = artist;
            Logger.info("Artist: " + artist.getName());
        }else if(id == R.id.album_item) {
            itemView = v.findViewById(R.id.album_name);
            Album album = (Album) itemView.getTag(R.id.album_item);
            _actionSelected = album;
            Logger.info("Artist: " + album.getArtist() + ",Album: " + album.getName());
        }else if(id == R.id.track_item) {
            itemView = v.findViewById(R.id.track_name);
            Track track = (Track) itemView.getTag(R.id.track_item);
            _actionSelected = track;
            Logger.info("Artist: " + track.getArtist() + ",Album: " + track.getAlbum() + ",Track: " + track.getName());
        }
        showDialog(1);
    }

    private void handleAction(ActionOptions option) {
        if(option == ActionOptions.add || option == ActionOptions.replace) {
            Boolean replace = option == ActionOptions.replace;
            Logger.info("Replace: " + replace.toString());
            _controller.addToPlaylist(_actionSelected,option == ActionOptions.replace);
        }
        _actionSelected = null;
    }

    protected Dialog onCreateDialog(int id) {
        return buildDialog();
    }

    public Dialog buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(ActionOptions.getCharSequence(),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                handleAction(ActionOptions.get(i));
            }
        });
        AlertDialog dialog = builder.create();
        return builder.create();
    }

    public void onArtistClick(View view) {
        Artist artist = (Artist) view.findViewById(R.id.artist_name).getTag(R.id.artist_item);
        if(artist.opened()) {
            artist.close();
        }else {
            artist.open();
        }
        _adapter.notifyDataSetChanged();
    }

    public void onAlbumClick(View view) {
        Album album = (Album) view.findViewById(R.id.album_name).getTag(R.id.album_item);
        if(album.opened()) {
            album.close();
        }else {
            album.open();
        }
        _adapter.notifyDataSetChanged();
    }

    public void onTrackClick(View view) {

    }
}