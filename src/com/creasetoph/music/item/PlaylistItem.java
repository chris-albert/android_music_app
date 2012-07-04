package com.creasetoph.music.item;

/**
 * Holds items for the playlist list view
 */
public class PlaylistItem implements Comparable<PlaylistItem> {

    //Track name
    private String _track;

   /**
     * Constructor sets the track name
     * @param track The track name
     */
    public PlaylistItem(String track) {
        setTrack(track);
    }

    /**
     * Sets the track name
     * @param track The track name
     */
    public void setTrack(String track) {
        _track = track;
    }

    /**
     * Gets the track name
     * @return The track name
     */
    public String getTrack() {
        return _track;
    }

    /**
     * Comparator so we can sort playlist items
     * @param another Other PlaylistItem to compare to
     * @return 0 if the strings are equal, a negative integer if this string is
     *         before the specified string, or a positive integer if this string is
     *         after the specified string.
     */
    public int compareTo(PlaylistItem another) {
        return _track.compareTo(another.getTrack());
    }
}
