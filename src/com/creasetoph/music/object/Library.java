package com.creasetoph.music.object;

import com.creasetoph.music.model.MusicModel;
import com.creasetoph.music.model.MusicModelFactory;
import com.creasetoph.music.model.MusicModelManager;

import java.util.ArrayList;

/**
 * Holds all the artist for a certain library
 */
public class Library {

    //Albums in this library
    private ArrayList<Artist> _artists = new ArrayList<Artist>();
    private MusicModel _musicModel;

    public Library(MusicModel musicModel) {
        _musicModel = musicModel;
    }

    /**
     * Adds an artist to the library
     *
     * @param artist Artist to add to library
     */
    public void addArtist(Artist artist) {
        _artists.add(artist);
    }

    /**
     * Gets the list of all artist in this library
     *
     * @return The list of all artist in this library
     */
    public ArrayList<Artist> getArtistList() {
        return _artists;
    }

    /**
     * Gets an artist object for an artist name
     *
     * @param artistName Artist name to get object for
     * @return Artist object
     */
    public Artist getArtist(String artistName) {
        for (Artist artist : _artists) {
            if (artist.getName().equals(artistName)) {
                return artist;
            }
        }
        return null;
    }

    public String getPath(Track track) {
        return _musicModel.getPath(track);
    }

    /**
     * Pretty prints this library (Can be very long!)
     *
     * @return String version of whole library
     */
    public String toString() {
        String str = "";
        for (Artist artist : _artists) {
            str += "Artist: " + artist.getName() + "\n";
            for (Album album : artist.getAlbums()) {
                str += "\tAlbum: " + album.getName() + "\n";
                for (Track track : album.getTracks()) {
                    str += "\t\tTrack: " + track.getName() + "\n";
                }
            }
        }
        return str;
    }
}
