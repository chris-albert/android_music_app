package com.creasetoph.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.creasetoph.music.R;
import com.creasetoph.music.item.LibraryItem;
import com.creasetoph.music.object.Album;
import com.creasetoph.music.object.Artist;
import com.creasetoph.music.object.Track;
import com.creasetoph.music.util.Formatter;
import com.creasetoph.music.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends ArrayAdapter<Artist> {

    //Context of application
    private Context _context;

    /**
     * Constructor sets up Adapter
     * @param context The current context.
     * @param textViewResourceId The resource ID for a layout file containing a
     *                           TextView to use when instantiating views.
     * @param items Items to show in list view
     */
    public LibraryAdapter(Context context, int textViewResourceId, ArrayList<Artist> items) {
        super(context, textViewResourceId, items);
        _context = context;
    }

    /**
     * Gets the view for each item in adapter
     * @param position Position in items lise
     * @param v The current view
     * @param parent The parent of the view
     * @return Updated view with item added to it
     */
    public View getView(int position, View v, ViewGroup parent) {
        Artist artist = getItem(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = li.inflate(R.layout.library_item,null);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.library_item);
        View artistView = li.inflate(R.layout.artist_item,linearLayout);
        TextView tv = (TextView) artistView.findViewById(R.id.artist_name);
        tv.setTag(R.id.artist_item,artist);
        tv.setText(artist.getName());
        if(artist.opened()) {
            linearLayout.addView(li.inflate(R.layout.album_title,null));
            for(Album album: artist.getAlbums()) {
                View view = li.inflate(R.layout.album_item,null);
                TextView textView = (TextView) view.findViewById(R.id.album_name);
                textView.setTag(R.id.artist_item,artist);
                textView.setTag(R.id.album_item,album);
                textView.setText(album.getName());
                linearLayout.addView(view);
                if(album.opened()) {
                    linearLayout.addView(li.inflate(R.layout.track_title,null));
                    Album selectedAlbum = getItem(position).getAlbum(album.getName());
                    for(Track track: selectedAlbum.getTracks()) {
                        View trackView = li.inflate(R.layout.track_item,null);
                        TextView trackText = (TextView) trackView.findViewById(R.id.track_name);
                        trackText.setTag(R.id.artist_item,artist);
                        trackText.setTag(R.id.album_item,album);
                        trackText.setTag(R.id.track_item,track);
                        trackText.setText(Formatter.formatTrack(track.getName()));
                        linearLayout.addView(trackView);
                    }
                }
            }
        }
        return v;
    }
}
