package com.example.himani_k.greeting_card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
       return rootview;
   }

    public interface OnFragmentInteractionListener {
    }
}
