package com.creasetoph.music;

public class PlaylistItem implements Comparable<PlaylistItem>{
    
    private String _track;
    
    public PlaylistItem(String track) {
        setTrack(track);
    }

    public void setTrack(String track) {
        _track = track;
    }

    public String getTrack() {
        return _track;
    }

    public int compareTo(PlaylistItem another) {
        return _track.compareTo(another.getTrack());
    }
}
