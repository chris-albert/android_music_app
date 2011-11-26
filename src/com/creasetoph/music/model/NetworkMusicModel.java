package com.creasetoph.music.model;

import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.creasetoph.music.HttpUtil;
import com.creasetoph.music.Logger;
import com.creasetoph.music.objects.Album;
import com.creasetoph.music.objects.Artist;
import com.creasetoph.music.objects.Library;
import com.creasetoph.music.objects.Track;

public class NetworkMusicModel extends MusicModel {
	
	private static final String BASE_JSON_URL = "http://music.creasetoph.com:1338/music/json";
	
	private static final String TEST_JSON = "" +
			"{" +
			"	artist1:{" +
			"		album1:{" +
			"			track1:'file'," +
			"			track2:'file'" +
			"		}," +
			"		album2:{" +
			"			track2:'file'," +
			"			track3:'file'" +
			"		}" +
			"	}," +
			"	artist2:{" +
			"		album1:{" +
			"			track1:'file'," +
			"			track2:'file'" +
			"		}," +
			"		album2:{" +
			"			track2:'file'," +
			"			track3:'file'" +
			"		}" +
			"	}" +
			"}";

	private static NetworkMusicModel _instance; 
	
	private Library _library = null;
	
	private NetworkMusicModel() {
		super();
		_library = new Library();
		fetchJson();
	}
	
	public static NetworkMusicModel getInstance() {
		if(_instance == null) { 
			_instance = new NetworkMusicModel();
		}
		return _instance;
	}
	
	private void fetchJson() {
		String json = HttpUtil.httpGet(BASE_JSON_URL);
		//json = TEST_JSON;
		if(json != null) {
			parseJson(json);
		}else {
			Logger.log("Error fetching json");
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
        for(Iterator<?> i = json.keys(); i.hasNext();) {
            String artist = i.next().toString();
            Artist artistObject = new Artist(artist);
            try {
                JSONObject albumJsonObject = json.getJSONObject(artist);
                for(Iterator<?> j = albumJsonObject.keys(); j.hasNext();) {
                	String album = j.next().toString();
                	Album albumObject = new Album(artist,album);
                	artistObject.addAlbum(albumObject);
                	JSONObject trackJsonObject = albumJsonObject.getJSONObject(album);
                	for(Iterator<?> l = trackJsonObject.keys();l.hasNext();) {
                		String track = l.next().toString();
                		Track trackObject = new Track(artist,album,track);
                		albumObject.addTrack(trackObject);
                	}
                }
            }catch(JSONException e) {
                //map.put(key,"file");
            }
            _library.addArtist(artistObject);
        }; 
    }

	public Library getLibrary() {
		return _library;
	}
}
