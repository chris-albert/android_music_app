package com.creasetoph.music.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.creasetoph.music.util.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Utils for working with http connections
 */
public class HttpUtil {

    //Millisecond connect timeout
    private static final int _connectTimeout = 10000;
    //Millisecond socket timeout
    private static final int _socketTimeout = 10000;

    /**
     * Gets an HttpClient with any http params set
     * @return A pre-configured HttpClient
     */
    public static HttpClient getHttpClient() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,_connectTimeout);
        HttpConnectionParams.setSoTimeout(httpParams,_socketTimeout);
        return new DefaultHttpClient(httpParams);
    }

    /**
     * Gets data from the url provided
     * @param url Url to get data from
     * @return The body of the http response
     */
    public static String httpGet(String url) {
        Logger.info("Trying to get: " + url);
        HttpClient httpClient = getHttpClient();
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

    /**
     * Converts an InputStream to a string
     * @param is InputStream to convert to string
     * @return String version of InputStream
     */
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
            Logger.info("IOException in inputStreamToString: " + e.getMessage());
        }

        // Return full string
        return total.toString();
    }

    /**
     * Encodes a string for use as an url in a http request
     * @param url String to encode
     * @return Encoded string
     */
    public static String encode(String url) {
        return Uri.encode(url);
    }
}
