package com.creasetoph.music.object;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.creasetoph.music.util.Logger;

public class Sound {

    private MediaPlayer _mediaPlayer;
    private int _streamType = AudioManager.STREAM_MUSIC;

    public Sound() {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setAudioStreamType(_streamType);
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        _mediaPlayer.setOnCompletionListener(listener);
    }

    public void load(String url) {
        try {
            _mediaPlayer.reset();
            _mediaPlayer.setDataSource(url);
            _mediaPlayer.prepare();
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    public void play() {
        play(0);
    }

    public void play(int seek) {
        _mediaPlayer.seekTo(seek);
        _mediaPlayer.start();
    }

    public void resume() {
        _mediaPlayer.start();
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    public void stop() {
        _mediaPlayer.stop();
    }
}