package com.creasetoph.music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.util.Logger;

/**
 * Detects when the headset is plugged or unplugged
 * When you unplug the headset we want to pause playback,
 * and resume playback when the headset is plugged back in
 */
public class HeadsetReceiver extends BroadcastReceiver {

    /*********************************
     * Available data on this Intent *
     *********************************/
    /**
     * State of the plug
     * 0 - Unplugged
     * 1 - Plugged
     */
    private static final String STATE = "state";

    /**
     * Headset type in human readable string
     */
    private static final String NAME = "name";

    /**
     * If headset has microphone
     * 0 - Headset has microphone
     * 1 - Headset doesn't have microphone
     */
    private static final String MICROPHONE = "microphone";

    /*********************************************
     * Possible states for the state intent data *
     *********************************************/
    /**
     * Plugged in state
     */
    private static final int PLUGGED = 1;

    /**
     * Unplugged state
     */
    private static final int UNPLUGGED = 0;

    /***********************
     * Other class members *
     ***********************/
    /**
     * This is so we know that we paused the playback
     */
    private static boolean paused = false;

    public void onReceive(Context context, Intent intent) {
        Integer state = intent.getIntExtra(STATE,100);
        PlaylistController pc = PlaylistController.getInstance();
        if(state == UNPLUGGED && pc.isPlaying()) {
            Logger.info("Headset unplugged, pausing!");
            paused = true;
            pc.playPause();
        }else if(state == PLUGGED && !pc.isPlaying() && paused) {
            Logger.info("Headset plugged, playing!");
            paused = false;
            pc.playPause();
        }
    }
}
