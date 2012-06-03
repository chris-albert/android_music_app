package com.creasetoph.music.object;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Artist object has a name and holds albums
 */
public class Artist {

    //Albums for this artist
    private ArrayList<Album> _albums = new ArrayList<Album>();
    //Artist name
    private String _name = "";

    /**
     * Constructor sets name for artist
     *
     * @param name Artist name
     */
    public Artist(String name) {
        setName(name);
    }

    /**
     * Getter for artist name
     *
     * @return Artist name
     */
    public String getName() {
        return _name;
    }

    /**
     * Setter for artist name
     *
     * @param name Artist name
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * Adds an album to this artist
     *
     * @param album Album to add to artist's album list
     */
    public void addAlbum(Album album) {
        _albums.add(album);
    }

    /**
     * Gets all the albums for this artist
     *
     * @return List of albums
     */
    public ArrayList<Album> getAlbums() {
        return _albums;
    }

    /**
     * Sorts the albums for an artist in alphabetic order
     */
    public void sortAlbums() {
        Collections.sort(_albums);
    }

    /**
     * Searches the list of albums for specified album name
     *
     * @param albumName Album to find in artist's album list
     * @return Album object representing the album name
     */
    public Album getAlbum(String albumName) {
        for (Album album : _albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Pretty prints this object
     *
     * @return The artist name
     */
    public String toString() {
        return getName();
    }
}