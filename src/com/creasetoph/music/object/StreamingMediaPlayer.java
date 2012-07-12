package com.creasetoph.music.object;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import com.creasetoph.music.util.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class StreamingMediaPlayer {

    //96kbps * 10 seconds/8 bits per byte
    private static final int INITIAL_KB_BUFFER = 96 * 10 / 8;
    private static final String CACHE_FILE_PREFIX = "downloadingMedia_";
    private static final String PLAYING_FILE_PREFIX = "playingMedia_";
    private final Handler  _handler = new Handler();
    private MediaPlayer _mediaPlayer = null;
    private Context _context;
    private File  _downloadingMediaFile;
    private boolean _isInterrupted;
    private int _counter = 0;
    private int _totalKbRead = 0;
    private String _url;

    public StreamingMediaPlayer(Context context) {
        _context = context;
    }

    public void startStreaming(final String url) {
        _url = url;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    downloadAuditIncrement();
                } catch (IOException ioe) {
                    Logger.error("");
                    return;
                }
            }
        };
        new Thread(r).start();
    }

    private void downloadAuditIncrement() throws IOException {
        //Establish a connection to the url
        URLConnection urlConnection = new URL(_url).openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        if (inputStream == null) {
            Logger.error(getClass().getSimpleName() + ": Unable to create input stream for url: " + _url);
            return;
        }
        //Create temporary file for buffering data into
        _downloadingMediaFile = new File(_context.getCacheDir(), CACHE_FILE_PREFIX + (_counter++) + ".dat");
        if (_downloadingMediaFile.exists()) {
            _downloadingMediaFile.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(_downloadingMediaFile);
        //Start to read data from url stream
        byte buffer[] = new byte[16384];
        int totalBytesRead = 0;
        int incrementalBytesRead = 0;
        _totalKbRead = 0;
        do {
            int numread = inputStream.read(buffer);
            if (numread <= 0) {
                //Nothing left to do so quit
                break;
            } else {
                fileOutputStream.write(buffer, 0, numread);
                totalBytesRead += numread;
                incrementalBytesRead += numread;
                _totalKbRead = totalBytesRead / 1000;
                //Test whether we need to transfer buffered data to the media player
                testMediaBuffer();
                fireDataLoadUpdate();
            }
        } while (validateNotInterrupted());

        if (validateNotInterrupted()) {
            fireDataFullyLoaded();
        }
        inputStream.close();
    }

    private boolean validateNotInterrupted() {
        if (_isInterrupted) {
            if (_mediaPlayer != null) {
                _mediaPlayer.pause();
            }
            return false;
        }
        return true;
    }

    private void testMediaBuffer() {
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                if (_mediaPlayer == null) {
                    if (_totalKbRead >= INITIAL_KB_BUFFER) {
                        //We have enough buffered content so start the media player
                        startMediaPlayer();
                    }
                } else if (_mediaPlayer.getDuration() - _mediaPlayer.getCurrentPosition() <= 1000) {
                    //The media player has been started and has reached the end of its buffered data
                    //We test for < 1 second of data because the media player will ofter stop when there
                    //are still a few milliseconds of data left to play
                    transferBufferToMediaPlayer();
                }
            }
        };
        _handler.post(updater);
    }

    private void transferBufferToMediaPlayer() {
        try {
            //Determine if we need to restart the player after transferring data
            //(eg. perhaps the user pressed pause)
            //and store the current audio position so we can reset it later
            boolean wasPlaying = _mediaPlayer.isPlaying();
            int currentPosition = _mediaPlayer.getCurrentPosition();

            //Copy the current buffer file as we can't download content into the same
            //file that the media player is reading from
            File oldBufferedFile = new File(_context.getCacheDir(), PLAYING_FILE_PREFIX + _counter + ".dat");
            File bufferedFile = new File(_context.getCacheDir(), PLAYING_FILE_PREFIX + (_counter++) + ".dat");

            bufferedFile.deleteOnExit();
            moveFile(_downloadingMediaFile, bufferedFile);

            _mediaPlayer.pause();
            _mediaPlayer = createMediaPlayer(bufferedFile);
            _mediaPlayer.seekTo(currentPosition);

            //Restart it at the end of prior buffered content of mediaPlayer was previously playing;
            //Note: we test for < 1 second of data because the media player can stop when there
            //are still a few milliseconds of dat left to play
            boolean atEndOfFile = _mediaPlayer.getDuration() - _mediaPlayer.getCurrentPosition() <= 1000;
            if (wasPlaying || atEndOfFile) {
                _mediaPlayer.start();
            }

            oldBufferedFile.delete();
        } catch (Exception e) {
            Logger.error(getClass().getSimpleName() + ": Error updating to newly loaded content");
        }
    }

    private void startMediaPlayer() {
        try {
            File bufferedFile = new File(_context.getCacheDir(), PLAYING_FILE_PREFIX + (_counter++) + ".dat");
            moveFile(_downloadingMediaFile, bufferedFile);

            Logger.info(getClass().getSimpleName() + "Buffered File length: " + bufferedFile.length());
            Logger.info(getClass().getSimpleName() + "Buffered File path: " + bufferedFile.getAbsolutePath());

            _mediaPlayer = createMediaPlayer(bufferedFile);
            _mediaPlayer.start();
            fireDataPreLoadComplete();
        } catch (IOException ioe) {
            Logger.error(getClass().getSimpleName() + ": Error initializing MediaPlayer");
            return;
        }
    }

    private MediaPlayer createMediaPlayer(File mediaFile) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Logger.error(getClass().getSimpleName() + "Error in MediaPlayer: (" + i + ") with extra (" + i1 + ")");
                return false;
            }
        });
        FileInputStream fis = new FileInputStream(mediaFile);
        mediaPlayer.setDataSource(fis.getFD());
        mediaPlayer.prepare();
        return mediaPlayer;
    }

    private void fireDataFullyLoaded() {
        Logger.info(getClass().getSimpleName() + ": data fully loaded");
    }

    private void fireDataPreLoadComplete() {
        Logger.info(getClass().getSimpleName() + ": data preload complete");
    }

    private void fireDataLoadUpdate() {
        Logger.info(getClass().getSimpleName() + ": data load update");
    }

    public void moveFile(File oldLocation, File newLocation)
            throws IOException {

        if (oldLocation.exists()) {
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(oldLocation));
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(newLocation, false));
            try {
                byte[] buff = new byte[8192];
                int numChars;
                while ((numChars = reader.read(buff, 0, buff.length)) != -1) {
                    writer.write(buff, 0, numChars);
                }
            } catch (IOException ex) {
                throw new IOException("IOException when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
            } finally {
                try {
                    if (reader != null) {
                        writer.close();
                        reader.close();
                    }
                } catch (IOException ex) {
                    Log.e(getClass().getName(), "Error closing files when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
                }
            }
        } else {
            throw new IOException("Old location does not exist when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
        }
    }
}
