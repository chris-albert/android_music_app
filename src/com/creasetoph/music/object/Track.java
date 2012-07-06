package com.creasetoph.music.object;

import com.creasetoph.music.util.HttpUtil;

/**
 * Track object has a track name
 */
public class Track extends MusicItem implements Comparable<Track> {

    //Album name
    private Album _album;

    /**
     * Constructor sets the name for the track, album, and artist
     *
     * @param album  Album name
     * @param name   Track name
     */
    public Track( Album album, String name) {
        setAlbum(album);
        setName(name);
    }

    /**
     * Getter for the album name
     *
     * @return Album name
     */
    public Album getAlbum() {
        return _album;
    }

    /**
     * Setter for the album name
     *
     * @param album Album name
     */
    public void setAlbum(Album album) {
        _album = album;
    }

    /**
     * Compares one track to another
     * @param track Track to compare to
     * @return 0 if the strings are equal,
     * a negative integer if this string is before the specified string,
     * or a positive integer if this string is after the specified string.
     */
    public int compareTo(Track track) {
        return getName().compareTo(track.getName());
    }

    public String getPath() {
        return getAlbum().getArtist().getLibrary().getPath(this);
    }
}
