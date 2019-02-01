package com.example.himani_k.greeting_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater= (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.first_cards,null); }
            //Get View
            ImageView img= convertView.findViewById(R.id.imageView1);
            //Assign data
             img.setImageResource(mThumbIds[position]);
             return convertView;
    }
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.pongal, R.drawable.sankrant,
            R.drawable.shivratri, R.drawable.valentine,
            R.drawable.republicday,R.drawable.holi,
    };
}

