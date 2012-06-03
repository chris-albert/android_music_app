package com.creasetoph.music.object;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.creasetoph.music.util.Logger;

/**
 * Wrapper for the android media player
 */
public class Sound {

    //Android media player instance
    private MediaPlayer _mediaPlayer;
    //The audio stream type for the media player
    private int _streamType = AudioManager.STREAM_MUSIC;

    /**
     * Constructor for sound object
     * Initializes and sets up MediaPlayer
     */
    public Sound() {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setAudioStreamType(_streamType);
    }

    /**
     * Sets the OnCompletionListener for the media player
     * This is what gets called when a song finishes
     * @param listener The listener to be called on sound complete
     */
    public void setOnCompletionListener(OnCompletionListener listener) {
        _mediaPlayer.setOnCompletionListener(listener);
    }

    /**
     * Loads an url for streaming through the media player
     * @param url Url to stream media from
     */
    public void load(String url) {
        try {
            _mediaPlayer.reset();
            _mediaPlayer.setDataSource(url);
            _mediaPlayer.prepare();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    /**
     * Wrapper for playing media from the start
     */
    public void play() {
        play(0);
    }

    /**
     * Plays media loaded in the media player
     * @param seek Millisecond to start at
     */
    public void play(int seek) {
        _mediaPlayer.seekTo(seek);
        _mediaPlayer.start();
    }

    /**
     * Resumes media player operations
     */
    public void resume() {
        _mediaPlayer.start();
    }

    /**
     * Paused media player
     */
    public void pause() {
        _mediaPlayer.pause();
    }

    /**
     * Stops media player
     */
    public void stop() {
        _mediaPlayer.stop();
    }
}