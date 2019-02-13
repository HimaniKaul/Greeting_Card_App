package com.example.himani_k.greeting_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class sticker_adapter extends BaseAdapter {
    Context context;
    public sticker_adapter(Context c) {
        context=c; }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        { LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.sticker_image,null); }
        //Get View
        ImageView img= convertView.findViewById(R.id.imageView1);
        //Assign data
        img.setImageResource(mThumbIds[position]);
        return convertView;
    }
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sticker_one, R.drawable.sticker_two,
            R.drawable.sticker_three, R.drawable.sticker_one, R.drawable.sticker_two,
            R.drawable.sticker_three, R.drawable.sticker_one, R.drawable.sticker_two,
            R.drawable.sticker_three,R.drawable.sticker_one, R.drawable.sticker_two,
            R.drawable.sticker_three,R.drawable.sticker_one, R.drawable.sticker_two,
            R.drawable.sticker_three};

}
