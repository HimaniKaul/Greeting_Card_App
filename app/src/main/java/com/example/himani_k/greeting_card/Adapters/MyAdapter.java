package com.example.himani_k.greeting_card.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.himani_k.greeting_card.Module.Category.CategoryStoreData;
import com.example.himani_k.greeting_card.Module.Quotes.QuotesStoreData;
import com.example.himani_k.greeting_card.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final ArrayList<QuotesStoreData> lstdata;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
       public ImageView imageView;
       public TextView mTextView;
       public RelativeLayout mParentLayout;
       public MyViewHolder(View v) {
            super(v);
            mTextView =v.findViewById(R.id.text_quote);
            imageView=v.findViewById(R.id.image_quote);
            mParentLayout=v.findViewById(R.id.parent_layout);
       }
    }

    public MyAdapter(Context context, ArrayList<QuotesStoreData> myData_set) {
        this.lstdata = myData_set;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                      .inflate(R.layout.quotes, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        QuotesStoreData categoriesDataStorea = lstdata.get(i);
        holder.mTextView.setText(categoriesDataStorea.getMessage());
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lstdata.size();
    }
}
