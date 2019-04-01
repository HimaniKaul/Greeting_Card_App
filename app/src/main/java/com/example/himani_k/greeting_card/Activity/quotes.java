package com.example.himani_k.greeting_card.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Adapters.MyAdapter;
import com.example.himani_k.greeting_card.Adapters.RecyclerTouchListener;
import com.example.himani_k.greeting_card.Constant.Constants;
import com.example.himani_k.greeting_card.Interface.RequestInterface;
import com.example.himani_k.greeting_card.Module.Category.CategoryStoreData;
import com.example.himani_k.greeting_card.Module.Category.CategorySuccess;
import com.example.himani_k.greeting_card.Module.Quotes.QuotesStoreData;
import com.example.himani_k.greeting_card.Module.Quotes.QuotesSuccess;
import com.example.himani_k.greeting_card.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class quotes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String quote="";
    private ArrayList<QuotesStoreData> arrayListCat;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotes_recycler_view);
        mRecyclerView =findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        arrayListCat=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        callQuotesApi();

    }

    private void callQuotesApi() {

        showProgressDilaog();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();

        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
        final Call<QuotesSuccess> callFbLogin = requestInterface.getQuoteData();
        callFbLogin.enqueue(new Callback<QuotesSuccess>() {
            @Override
            public void onResponse(Call<QuotesSuccess> call, Response<QuotesSuccess> response)
            {
                try{
                    hideProgress();
                    if(response.body().getSuccess())
                    { for(int  i=0;i<response.body().getQuotes().size();i++)
                    {   String quotes = response.body().getQuotes().get(i).getQuotes();

                        QuotesStoreData categoriesDataStore = new QuotesStoreData();
                        categoriesDataStore.setMessage(quotes);

                        arrayListCat.add(categoriesDataStore);
                        System.out.println("name :"+quotes);
                    }

                        //mAdapter = new ImageAdapter(getApplicationContext(),arrayListCat);
                        mLayoutManager = new LinearLayoutManager(quotes.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new MyAdapter(getApplicationContext(),arrayListCat);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.addOnItemTouchListener( // and the click is handled
                                new RecyclerTouchListener(quotes.this, new RecyclerTouchListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position) {
                                        Log.d("click item", String.valueOf(position));
                                        TextView textView = view.findViewById(R.id.text_quote);
                                        quote=textView.getText().toString();
                                        Log.d("click item", quote);
                                        onBackPressed();
                                    }
                                }));
                    }
                    else
                    {
                        Toast.makeText(quotes.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    System.out.println("exception"+e.toString());
                    Toast.makeText(quotes.this, ""+getResources().getString(R.string.somethingwrong)+": "+e.toString(), Toast.LENGTH_SHORT).show();
                    hideProgress();
                }

            }

            @Override
            public void onFailure(Call<QuotesSuccess> call, Throwable t) {
                System.out.println("api fail"+t.getMessage());
                Toast.makeText(quotes.this, "Make Sure You Are Connected To Internet", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    private void hideProgress() {
        if(progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDilaog() {
      progressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
