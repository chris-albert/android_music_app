package com.creasetoph.music.objects;

import java.util.ArrayList;

import com.creasetoph.music.Logger;

public class Library {

	private ArrayList<Artist> _artists = new ArrayList<Artist>();
	
	public void addArtist(Artist artist) {
		_artists.add(artist);
	}
	
	public void addArtist(String artist) {
		addArtist(new Artist(artist));
	}
	
	public ArrayList<Artist> getArtistList() {
		return _artists;
	}
	
	public ArrayList<Album> getAlbumList(String artistName) {
		for(Artist artist: _artists) {
			if(artist.getName().equals(artistName)) {
				return artist.getAlbums();
			}
		}
		return null;
	}
	
	public ArrayList<Track> getTrackList(String artist,String album) {
		ArrayList<Album> albums = getAlbumList(artist);
		return albums.get(albums.indexOf(album)).getTracks();
	}
	
	public String toString() {
		String str = "";
		for(Artist artist: _artists) {
			str += "Artist: " + artist.getName() + "\n";
			for(Album album: artist.getAlbums()) {
				str += "\tAlbum: " + album.getName() + "\n";
				for(Track track: album.getTracks()) {
					str += "\t\tTrack: " + track.getName() + "\n";
				}
			}
		}
		return str;
	}
}
