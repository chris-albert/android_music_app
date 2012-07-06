package com.creasetoph.music.object;

import java.util.ArrayList;

/**
 * Holder of playlist tracks and operations
 */
public class Playlist {

    //List of playlist tracks
    private ArrayList<Track> _playlistTracks;
    //Currently playing track
    private int _currentTrackIndex = 0;

    /**
     * Constructor initializes playlist array
     */
    public Playlist() {
        _playlistTracks = new ArrayList<Track>();
    }

    public void addToPlaylist(Track track) {
        _playlistTracks.add(track);
    }

    /**
     * Sets the current track index
     * @param index Current track index
     */
    public void setCurrentTrackIndex(int index) {
        _currentTrackIndex = index;
    }

    /**
     * Gets the current track index
     * @return Current track index
     */
    public int getCurrentTrackIndex() {
        return _currentTrackIndex;
    }

    /**
     * Gets the size of the playlist
     * @return The size of the playlist
     */
    public int size() {
        return _playlistTracks.size();
    }

    /**
     * Removes all playlist tracks from playlist
     */
    public void clearPlaylist() {
        _playlistTracks.clear();
        setCurrentTrackIndex(0);
    }

    /**
     * Gets the current playlist track
     * @return The current playlist track
     */
    public Track getCurrentPlaylistTrack() {
        return _playlistTracks.get(getCurrentTrackIndex());
    }

    /**
     * Gets the current playlist tracks encoded path
     * @return Encoded path the playlist track
     */
    public String getCurrentPlaylistTrackPath() {
        Track playlistTrack = getCurrentPlaylistTrack();
        if (playlistTrack != null) {
            return playlistTrack.getPath();
        }
        return null;
    }

    /**
     * Gets all playlist tracks
     * @return Array of playlist tracks
     */
    public ArrayList<Track> getPlaylistTracks() {
        return _playlistTracks;
    }
}
