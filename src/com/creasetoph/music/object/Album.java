package com.creasetoph.music.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Album object has name and holds tracks
 */
public class Album extends MusicItem implements Comparable<Album> {

    //Tracks for this album
    private ArrayList<Track> _tracks = new ArrayList<Track>();
    //Artist name
    private String _artist = "";

    /**
     * Constructor sets the artist and album name
     *
     * @param artist Artist name
     * @param name   Album name
     */
    public Album(String artist, String name) {
        setArtist(artist);
        setName(name);
    }

    /**
     * Getter for artist name
     *
     * @return Artist name
     */
    public String getArtist() {
        return _artist;
    }

    /**
     * Setter for artist name
     *
     * @param artist Artist name
     */
    public void setArtist(String artist) {
        _artist = artist;
    }

    /**
     * Adds a track to this album
     *
     * @param track Track to add to albums' track list
     */
    public void addTrack(Track track) {
        _tracks.add(track);
    }

    /**
     * Gets all the tracks for this album
     *
     * @return List of tracks for this album
     */
    public ArrayList<Track> getTracks() {
        return _tracks;
    }

    /**
     * Sorts the tracks for the album in alphabetic order
     */
    public void sortTracks() {
        Collections.sort(_tracks);
    }

    /**
     * Searches the list of tracks for a specified track
     *
     * @param trackName Track to find in albums' track list
     * @return Track object representing the track name
     */
    public Track getTrack(String trackName) {
        for (Track track : _tracks) {
            if (track.getName().equals(trackName)) {
                return track;
            }
        }
        return null;
    }

    /**
     * Compares one album to another
     * @param album Album to compare to
     * @return 0 if the strings are equal,
     * a negative integer if this string is before the specified string,
     * or a positive integer if this string is after the specified string.
     */
    public int compareTo(Album album) {
        return getName().compareTo(album.getName());
    }
}
