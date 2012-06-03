package com.creasetoph.music.object;

/**
 * Track object has a track name
 */
public class Track extends MusicItem implements Comparable<Track> {

    //Artist name
    private String _artist = "";
    //Album name
    private String _album = "";

    /**
     * Constructor sets the name for the track, album, and artist
     *
     * @param artist Artist name
     * @param album  Album name
     * @param name   Track name
     */
    public Track(String artist, String album, String name) {
        setArtist(artist);
        setAlbum(album);
        setName(name);
    }

    /**
     * Getter for the artist name
     *
     * @return Artist name
     */
    public String getArtist() {
        return _artist;
    }

    /**
     * Setter for the artist name
     *
     * @param artist The artist name
     */
    public void setArtist(String artist) {
        _artist = artist;
    }

    /**
     * Getter for the album name
     *
     * @return Album name
     */
    public String getAlbum() {
        return _album;
    }

    /**
     * Setter for the album name
     *
     * @param album Album name
     */
    public void setAlbum(String album) {
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
}
