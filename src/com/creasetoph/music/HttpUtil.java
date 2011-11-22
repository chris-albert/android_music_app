package com.creasetoph.music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class HttpUtil {

    public static String httpGet(String url) {
        Logger.log("Trying to get: " + url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        
        try {
            HttpResponse response = httpClient.execute(httpGet);
            InputStream content = response.getEntity().getContent();
            return inputStreamToString(content);
        } catch (Exception e) {
            Logger.log(e);
        }
        return null;
    }
    
    private static String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) { 
                total.append(line); 
            }
        } catch (IOException e) {
            Logger.log("IOException in inputStreamToString: " + e.getMessage());
        }
        
        // Return full string
        return total.toString();
    }
    
    public static void streamMedia(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (Exception e) {
            Logger.log(e);
        }
        mediaPlayer.start();
    }
    
    public static String encode(String str) {
        return Uri.encode(str);
    }
}
