package com.creasetoph.music.model;

import java.util.Iterator;

import com.creasetoph.music.util.Preferences;
import org.json.JSONException;
import org.json.JSONObject;

import com.creasetoph.music.util.HttpUtil;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.object.Album;
import com.creasetoph.music.object.Artist;
import com.creasetoph.music.object.Library;
import com.creasetoph.music.object.Track;

public class NetworkMusicModel extends MusicModel {

    private static String json_url = null;
    private static NetworkMusicModel _instance;

    private Library _library = null;

    private NetworkMusicModel() {
        super();
        _library = new Library();
        json_url = Preferences.getString(Preferences.Name.json_url);
        fetchJson();
    }

    public static NetworkMusicModel getInstance() {
        if (_instance == null) {
            _instance = new NetworkMusicModel();
        }
        return _instance;
    }

    private void fetchJson() {
        try {
            String json = HttpUtil.httpGet(json_url);
            parseJson(json);
        }catch(Exception e) {
            Logger.info("Error fetching json from url: " + json_url);
        }
    }

    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            parseJsonObject(jsonObject);
        } catch (JSONException e) {
            Logger.log(e);
        }
    }

    private void parseJsonObject(JSONObject json) {
        for (Iterator<?> i = json.keys(); i.hasNext(); ) {
            String artist = i.next().toString();
            if(artist.equals(".DS_Store")) {
                continue;
            }
            Artist artistObject = new Artist(artist);
            try {
                JSONObject albumJsonObject = json.getJSONObject(artist);
                for (Iterator<?> j = albumJsonObject.keys(); j.hasNext(); ) {
                    String album = j.next().toString();
                    Album albumObject = new Album(artist, album);
                    artistObject.addAlbum(albumObject);
                    JSONObject trackJsonObject = albumJsonObject.getJSONObject(album);
                    for (Iterator<?> l = trackJsonObject.keys(); l.hasNext(); ) {
                        String track = l.next().toString();
                        Track trackObject = new Track(artist, album, track);
                        albumObject.addTrack(trackObject);
                    }
                    albumObject.sortTracks();
                }
            } catch (JSONException e) {
                Logger.error("Something funny happend parsing the json: " + e.getMessage());
            }
            artistObject.sortAlbums();
            _library.addArtist(artistObject);
        };
    }

    public Library getLibrary() {
        return _library;
    }
}
