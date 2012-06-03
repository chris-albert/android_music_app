package com.creasetoph.music.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.creasetoph.music.controller.PlaylistController;
import com.creasetoph.music.PlaylistItem;
import com.creasetoph.music.R;

public class PlaylistAdapter extends ArrayAdapter<PlaylistItem> {
    
    private ArrayList<PlaylistItem> _items;
    private Context _context;
    private PlaylistController _playlist;
    
    public PlaylistAdapter(Context context,int textViewResourceId,ArrayList<PlaylistItem> items) {
        super(context,textViewResourceId,items);
        _context = context;
        _items = items;
        _playlist = PlaylistController.getInstance();
    }
    
    public View getView(int position,View convertView,ViewGroup parent) {
        View v = convertView;
        PlaylistItem item = _items.get(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.playlist_item,null);
        TextView tv = (TextView) v.findViewById(R.id.track_name);
        if(tv != null) {
            tv.setText(item.getTrack());
        }
        //set current track number
        if(_playlist.getCurrentTrack() == position) {
            v.setBackgroundColor(0xAA000088);
        }
        return v;
    }
}
