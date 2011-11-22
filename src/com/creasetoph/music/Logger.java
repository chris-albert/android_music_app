package com.creasetoph.music;

public class Logger {
    
    public static void log(String str) {
        System.out.println(str);
    }
    
    public static void log(Exception e) {
        log(e.getMessage());
    }
}
