package com.creasetoph.music.object;

import com.creasetoph.music.PlaylistTrack;

import java.util.ArrayList;

public class Playlist {
	
	private ArrayList<PlaylistTrack> _playlistTracks;
	private int _currentTrackIndex = 0;
	
	public Playlist() {
		_playlistTracks = new ArrayList<PlaylistTrack>();
	}
	
	public void addToPlaylist(String artist,String album,String track) {
		_playlistTracks.add(new PlaylistTrack(artist,album,track));
	}

	public void setCurrentTrackIndex(int index) {
		_currentTrackIndex = index;
	}

	public int getCurrentTrackIndex() {
		return _currentTrackIndex;
	}
	
	public int size() {
		return _playlistTracks.size();
	}
	
	public void clearPlaylist() {
	    _playlistTracks.clear();
	    setCurrentTrackIndex(0);
	}
	
	public PlaylistTrack getCurrentPlaylistTrack() {
		return _playlistTracks.get(getCurrentTrackIndex());
	}
	
	public String getCurrentPlaylistTrackPath() {
		PlaylistTrack playlistTrack = getCurrentPlaylistTrack();
		if(playlistTrack != null) {
			return playlistTrack.toString();
		}
		return null;
	}
	
	public ArrayList<PlaylistTrack> getPlaylistTracks() {
	    return _playlistTracks;
	}
}
