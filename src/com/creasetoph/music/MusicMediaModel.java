package com.creasetoph.music;

import java.util.ArrayList;

public class MusicMediaModel {
	
	private static MusicMediaModel _instance = null;
	
	private MusicMediaModel() {
		
	}
	
	public static MusicMediaModel getInstance() {
		if(_instance == null) { 
			_instance = new MusicMediaModel();
		}
		return _instance;
	}
	
	public ArrayList<String> getAllArtists() {
		
		return null;
	}
	
	public ArrayList<String> getAlbums(String artist) {
		
		return null;
	}

	public ArrayList<String> getAlbums() {
		return getAlbums("");
	}
	
	public ArrayList<String> getTracks(String artist, String album) {
		
		return null;
	}
}
