package com.creasetoph.music.object;

/**
 * Track object has a track name
 */
public class Track {

    //Track name
	private String _name   = "";
    //Artist name
	private String _artist = "";
    //Album name
	private String _album  = "";

    /**
     * Constructor sets the name for the track, album, and artist
     * @param artist Artist name
     * @param album Album name
     * @param name Track name
     */
	public Track(String artist,String album,String name) {
		setArtist(artist);
		setAlbum(album);
		setName(name);
	}

    /**
     * Getter for the artist name
     * @return Artist name
     */
	public String getArtist() {
		return _artist;
	}

    /**
     * Setter for the artist name
     * @param artist The artist name
     */
	public void setArtist(String artist) {
		_artist = artist;
	}

    /**
     * Getter for the album name
     * @return Album name
     */
	public String getAlbum() {
		return _album;
	}

    /**
     * Setter for the album name
     * @param album Album name
     */
	public void setAlbum(String album) {
		_album = album;
	}

    /**
     * Getter for the track name
     * @return Track name
     */
	public String getName() {
		return _name;
	}

    /**
     * Setter for the track name
     * @param name Track name
     */
	public void setName(String name) {
		_name = name;
	}

    /**
     * Pretty prints this object
     * @return The track name
     */
    public String toString() {
        return getName();
    }
}
