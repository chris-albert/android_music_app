package com.creasetoph.music.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.creasetoph.music.item.LibraryItem;
import com.creasetoph.music.R;
import com.creasetoph.music.item.PlaylistItem;
import com.creasetoph.music.util.Logger;

/**
 * Tells android how to draw the library list
 */
public class LibraryAdapter extends ArrayAdapter<LibraryItem> {

    //Context of application
    private Context _context;

    /**
     * Constructor sets up Adapter
     * @param context The current context.
     * @param textViewResourceId The resource ID for a layout file containing a
     *                           TextView to use when instantiating views.
     * @param items Items to show in list view
     */
    public LibraryAdapter(Context context, int textViewResourceId, ArrayList<LibraryItem> items) {
        super(context, textViewResourceId, items);
        _context = context;
    }

    public void setItems(List<LibraryItem> items) {
        clear();
        for (LibraryItem item : items) {
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
        LibraryItem item = getItem(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (item.typeEquals(LibraryItem.Type.Artist)) {
            v = li.inflate(R.layout.artist_item, null);
            TextView tv = (TextView) v.findViewById(R.id.artist_name);
            if (tv != null) {
                tv.setText(item.getValue());
            }
        }else if(item.typeEquals(LibraryItem.Type.Album)){
            v = li.inflate(R.layout.album_item, null);
            TextView tv = (TextView) v.findViewById(R.id.album_name);
            if (tv != null) {
                tv.setText(item.getValue());
            }
        }else if(item.typeEquals(LibraryItem.Type.Track)){
            v = li.inflate(R.layout.track_item, null);
            TextView tv = (TextView) v.findViewById(R.id.track_name);
            if (tv != null) {
                tv.setText(item.getValue());
            }
        }
        return v;
    }
}
