package com.creasetoph.music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.util.Logger;

/**
 * Detects incoming and outgoing calls
 * When we get an income call or start a new outgoing call then we will pause the music
 * When the call has finished then we will start the music again
 *
 * This class listens on intents:
 *      android.intent.action.PHONE_STATE
 *      android.intent.action.NEW_OUTGOING_CALL
 *
 * When we get a new incoming call the state is TelephonyManager.EXTRA_STATE_RINGING
 * When we start a new outgoing call the state is TelephonyManager.EXTRA_STATE_OFFHOOK
 * When we hang up from either incoming or outgoing the state is TelephonyManager.EXTRA_STATE_IDLE
 */
public class CallReceiver extends BroadcastReceiver {

    /**
     * This is so we know that we paused the playback
     */
    private static boolean paused = false;

    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        PlaylistController pc = PlaylistController.getInstance();
        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING) ||
           state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            if(pc.isPlaying()) {
                paused = true;
                Logger.info("(Incoming/Outgoing) call, pausing!");
                pc.playPause();
            }
        }else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if(!pc.isPlaying() && paused) {
                paused = false;
                Logger.info("Call over, playing!");
                pc.playPause();
            }
        }
    }
}
