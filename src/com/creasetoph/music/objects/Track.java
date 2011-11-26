package com.creasetoph.music.objects;

public class Track {

	private String _name   = "";
	private String _artist = "";
	private String _album  = "";
	
	public Track(String artist,String album,String name) {
		setArtist(artist);
		setAlbum(album);
		setName(name);
	}
	
	public String getArtist() {
		return _artist;
	}
	
	public void setArtist(String artist) {
		_artist = artist;
	}
	
	public String getAlbum() {
		return _album;
	}
	
	public void setAlbum(String album) {
		_album = album;
	}

	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
}
