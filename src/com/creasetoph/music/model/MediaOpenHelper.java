package com.creasetoph.music.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MediaOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "creasetoph_media";
    private static final String MEDIA_TABLE_NAME = "media";
    /*
    private static final String MEDIA_TABLE_CREATE =
                "CREATE TABLE " + MEDIA_TABLE_NAME + " (" +
                KEY_WORD + " TEXT, " +
                KEY_DEFINITION + " TEXT);";
    */

	public MediaOpenHelper(Context context) {
		super(context, DATABASE_NAME,null,DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
