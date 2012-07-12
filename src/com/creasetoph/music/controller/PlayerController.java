package com.creasetoph.music.controller;

import java.util.concurrent.TimeUnit;

public class PlayerController {

    private static PlayerController _instance = null;

    private PlaylistController _playlistController = null;

    public static PlayerController getInstance() {
        if (_instance == null) {
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

    public int getProgress() {
        Double percentage;
        percentage = (((double) _playlistController.getCurrentPosition())/_playlistController.getDuration()) * 1000;
        return percentage.intValue();
    }

    public String getProgressMinute() {
        return String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(_playlistController.getCurrentPosition()));
    }

    public String getProgressSecond() {
        int millis = _playlistController.getCurrentPosition();
        return String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
