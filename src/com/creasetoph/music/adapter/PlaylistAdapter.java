package com.creasetoph.music.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.creasetoph.music.activity.PlaylistActivity;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.item.PlaylistItem;
import com.creasetoph.music.R;
import com.creasetoph.music.util.Logger;

/**
 * Tells android how to draw the playlist list
 */
public class PlaylistAdapter extends ArrayAdapter<PlaylistItem> {

    //Current context
    private PlaylistActivity _context;
    //Color of current playlist track
    public static final int CURRENT_COLOR = 0xAA000088; //Dark Blue

    /**
     * Constructor sets up Adapter
     * @param context The current context.
     * @param textViewResourceId The resource ID for a layout file containing a
     *                           TextView to use when instantiating views.
     * @param items Items to show in list view
     */
    public PlaylistAdapter(PlaylistActivity context, int textViewResourceId, List<PlaylistItem> items) {
        super(context, textViewResourceId, items);
        _context = context;
    }

    public void setItems(List<PlaylistItem> items) {
        clear();
        for (PlaylistItem item : items) {
            add(item);
        }
        notifyDataSetChanged();
    }

    /**
     * Gets the view for each item in adapter
     * @param position Position in items lise
     * @param v The current view
     * @param parent The parent of the view
     * @return Updated view with item added to it
     */
    public View getView(int position, View v, ViewGroup parent) {
        Logger.info("PlaylistAdapter position: " + position);
        PlaylistItem item = getItem(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.playlist_item, null);
        TextView tv = (TextView) v.findViewById(R.id.track_name);
        if (tv != null) {
            tv.setText(item.getTrack());
        }
        if(_context.isActive(position)) {
            v.setBackgroundColor(CURRENT_COLOR);
        }
        return v;
    }

}
