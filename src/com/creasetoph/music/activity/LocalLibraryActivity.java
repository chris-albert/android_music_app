package com.creasetoph.music.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LocalLibraryActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is the Local Library tab");
        setContentView(textview);
    }
}