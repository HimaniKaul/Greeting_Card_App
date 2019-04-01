                package com.example.himani_k.greeting_card.Activity;

                import android.app.ProgressDialog;
                import android.content.Context;
                import android.content.Intent;
                import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
                import android.widget.RelativeLayout;
                import android.widget.TextView;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Constant.Constants;
import com.example.himani_k.greeting_card.Interface.RequestInterface;
import com.example.himani_k.greeting_card.Module.Category.CategoryStoreData;
import com.example.himani_k.greeting_card.Module.Category.CategorySuccess;
import com.example.himani_k.greeting_card.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

                public class grid_Screen extends AppCompatActivity {
                    Toolbar myToolbar;
                    private DrawerLayout drawerLayout;
                    private ArrayList<CategoryStoreData> arrayListCat;
                    ImageAdapter adpater;
                    RecyclerView recyclerView;

                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_grid__screen);

                        recyclerView=findViewById(R.id.gridview);
                        //setting toolbar
                        myToolbar = findViewById(R.id.toolbar1);
                        setSupportActionBar(myToolbar);

                        getSupportActionBar().setDisplayShowTitleEnabled(false);

                        if(getSupportActionBar() != null)
                        { getSupportActionBar().setDisplayHomeAsUpEnabled(true); }

                        arrayListCat = new ArrayList<>();
                        call_card_api();

                        //code for navigation bar
                        drawerLayout= findViewById(R.id.drawer_layout);
                        ActionBarDrawerToggle drawerToggle; // takes care of everything
                        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_closed);
                        drawerLayout.addDrawerListener(drawerToggle);
                        drawerToggle.syncState(); //rotating the hamburger icon
                    }

                    private void call_card_api() {

                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(100,TimeUnit.SECONDS).build();

                        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
                        RequestInterface requestInterface=retrofit.create(RequestInterface.class);
                        final Call<CategorySuccess> callFbLogin = requestInterface.getCatData();
                        callFbLogin.enqueue(new Callback<CategorySuccess>() {

                            @Override
                            public void onResponse(Call<CategorySuccess> call, Response<CategorySuccess> response)
                            {
                                try{
                                    if(response.body().isSuccess())
                                    { for(int  i=0;i<response.body().getCategory().size();i++)
                                        {   String name = response.body().getCategory().get(i).getName();
                                            String imageurl = response.body().getCategory().get(i).getImage();

                                            CategoryStoreData categoriesDataStore = new CategoryStoreData();
                                            categoriesDataStore.setName(name);
                                            categoriesDataStore.setImage(imageurl);

                                            arrayListCat.add(categoriesDataStore);
                                            System.out.println("name :"+name+" image url :"+imageurl);
                                        }

                                        adpater = new ImageAdapter(getApplicationContext(),arrayListCat);
                                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                                        recyclerView.setLayoutManager(gridLayoutManager);
                                        recyclerView.setAdapter(adpater);
                                    }
                                    else
                                    {
                                        Toast.makeText(grid_Screen.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e)
                                {
                                    System.out.println("exception"+e.toString());
                                    Toast.makeText(grid_Screen.this, ""+getResources().getString(R.string.somethingwrong)+": "+e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<CategorySuccess> call, Throwable t) {
                                System.out.println("api fail"+t.getMessage());
                                Toast.makeText(grid_Screen.this, "Make Sure You Are Connected To Internet", Toast.LENGTH_SHORT).show();
                            }
                        }); }

                    @Override
                        public void onBackPressed() {
                        if(drawerLayout.isDrawerOpen(Gravity.START))
                        {drawerLayout.closeDrawer(Gravity.START);}
                        else{
                        super.onBackPressed();
                    }}
                    @Override
                    public boolean onCreateOptionsMenu(Menu menu) {
                        getMenuInflater().inflate(R.menu.app_bar_contents,menu);
                        return super.onCreateOptionsMenu(menu);
                    }
                }

                 class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
                    private final ArrayList<CategoryStoreData> lstdata;
                    Context context;

                    public class MyViewHolder extends RecyclerView.ViewHolder {
                        public TextView mTextView;
                        public ImageView img; RelativeLayout rl;
                        public MyViewHolder(View v) {
                            super(v);
                            mTextView =v.findViewById(R.id.tv);
                            img=v.findViewById(R.id.imageView1);
                            rl=v.findViewById(R.id.custom_image);
                        }
                    }

                    public ImageAdapter(Context applicationContext, ArrayList<CategoryStoreData> arrayListCat)
                    {
                        this.context = applicationContext;
                        this.lstdata = arrayListCat;
                    }
                    // Create new views (invoked by the layout manager)
                    @Override
                    public ImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.first_cards, parent, false);
                        MyViewHolder vh = new MyViewHolder(v);
                        return vh;
                    }

                    @Override
                    public void onBindViewHolder(final MyViewHolder holder, final int i) {
                        CategoryStoreData categoriesDataStorea = lstdata.get(i);
                        holder.mTextView.setText(categoriesDataStorea.getName());
                        Picasso.with(context.getApplicationContext())
                                .load(categoriesDataStorea.getImage())
                                .resize(512,512)
                                .into(holder.img, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess()
                                    {
                                    }

                                    @Override
                                    public void onError()
                                    {

                                    }
                                });
                     holder.rl.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Intent i =new Intent(context,greeting_cards_class.class);
                             i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             context.startActivity(i);
                         }
                     });


                    }

                    // Return the size of your data set (invoked by the layout manager)
                    @Override
                    public int getItemCount() {
                        return lstdata.size();
                    }
                }