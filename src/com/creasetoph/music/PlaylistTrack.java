package com.creasetoph.music;

import com.creasetoph.music.util.HttpUtil;

public class PlaylistTrack {

    private String _artist;
    private String _album;
    private String _track;

    public static final String SEPARATOR = "/";

    public PlaylistTrack() {
        _artist = "";
        _album = "";
        _track = "";
    }

    public PlaylistTrack(String artist, String album, String track) {
        _artist = artist;
        _album = album;
        _track = track;
    }

    public String getArtist() {
        return _artist;
    }

    public String getAlbum() {
        return _album;
    }

    public String getTrack() {
        return _track;
    }

    public String toString() {
        return SEPARATOR + HttpUtil.encode(_artist) + SEPARATOR
                + HttpUtil.encode(_album) + SEPARATOR + HttpUtil.encode(_track);
    }
}
