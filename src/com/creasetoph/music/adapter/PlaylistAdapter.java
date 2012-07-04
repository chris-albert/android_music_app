package com.creasetoph.music.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.creasetoph.music.activity.PlaylistActivity;
import com.creasetoph.music.item.PlaylistItem;
import com.creasetoph.music.R;
import com.creasetoph.music.object.PlaylistTrack;
import com.creasetoph.music.util.Formatter;
import com.creasetoph.music.util.Logger;

/**
 * Tells android how to draw the playlist list
 */
public class PlaylistAdapter extends ArrayAdapter<PlaylistTrack> {

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
    public PlaylistAdapter(PlaylistActivity context, int textViewResourceId, List<PlaylistTrack> items) {
        super(context, textViewResourceId, items);
        _context = context;
    }

    public void setItems(List<PlaylistTrack> playlistTracks) {
        clear();
        for (PlaylistTrack item : playlistTracks) {
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
        Logger.debug("PlaylistAdapter position: " + position);
        PlaylistTrack item = getItem(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.playlist_item,null);
        TextView artistText = (TextView) v.findViewById(R.id.playlist_artist);
        artistText.setText(item.getArtist() + "/");
        TextView albumText = (TextView) v.findViewById(R.id.playlist_album);
        albumText.setText(item.getAlbum());
        TextView trackText = (TextView) v.findViewById(R.id.playlist_track);
        trackText.setText(Formatter.formatTrack(item.getTrack()));
        if(_context.isActive(position)) {
            v.setBackgroundColor(CURRENT_COLOR);
        }
        return v;
    }

}
