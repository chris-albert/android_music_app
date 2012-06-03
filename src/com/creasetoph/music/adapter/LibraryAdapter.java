package com.creasetoph.music.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.creasetoph.music.LibraryItem;
import com.creasetoph.music.R;

public class LibraryAdapter extends ArrayAdapter<LibraryItem> {

    private ArrayList<LibraryItem> _items;
    private Context _context;

    public LibraryAdapter(Context context, int textViewResourceId, ArrayList<LibraryItem> items) {
        super(context, textViewResourceId, items);
        _context = context;
        _items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LibraryItem item = _items.get(position);
        LayoutInflater li = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (item.typeEquals(LibraryItem.Type.Artist)) {
            v = li.inflate(R.layout.artist_item, null);
            TextView tv = (TextView) v.findViewById(R.id.artist_name);
            if (tv != null) {
                tv.setText(item.getValue());
            }
        } else {
            v = li.inflate(R.layout.album_item, null);
            TextView tv = (TextView) v.findViewById(R.id.album_name);
            if (tv != null) {
                tv.setText(item.getValue());
            }
        }
        return v;
    }
}
