package com.example.himani_k.greeting_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class tab_One extends Fragment {
    GridView gv;
   int images[]={R.drawable.main_base_red, R.drawable.main_base_green,
           R.drawable.main_base_darkpurple, R.drawable.main_base_aqua,
           R.drawable.main_base_purple,R.drawable.main_base_yellow};
   @Override
   public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.fragment_tab__one,null);
       gv=(GridView) rootview.findViewById(R.id.grid_view1);

       //setting Adapter
       greeting_card_adapter adapter=new greeting_card_adapter(getActivity(),images);
       gv.setAdapter(adapter);

       //adding a fab
       FloatingActionButton fab1= rootview.findViewById(R.id.second_fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"First Button",Toast.LENGTH_SHORT).show();
                //Intent gallery= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
               // startActivityForResult(gallery, PICK_IMAGE);
                Intent i= new Intent(getActivity(),image_from_gallery.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab2= rootview.findViewById(R.id.third_fab);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Second Button",Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getActivity(),create_custom_card.class);
                startActivity(i);
                }
        });

        return rootview;
   }
    public interface OnFragmentInteractionListener {
    }
}
