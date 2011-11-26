package com.creasetoph.music.model;

import java.io.File;

import android.os.AsyncTask;

import com.creasetoph.music.objects.Album;
import com.creasetoph.music.objects.Artist;
import com.creasetoph.music.objects.Library;
import com.creasetoph.music.objects.Track;

public class LocalMusicModel extends MusicModel {
	
	private static final String BASE_MEDIA_PATH = "/sdcard/Music";
	private static LocalMusicModel _instance = null;
	
	private Library _library = null;
	
	private LocalMusicModel() {
		super();
		_library = new Library();
		fetchDiskData();
	}
	
	public static LocalMusicModel getInstance() {
		if(_instance == null) {
			 _instance = new LocalMusicModel();
		}
		return _instance;
	}

	private void fetchDiskData() {
		new ParseDiskTask().execute(BASE_MEDIA_PATH);
	}
	
	private void parseDisk(String path) {
		File[] files = new File(path).listFiles();
		for(File file: files) {
			if(file.isDirectory()) {
				String artist = file.getName();
				Artist artistObject = new Artist(artist);
				File[] artists = new File(path + "/" + artist).listFiles();
				for(File artistFile: artists){
					if(artistFile.isDirectory()) {
						String album = artistFile.getName();
						Album albumObject = new Album(artist,album);
						artistObject.addAlbum(albumObject);
						File[] tracks = new File(path + "/" + artist + "/" + album).listFiles();
						for(File trackFile: tracks) {
							if(trackFile.isFile()) {
								String track = trackFile.getName();
								Track trackObject = new Track(artist,album,track);
								albumObject.addTrack(trackObject);
							}
						}
					}
				}
				_library.addArtist(artistObject);
			}
		}
	}
	
	private class ParseDiskTask extends AsyncTask<String,Void,Void> {
        
        protected Void doInBackground(String... params) {
        	parseDisk(params[0]);
        	return null;
        }
        
        protected void onPostExecute() {
            
        }
    }

	@Override
	public Library getLibrary() {
		// TODO Auto-generated method stub
		return null;
	}
}
