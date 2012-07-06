package com.creasetoph.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.creasetoph.music.item.LibraryItem;
import com.creasetoph.music.model.MusicModelFactory;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.object.*;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.model.MusicModel;

public class LibraryController {

    private ArrayList<LibraryItem> _libraryList        = new ArrayList<LibraryItem>();
    private MusicModel             _model              = null;
    private PlaylistController     _playlistController = null;

    public LibraryController(MusicModelFactory.Type type) {
        _model = MusicModelManager.getInstance().getMusicModel(type);
        _playlistController = PlaylistController.getInstance();
        setUpLibraryList();
    }

    private void setUpLibraryList() {
        for (Artist artist : _model.getLibrary().getArtistList()) {
            _libraryList.add(new LibraryItem(LibraryItem.Type.Artist, artist.getName()));
        }
        Collections.sort(_libraryList);
    }

    public void setLibrary(ArrayList<LibraryItem> libraryList) {
        _libraryList = libraryList;
    }

    public ArrayList<LibraryItem> getLibrary() {
        return _libraryList;
    }

    public Library getModelLibrary() {
        return _model.getLibrary();
    }

    public int getLibrarySize() {
        return _libraryList.size();
    }

    public boolean libraryEmpty() {
        return getLibrarySize() == 0;
    }

    public void addArtistToPlaylist(Artist artist) {
        Logger.info("Adding artist (" + artist.getName() + ") to playlist");
        for(Album album: artist.getAlbums()) {
            for(Track track: album.getTracks()) {
                _playlistController.addToPlaylist(track);
            }
        }
    }

    public void addAlbumToPlaylist(Album album) {
        Logger.info("Adding album (" + album.getName() + ") to playlist");
        for(Track track: album.getTracks()) {
            _playlistController.addToPlaylist(track);
        }
    }

    public void addTrackToPlaylist(Track track) {
        Logger.info("Adding track (" + track.getName() + ") to playlist");
        _playlistController.addToPlaylist(track);
    }

    public void addToPlaylist(MusicItem item,boolean replace) {
        if(replace) {
            _playlistController.stop();
            clearPlaylist();
        }
        if(item instanceof Artist) {
            addArtistToPlaylist((Artist) item);
        }else if(item instanceof Album) {
            addAlbumToPlaylist((Album) item);
        }else if(item instanceof Track) {
            addTrackToPlaylist((Track) item);
        }
        if(replace) {
            _playlistController.playPause();
        }
    }

    public void clearPlaylist() {
        _playlistController.clearPlaylist();
    }

}
