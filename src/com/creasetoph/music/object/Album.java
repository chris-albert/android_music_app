package com.creasetoph.music.object;

import java.util.ArrayList;

/**
 * Album object has name and holds tracks
 */
public class Album {

    //Tracks for this album
	private ArrayList<Track> _tracks = new ArrayList<Track>();
    //Album name
	private String _name = "";
    //Artist name
	private String _artist = "";

    /**
     * Constructor sets the artist and albim name
     * @param artist Artist name
     * @param name Album name
     */
	public Album(String artist,String name) {
		setArtist(artist);
		setName(name);
	}

    /**
     * Getter for artist name
     * @return Artist name
     */
	public String getArtist() {
		return _artist;
	}

    /**
     * Setter for artist name
     * @param artist Artist name
     */
	public void setArtist(String artist) {
		_artist = artist;
	}

    /**
     * Getter for album name
     * @return Album name
     */
	public String getName() {
		return _name;
	}

    /**
     * Setter for album name
     * @param name Album name
     */
	public void setName(String name) {
		_name = name;
	}

    /**
     * Adds a track to this album
     * @param track Track to add to albums' track list
     */
	public void addTrack(Track track) {
		_tracks.add(track);
	}

    /**
     * Gets all the tracks for this album
     * @return List of tracks for this album
     */
	public ArrayList<Track> getTracks() {
		 return _tracks;
	}

    /**
     * Searches the list of tracks for a specified track
     * @param trackName Track to find in albums' track list
     * @return Track object representing the track name
     */
    public Track getTrack(String trackName) {
        for(Track track: _tracks) {
            if(track.getName().equals(trackName)) {
                return track;
            }
        }
        return null;
    }

    /**
     * Pretty prints this object
     * @return The album name
     */
    public String toString() {
        return getName();
    }
}
