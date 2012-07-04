package com.creasetoph.music.controller;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.creasetoph.music.object.Playlist;
import com.creasetoph.music.item.PlaylistItem;
import com.creasetoph.music.object.PlaylistTrack;
import com.creasetoph.music.object.Sound;
import com.creasetoph.music.object.Track;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.util.Preferences;

public class PlaylistController {

    private static PlaylistController _instance    = null;
    private static String             _baseUrl     = null;
    private static boolean            repeat_album = true;

    private Playlist _playlist;
    private Sound    _sound;
    private Boolean  _playing;
    private Boolean  _paused;
    private MediaPlayer.OnPreparedListener _onPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mediaPlayer) {
            _sound.play();
            _paused = false;
            _playing = true;
        }
    };

    public static PlaylistController getInstance() {
        if (_instance == null) {
            _instance = new PlaylistController();
        }
        return _instance;
    }

    private PlaylistController() {
        _playlist = new Playlist();
        _sound = new Sound(_onPreparedListener);
        _playing = false;
        _paused = false;
        _baseUrl = Preferences.getString(Preferences.Name.stream_url);
        registerSoundListeners();
    }

    private void registerSoundListeners() {
        _sound.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
    }

    public void addToPlaylist(String artist, String album, String track) {
        Logger.info("Adding to playlist(" + artist + "," + album + "," + track + ")");
        _playlist.addToPlaylist(artist, album, track);
    }

    public void addToPlaylist(Track track) {
        _playlist.addToPlaylist(track);
    }

    public void clearPlaylist() {
        _playlist.clearPlaylist();
    }

    public void playPause() {
        if (_playing) {
            pause();
        } else {
            play();
        }
    }

    private void play() {
        if (_paused) {
            _sound.resume();
            _paused = false;
            _playing = true;
        } else {
            String path = _playlist.getCurrentPlaylistTrackPath();
            if (path != null) {
                _sound.load(_baseUrl + path);
            }
        }
    }

    private void pause() {
        if (_playing) {
            _sound.pause();
            _paused = true;
            _playing = false;
        }
    }

    public void stop() {
        if (_playing) {
            _sound.stop();
            _playing = false;
            _paused = false;
        }
    }

    public void next() {
        stop();
        if (_playlist.getCurrentTrackIndex() < _playlist.size() - 1) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() + 1);
            play();
        } else if (repeat_album) {
            _playlist.setCurrentTrackIndex(0);
            play();
        }
    }

    public void prev() {
        stop();
        if (_playlist.getCurrentTrackIndex() > 0) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() - 1);
            play();
        } else if (repeat_album) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() - 1);
            play();
        }
    }

    public boolean isPlaying() {
        return _playing;
    }

    public int getCurrentTrack() {
        return _playlist.getCurrentTrackIndex();
    }

    public void selectTrack(int track) {
        stop();
        _playlist.setCurrentTrackIndex(track);
        play();
    }

    public ArrayList<PlaylistTrack> getPlaylistItems() {
        return _playlist.getPlaylistTracks();
    }
}
