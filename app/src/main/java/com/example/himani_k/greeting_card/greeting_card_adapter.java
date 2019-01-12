package com.example.himani_k.greeting_card;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class greeting_card_adapter extends BaseAdapter {
    Context context;
    int [] imageId;
    LayoutInflater inflater;
    public greeting_card_adapter(FragmentActivity mainActivity, int[] osImages) {
        context=mainActivity;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
    @Override
    public int getCount() {
       return imageId.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.card_list,null); }
            //Get View
            ImageView img=(ImageView) convertView.findViewById(R.id.imageView1);
            //Assign data
            img.setImageResource(imageId[position]);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return convertView;

    }

}
