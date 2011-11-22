package com.creasetoph.music;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class PlaylistController {
    
    private static PlaylistController _instance = null;
    private static String _baseUrl = "http://music.creasetoph.com:1338/music/stream";
    private static boolean repeat_album = true;
	
	private Playlist _playlist;
	private Sound    _sound;
	private Boolean  _playing;
	private Boolean  _paused;
	
	public static PlaylistController getInstance() {
		if(_instance == null) {
			_instance = new PlaylistController();
		}
		return _instance;
	}
	
	private PlaylistController() {
		_playlist = new Playlist();
		_sound    = new Sound();
		_playing  = false;
        _paused   = false;
		registerSoundListeners();
	}
	
	private void registerSoundListeners() {
		_sound.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				next();
			}
		});
	}
	
	public void addToPlaylist(String artist,String album,String track) {
		_playlist.addToPlaylist(artist,album,track);
	}
	
	public void playPause() {
		if(_playing) {
			pause();
		}else {
			play();
		}
	}
	
	private void play() {
		if(_paused) {
			_sound.resume();
			_paused = false;
			_playing = true;
		}else {
			String path = _playlist.getCurrentPlaylistTrackPath();
			if(path != null) {
				_sound.load(_baseUrl + path);
				_sound.play();
				_paused = false;
				_playing = true;
			}
		}
	}
	
	private void pause() {
		if(_playing) {
			_sound.pause();
			_paused = true;
			_playing = false;
		}
	}
	
	public void stop() {
		if(_playing) {
			_sound.stop();
			_playing = false;
			_paused = false;
		}
	}
	
	public void next() {
		stop();
		if(_playlist.getCurrentTrackIndex() < _playlist.size() - 1) {
			_playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() + 1);
			play();
		}else if(repeat_album){
			_playlist.setCurrentTrackIndex(0);
			play();
		}
	}
	
	public void prev() { 
	    stop();
	    if(_playlist.getCurrentTrackIndex() > 0) {
	        _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() - 1);
	        play();
	    }else if(repeat_album){
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
	
	public ArrayList<PlaylistItem> getPlaylistItems() {
	    ArrayList<PlaylistTrack> tracks = _playlist.getPlaylistTracks();
	    ArrayList<PlaylistItem> items = new ArrayList<PlaylistItem>();
	    for(PlaylistTrack track : tracks) {
	        items.add(new PlaylistItem(track.getTrack())); 
	    }
	    return items;
	}
}
