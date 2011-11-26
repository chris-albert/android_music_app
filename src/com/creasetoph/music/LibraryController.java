package com.creasetoph.music;

import java.util.ArrayList;
import java.util.Collections;

import com.creasetoph.music.model.MusicModel;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.objects.Album;
import com.creasetoph.music.objects.Artist;
import com.creasetoph.music.objects.Library;
import com.creasetoph.music.objects.Track;

public class LibraryController {
	
	private ArrayList<LibraryItem> _libraryList = new ArrayList<LibraryItem>();
	private MusicModel _model = null;
	//private MediaData _mediaData;
	
	private static LibraryController _instance = null;
	
	public static final String ARTIST = "artist";
	public static final String ALBUM  = "album";
	public static final String TRACK  = "track";
	
	public static LibraryController getInstance() {
		if(_instance == null) {
			_instance = new LibraryController();
		}
		return _instance;
	}
	
	private LibraryController(){
		_model = MusicModelManager.fetchModel();
		setUpLibraryList();
	}
	
	private void setUpLibraryList() {
		for(Artist artist: _model.getLibrary().getArtistList()) {
			_libraryList.add(new LibraryItem(ARTIST,artist.getName()));
		}
		Collections.sort(_libraryList);
	}
	
	private ArrayList<LibraryItem> getList(ArrayList<String> items,String type) {
		return _libraryList;
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
	
	public void addAlbumToPlaylist(String selection,int index) {
		String artist = "";
		for(int i = index;i >= 0;i--) {
			if(_libraryList.get(i).getType() == "artist") {
				artist = _libraryList.get(i).getValue();
				break;
			}
		}
		PlaylistController pc = PlaylistController.getInstance();
		Logger.log("Artist: " + artist + " Selection: " + selection);
		for(Track track: _model.getLibrary().getTrackList(artist, selection)) {
			pc.addToPlaylist(artist,selection,track.getName());
		}
		pc.playPause();
	}
	
	public void removeAlbums(String artist,int index) {
		int start = index + 1;
		for(int i = start;i <= _libraryList.size();i++) {
			if(_libraryList.get(start).getType() == "album") {
				_libraryList.remove(start);
			}else {
				break;
			}
		}
	}
	
	public void addAlbums(String artist,int index) {
		Logger.log("Artist selected: " + artist);
		ArrayList<LibraryItem> albums = new ArrayList<LibraryItem>();
		for(Album album: _model.getLibrary().getAlbumList(artist)) {
			albums.add(new LibraryItem(ALBUM,album.getName()));
		}
		_libraryList.addAll(index + 1,albums);
	}
}
