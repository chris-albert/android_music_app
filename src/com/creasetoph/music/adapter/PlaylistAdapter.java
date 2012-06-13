package com.creasetoph.music.adapter;

import java.util.ArrayList;

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

/**
 * Tells android how to draw the playlist list
 */
public class PlaylistAdapter extends ArrayAdapter<PlaylistItem> {

    //List of PlaylistItem's to show
    private ArrayList<PlaylistItem> _items;
    //Current context
    private PlaylistActivity _context;
    //Playlist controller
    private PlaylistController _playlist;

    /**
     * Constructor sets up Adapter
     * @param context The current context.
     * @param textViewResourceId The resource ID for a layout file containing a
     *                           TextView to use when instantiating views.
     * @param items Items to show in list view
     */
    public PlaylistAdapter(PlaylistActivity context, int textViewResourceId, ArrayList<PlaylistItem> items) {
        super(context, textViewResourceId, items);
        _context = context;
        _items = items;
        _playlist = PlaylistController.getInstance();
    }

    /**
     * Gets the view for each item in adapter
     * @param position Position in items lise
     * @param v The current view
     * @param parent The parent of the view
     * @return Updated view with item added to it
     */
    public View getView(int position, View v, ViewGroup parent) {
        PlaylistItem item = _items.get(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.playlist_item, null);
        TextView tv = (TextView) v.findViewById(R.id.track_name);
        if (tv != null) {
            tv.setText(item.getTrack());
        }
        return v;
    }
}
