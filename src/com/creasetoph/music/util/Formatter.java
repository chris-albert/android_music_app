package com.creasetoph.music.util;


public class Formatter {

    public static String formatTrack(String item) {
        return capitalize(item.replace("_"," ").replace(".mp3","").replaceAll("^\\d*",""));
    }

    public static String getTrackNum(String track) {
        return track.substring(0,2);
    }

    public static String capitalize(String string) {
        String out = "";
        String[] tokens = string.split("\\s");
        for (String token : tokens) {
            try {
                out += Character.toUpperCase(token.charAt(0)) + token.substring(1) + " ";
            }catch(IndexOutOfBoundsException ioobe) {
                //If we get an IndexOutOfBoundsException then just dont add to string
            }
        }
        return out.trim();
    }
}
