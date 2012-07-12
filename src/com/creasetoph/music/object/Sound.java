package com.creasetoph.music.object;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.creasetoph.music.util.Logger;

import java.io.IOException;

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
    public Sound(MediaPlayer.OnPreparedListener onPreparedListener) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setAudioStreamType(_streamType);
        _mediaPlayer.setOnPreparedListener(onPreparedListener);
    }

    /**
     * Sets the OnCompletionListener for the media player
     * This is what gets called when a song finishes
     * @param listener The listener to be called on sound complete
     */
    public void setOnCompletionListener(OnCompletionListener listener) {
        _mediaPlayer.setOnCompletionListener(listener);
    }

    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener listener) {
        _mediaPlayer.setOnBufferingUpdateListener(listener);
    }

    /**
     * Loads an url for streaming through the media player
     * @param url Url to stream media from
     */
    public void load(String url) {
        _mediaPlayer.reset();
        try {
            _mediaPlayer.setDataSource(url);
            _mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Logger.error("IOException: " + e.getMessage());
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

    public int getCurrentPosition() {
        return _mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return _mediaPlayer.getDuration();
    }

    public void getLoaded() {
    }
}