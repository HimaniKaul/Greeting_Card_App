package com.example.himani_k.greeting_card;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;


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
        {   LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.card_list,null); }
            //Get View
            ImageView img= convertView.findViewById(R.id.imageView1);
            //Assign data
            img.setImageResource(imageId[position]);

            //sending image
             img.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(context,Card_open.class);
                     intent.putExtra("image",imageId[position]);
                     context.startActivity(intent);

                 }
             });

             //adding favourite
            final ToggleButton toggleButton = convertView.findViewById(R.id.myToggleButton);
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_black_24dp));
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorite_black_24dp));
                    else
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_black_24dp));
                }
            });

            return convertView;
    }

}
