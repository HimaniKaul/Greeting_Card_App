package com.example.himani_k.greeting_card.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Database.DatabaseHelper;
import com.example.himani_k.greeting_card.Database.Favourtites;
import com.example.himani_k.greeting_card.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

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
        datamodel = new ArrayList<>();
        recyclerView = root_view.findViewById(R.id.recycler_view_2);
        showCards();
        return root_view;
    }


    private void showCards() {
        database = new DatabaseHelper(getActivity());
        datamodel=  database.getAllContacts();
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

            public RecycleAdapter(List<Favourtites> dataModelArrayList) {
                this.dataModelArrayList = dataModelArrayList;
            }

            class Myholder extends RecyclerView.ViewHolder{
                ImageView img;

                public Myholder(View itemView) {
                    super(itemView);
                    img = itemView.findViewById(R.id.imageView1);
                }
            }
            @Override
            public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null);
                return new Myholder(view);

            }

            @Override
            public void onBindViewHolder(Myholder holder, int position) {
                Favourtites dataModel=dataModelArrayList.get(position);
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
                        }); }

            @Override
            public int getItemCount() {
                return dataModelArrayList.size();
            }
        }
    }

