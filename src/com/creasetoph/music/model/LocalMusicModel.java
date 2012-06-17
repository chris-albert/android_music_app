package com.creasetoph.music.model;

import java.io.File;

import android.os.AsyncTask;

import com.creasetoph.music.object.Album;
import com.creasetoph.music.object.Artist;
import com.creasetoph.music.object.Library;
import com.creasetoph.music.object.Track;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.util.Preferences;

public class LocalMusicModel extends MusicModel {

    private static String _mediaPath = null;
    private static LocalMusicModel _instance = null;

    private Library _library = null;

    public LocalMusicModel() {
        super();
        _library = new Library();
        _mediaPath = Preferences.getString(Preferences.Name.local_path);
        fetchDiskData();
    }

    public static LocalMusicModel getInstance() {
        if (_instance == null) {
            _instance = new LocalMusicModel();
        }
        return _instance;
    }

    private void fetchDiskData() {
        parseDisk(_mediaPath);
    }

    private void parseDisk(String path) {
        File[] files = new File(path).listFiles();
        if(files == null) {
            Logger.error("Error finding path " + path);
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                String artist = file.getName();
                Artist artistObject = new Artist(artist);
                File[] artists = new File(path + "/" + artist).listFiles();
                for (File artistFile : artists) {
                    if (artistFile.isDirectory()) {
                        String album = artistFile.getName();
                        Album albumObject = new Album(artist, album);
                        artistObject.addAlbum(albumObject);
                        File[] tracks = new File(path + "/" + artist + "/" + album).listFiles();
                        for (File trackFile : tracks) {
                            if (trackFile.isFile()) {
                                String track = trackFile.getName();
                                Track trackObject = new Track(artist, album, track);
                                albumObject.addTrack(trackObject);
                            }
                        }
                    }
                }
                _library.addArtist(artistObject);
            }
        }
    }

    @Override
    public Library getLibrary() {
        return _library;
    }
}
