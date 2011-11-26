package com.creasetoph.music.objects;

import java.util.ArrayList;

public class Album {
	
	private ArrayList<Track> _tracks = new ArrayList<Track>();
	private String _name = "";
	private String _artist = "";
	
	public Album(String artist,String name) {
		setArtist(artist);
		setName(name);
	}
	
	public String getArtist() {
		return _artist;
	}
	
	public void setArtist(String artist) {
		_artist = artist;
	}

	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void addTrack(Track track) {
		_tracks.add(track);
	}
	
	public ArrayList<Track> getTracks() {
		 return _tracks;
	}
}
