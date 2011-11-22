package com.creasetoph.music;

public class PlayerController {
    
    private static PlayerController _instance = null;
    
    private PlaylistController _playlistController = null;
    
    public static PlayerController getInstance() {
        if(_instance == null) {
            _instance = new PlayerController();
        }
        return _instance;
    }
    
    private PlayerController() {
        _playlistController = PlaylistController.getInstance();
    }
    
    public void playPause() {
        _playlistController.playPause();
    }
 
    public void stop() {
        _playlistController.stop();
    }
    
    public void prev() {
        _playlistController.prev();
    }
    
    public void next() {
        _playlistController.next();
    }
    
    public boolean isPlaying() {
        return _playlistController.isPlaying();
    }
}
