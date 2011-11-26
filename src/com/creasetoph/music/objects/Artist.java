package com.creasetoph.music.objects;

import java.util.ArrayList;

public class Artist {
	
	private ArrayList<Album> _albums = new ArrayList<Album>();
	private String _name = "";

	public Artist(String name) {
		setName(name);
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void addAlbum(Album album) {
		_albums.add(album);
	}
	
	public ArrayList<Album> getAlbums() {
		return _albums;
	}
	
	public String toString() {
		return getName();
	}
}