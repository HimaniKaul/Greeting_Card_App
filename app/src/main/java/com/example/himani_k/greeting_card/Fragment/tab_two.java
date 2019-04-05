package com.example.himani_k.greeting_card.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Database.DatabaseHelper;
import com.example.himani_k.greeting_card.Database.Favourtites;
import com.example.himani_k.greeting_card.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class tab_two extends Fragment {
    DatabaseHelper database;
    RecyclerView recyclerView;
    RecycleAdapter recycler;
    List<Favourtites> datamodel;

    public tab_two() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.fragment_tab_two, container, false);
        return root_view;
    }

    @Override
    public void onViewCreated(@NonNull View root_view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root_view, savedInstanceState);
        datamodel = new ArrayList<>();
        recyclerView = root_view.findViewById(R.id.recycler_view_2);
        showCards();
    }

    private void showCards() {
        database = new DatabaseHelper(getActivity());
        datamodel=  database.getAllCards();
        recycler =new RecycleAdapter(datamodel);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);
    }

    public interface OnFragmentInteractionListener {
    }
            public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {
            List<Favourtites> dataModelArrayList;
            DatabaseHelper db ;

            public RecycleAdapter(List<Favourtites> dataModelArrayList) {
                this.dataModelArrayList = dataModelArrayList;
            }

            class Myholder extends RecyclerView.ViewHolder{
                ImageView img_fav,img_un_fav;
                ImageView img;
                RelativeLayout layout;

                public Myholder(View itemView) {
                    super(itemView);
                    img = itemView.findViewById(R.id.imageView1);
                    img_fav=itemView.findViewById(R.id.imageView2);
                    img_un_fav=itemView.findViewById(R.id.imageView3);
                    layout=itemView.findViewById(R.id.custom_image);
                    db= new DatabaseHelper(getActivity());
                }
            }
            @Override
            public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null);
                return new Myholder(view);

            }

            @Override
            public void onBindViewHolder(final Myholder holder, final int position) {
                final Favourtites dataModel=dataModelArrayList.get(position);
                for(int i=0;i<dataModelArrayList.size();i++){
                    Log.d("Printing:",""+dataModel);
                }
                Picasso.with(getActivity().getApplicationContext())
                        .load(dataModel.getImage())
                        .resize(512, 512)
                        .into(holder.img, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                            }
                            @Override
                            public void onError() {

                            }
                        });
                holder.img_un_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.layout.setVisibility(View.GONE);
                        String s= dataModel.getImage();
                        // delete Cards
                        db.deleteCards(new Favourtites(position,s));
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                });




            }

            @Override
            public int getItemCount() {
                return dataModelArrayList.size();
            }
        }
    }

