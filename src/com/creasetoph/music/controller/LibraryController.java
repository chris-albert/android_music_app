package com.creasetoph.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.creasetoph.music.LibraryItem;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.model.MusicModel;
import com.creasetoph.music.object.Album;
import com.creasetoph.music.object.Artist;
import com.creasetoph.music.object.Track;

public class LibraryController {

    private ArrayList<LibraryItem> _libraryList        = new ArrayList<LibraryItem>();
    private MusicModel             _model              = null;
    private PlaylistController     _playlistController = null;

    private static LibraryController _instance = null;

    public static final String ARTIST = "artist";
    public static final String ALBUM  = "album";
    public static final String TRACK  = "track";

    public static LibraryController getInstance() {
        if (_instance == null) {
            _instance = new LibraryController();
        }
        return _instance;
    }

    private LibraryController() {
        _model = MusicModelManager.fetchModel();
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

    public int getLibrarySize() {
        return _libraryList.size();
    }

    public void addAlbumToPlaylist(String selection, int index) {
        String artist = "";
        for (int i = index; i >= 0; i--) {
            if (_libraryList.get(i).getType() == LibraryItem.Type.Artist) {
                artist = _libraryList.get(i).getValue();
                break;
            }
        }
        Logger.info("Artist: " + artist + ", Selection: " + selection);
        try {
            List<Track> tracks = _model.getLibrary().getArtist(artist).getAlbum(selection).getTracks();
            for (Track track : tracks) {
                _playlistController.addToPlaylist(artist, selection, track.getName());
            }
            _playlistController.playPause();
        } catch (NullPointerException npe) {
            Logger.log(npe);
        }
    }

    public void clearPlaylist() {
        _playlistController.clearPlaylist();
    }

    public void removeAlbums(String artist, int index) {
        int start = index + 1;
        for (int i = start; i <= _libraryList.size(); i++) {
            if (_libraryList.get(start).getType() == LibraryItem.Type.Album) {
                _libraryList.remove(start);
            } else {
                break;
            }
        }
    }

    public void addAlbums(String artist, int index) {
        Logger.info("Artist selected: " + artist);
        ArrayList<LibraryItem> albums = new ArrayList<LibraryItem>();
        try {
            for (Album album : _model.getLibrary().getArtist(artist).getAlbums()) {
                albums.add(new LibraryItem(LibraryItem.Type.Album, album.getName()));
            }
            _libraryList.addAll(index + 1, albums);
        } catch (NullPointerException npe) {
            Logger.log(npe);
        }
    }
}
