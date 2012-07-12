package com.creasetoph.music.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.creasetoph.music.util.Logger;
import com.creasetoph.music.controller.PlayerController;
import com.creasetoph.music.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerActivity extends Activity {

    private PlayerController _controller;
    private ProgressUpdater _progressUpdater = null;
    private SeekBar _seekBar;
    private TextView _progressPercent;
    private TextView _progressMinute;
    private TextView _progressSecond;
    final Handler _handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateProgress();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _controller = PlayerController.getInstance();
        setContentView(R.layout.player_view);
        _seekBar = (SeekBar) findViewById(R.id.seek_bar);
        _progressPercent = (TextView) findViewById(R.id.progress_percent);
        _progressMinute = (TextView) findViewById(R.id.progress_minute);
        _progressSecond = (TextView) findViewById(R.id.progress_second);
    }

    @Override
    public void onResume() {
        super.onResume();
        setPlayPause();
        setSeekProgress();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopProgressUpdater();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopProgressUpdater();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopProgressUpdater();
    }

    public void stopProgressUpdater() {
        if(_progressUpdater != null) {
            _progressUpdater.stop();
        }
    }

    private void setPlayPause() {
        Button button = (Button) findViewById(R.id.play_pause_button);
        if (_controller.isPlaying()) {
            button.setText(R.string.pause);
        } else {
            button.setText(R.string.play);
        }
    }

    private void setSeekProgress() {
        _progressUpdater = new ProgressUpdater(_handler);
        new Thread(_progressUpdater).start();
    }

    public void onPlayPauseClick(View view) {
        Logger.info("onPlayPause");
        _controller.playPause();
        setPlayPause();
    }

    public void onPrevClick(View view) {
        Logger.info("onPrev");
        _controller.prev();
        setPlayPause();
    }

    public void onNextClick(View view) {
        Logger.info("onNext");
        _controller.next();
        setPlayPause();
    }

    private class ProgressUpdater implements Runnable {

        private AtomicBoolean _stop = new AtomicBoolean(false);
        private Handler _handler;

        public ProgressUpdater(Handler handler) {
            _handler = handler;
        }

        public void stop() {
            _stop.set(true);
        }

        @Override
        public void run() {
            while(!_stop.get()) {
                Message msg = _handler.obtainMessage();
                _handler.sendMessage(msg);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
            }
        }
    }

    private void updateProgress() {
        int progress = _controller.getProgress();
        Integer percent = progress / 10;
        _seekBar.setProgress(progress);
        _progressPercent.setText(percent.toString());
        _progressMinute.setText(_controller.getProgressMinute());
        _progressSecond.setText(_controller.getProgressSecond());
    }
}
