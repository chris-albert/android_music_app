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
        _library = new Library(this);
        _mediaPath = Preferences.getString(Preferences.Name.local_path);
        fetchDiskData();
    }

    public static LocalMusicModel getInstance() {
        if (_instance == null) {
            _instance = new LocalMusicModel();
        }
        return _instance;
    }

    public String getPath(Track track) {
        return _mediaPath  + "/" +
                track.getAlbum().getArtist().toString()+ "/" +
                track.getAlbum().toString() + "/" +
                track.toString();
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
                Artist artistObject = new Artist(_library,artist);
                artistObject.setSize(file.getTotalSpace());
                artistObject.setPath(file.getAbsolutePath());
                File[] artists = new File(path + "/" + artist).listFiles();
                for (File artistFile : artists) {
                    if (artistFile.isDirectory()) {
                        String album = artistFile.getName();
                        Album albumObject = new Album(artistObject, album);
                        albumObject.setSize(artistFile.getTotalSpace());
                        albumObject.setPath(artistFile.getAbsolutePath());
                        artistObject.addAlbum(albumObject);
                        File[] tracks = new File(path + "/" + artist + "/" + album).listFiles();
                        for (File trackFile : tracks) {
                            if (trackFile.isFile()) {
                                String track = trackFile.getName();
                                Track trackObject = new Track(albumObject, track);
                                trackObject.setSize(trackFile.getTotalSpace());
                                trackObject.setPath(trackFile.getAbsolutePath());
                                albumObject.addTrack(trackObject);
                                trackFile.getTotalSpace();
                            }
                        }
                        albumObject.sortTracks();
                    }
                }
                artistObject.sortAlbums();
                _library.addArtist(artistObject);
            }
        }
    }

    @Override
    public Library getLibrary() {
        return _library;
    }
}
