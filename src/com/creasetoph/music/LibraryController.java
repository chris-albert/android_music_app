package com.creasetoph.music;

import java.util.ArrayList;

public class LibraryController {
	
	private ArrayList<LibraryItem> _libraryList = new ArrayList<LibraryItem>();
	private MediaData _mediaData;
	
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
	
	private LibraryController(){}
	
	public void setLibrary(String json) {
	    _mediaData = MediaData.getInstance(json);
	    _libraryList = getList(_mediaData.get(),ARTIST);
	}  
	
	private ArrayList<LibraryItem> getList(ArrayList<String> items,String type) {
	    ArrayList<LibraryItem> list = new ArrayList<LibraryItem>();
	    for(String key: items) {
            list.add(new LibraryItem(type,key));
        }
	    return list;
	}
	
	public void setLibrary(ArrayList<LibraryItem> libraryList) {
	    _libraryList = libraryList;
	}
	
	public ArrayList<LibraryItem> getLibrary() { 
	    return _libraryList;
	}
	
	public void addAlbumToPlaylist(String selection,int index) {
		String artist = "";
		for(int i = index;i > 0;i--) {
			if(_libraryList.get(i).getType() == "artist") {
				artist = _libraryList.get(i).getValue();
				break;
			}
		}
		PlaylistController pc = PlaylistController.getInstance();
	    ArrayList<LibraryItem> list = getList(_mediaData.get(artist,selection),TRACK);
		for(LibraryItem item : list) {
			pc.addToPlaylist(artist,selection,item.getValue());
		}
		pc.playPause();
	}
	
	public void removeAlbums(String artist,int index) {
		int start = index + 1;
		for(int i = start;i < _libraryList.size();i++) {
			if(_libraryList.get(start).getType() == "album") {
				_libraryList.remove(start);
			}else {
				break;
			}
		}
	}
	
	public void addAlbums(String artist,int index) {
	    ArrayList<String> albums = _mediaData.get(artist);
		_libraryList.addAll(index + 1,getList(albums,ALBUM));
	}
}
