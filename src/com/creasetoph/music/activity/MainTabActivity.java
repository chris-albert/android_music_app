package com.creasetoph.music.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import com.creasetoph.music.R;
import com.creasetoph.music.model.MusicModelManager;
import com.creasetoph.music.object.Library;


public class MainTabActivity extends TabActivity {

    private MusicModelManager musicModelManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        MusicModelManager.getInstance();
        setUpTabs();
    }

    private void setUpTabs() {
        Resources resources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec;
        Intent intent;

        intent = new Intent().setClass(this,NetworkLibraryActivity.class);
        tabSpec = tabHost
                .newTabSpec("network")
                .setIndicator("Network",resources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent().setClass(this,LocalLibraryActivity.class);
        tabSpec = tabHost
                .newTabSpec("local")
                .setIndicator("Local",resources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent().setClass(this,PlaylistActivity.class);
        tabSpec = tabHost
                .newTabSpec("playlist")
                .setIndicator("Playlist",resources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent().setClass(this,PlayerActivity.class);
        tabSpec = tabHost
                .newTabSpec("player")
                .setIndicator("Player",resources.getDrawable(R.drawable.ic_tab_artists))
                .setContent(intent);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_preferences:
                showPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPreferences() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }
}