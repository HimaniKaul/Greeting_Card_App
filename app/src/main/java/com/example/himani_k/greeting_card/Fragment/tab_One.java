package com.example.himani_k.greeting_card.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Activity.create_custom_card;
import com.example.himani_k.greeting_card.Activity.image_from_gallery;
import com.example.himani_k.greeting_card.Constant.Constants;
import com.example.himani_k.greeting_card.Database.DatabaseHelper;
import com.example.himani_k.greeting_card.Database.Favourtites;
import com.example.himani_k.greeting_card.Interface.RequestInterface;
import com.example.himani_k.greeting_card.Module.Cards.CardStoreData;
import com.example.himani_k.greeting_card.Module.Cards.CardsSuccess;
import com.example.himani_k.greeting_card.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class tab_One extends Fragment {
    private static final int IMAGE_GALLERY_REQUEST = 0;
    RecyclerView gv;
    public static int flag_check = 3;
    Uri imageUri;
    InputStream inputStream;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    public ArrayList<CardsSuccess> arrayListCat;
    private greeting_card_adapter adpater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.fragment_tab__one, null);
        gv = root_view.findViewById(R.id.grid_view1);
        arrayListCat = new ArrayList<>();

        progressDialog =  new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        setCardsFromApi();

        //setting the alpha
        final FloatingActionsMenu fab_m = root_view.findViewById(R.id.fab_menu);
        final View v = root_view.findViewById(R.id.view);
        fab_m.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuCollapsed() {
                v.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onMenuExpanded() {
                v.setVisibility(View.VISIBLE);
                v.setClickable(true);
            }
        });

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fab_m.collapse();
                return false;
            }
        });
        //adding a fab
        FloatingActionButton fab1 = root_view.findViewById(R.id.second_fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_check = 1;
                on_Image_from_Gallery();
            }
        });

        FloatingActionButton fab2 = root_view.findViewById(R.id.third_fab);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag_check = 2;
                Intent i = new Intent(getActivity(), create_custom_card.class);
                startActivity(i);
            }
        });
        return root_view;
    }

    private void setCardsFromApi() {

        showDialog();
         
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        final Call<CardStoreData> callFbLogin = requestInterface.getCardData();
        callFbLogin.enqueue(new Callback<CardStoreData>() {
            @Override
            public void onResponse(Call<CardStoreData> call, Response<CardStoreData> response) {
                try {

                    hideProgress();

                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getCards().size(); i++) {
                            String imageurl = response.body().getCards().get(i).getImage();
                            CardsSuccess cds = new CardsSuccess();
                            cds.setImage(imageurl);
                            arrayListCat.add(cds);
                        }
                        adpater = new greeting_card_adapter(getActivity(), arrayListCat);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                        gv.setLayoutManager(gridLayoutManager);
                        gv.setAdapter(adpater);
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println("exception" + e.toString());
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.somethingwrong) + ": " + e.toString(), Toast.LENGTH_SHORT).show();
                    hideProgress();
                }

            }

            @Override
            public void onFailure(Call<CardStoreData> call, Throwable t) {
                System.out.println("api fail" + t.getMessage());
                Toast.makeText(getActivity(), "Make Sure You Are Connected TO Internet", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });


    }

    private void hideProgress() {
        if(progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showDialog() {
        progressDialog.show();
    }

    // fetching image from the gallery
    public void on_Image_from_Gallery() {
        //prompting gallery
        Intent photopicker = new Intent(Intent.ACTION_PICK);
        //Where to find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String PictureDirectoryPath = pictureDirectory.getPath();
        //getting a URI representation
        Uri data = Uri.parse(PictureDirectoryPath);
        photopicker.setDataAndType(data, "image/*");
        startActivityForResult(photopicker, IMAGE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                imageUri = data.getData();
                try {
                    {
                        inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        String stringUri;
                        stringUri = imageUri.toString();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Constants.imageuripath = "";
                        intent.setClass(getActivity(), image_from_gallery.class);
                        intent.putExtra("KEY", stringUri);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.e("error ::", "" + e);
                }
            }
        }
    }

    public static int getVariable() {
        return flag_check;
    }

    public interface OnFragmentInteractionListener {
    }
}

       class greeting_card_adapter extends RecyclerView.Adapter<greeting_card_adapter.MyViewHolder> {
        public final ArrayList<CardsSuccess> lstdata;
        Context context;
           String s;
           DatabaseHelper db ;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView img,img_fav,img_un_fav;
            public MyViewHolder(View v) {
                super(v);
                img=v.findViewById(R.id.imageView1);
                img_fav=v.findViewById(R.id.imageView2);
                img_un_fav=v.findViewById(R.id.imageView3);
                db= new DatabaseHelper(context);
            }
        }
    public  greeting_card_adapter(Context applicationContext, ArrayList<CardsSuccess> arrayListCat)
    {
        this.context = applicationContext;
        this.lstdata = arrayListCat;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public greeting_card_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list, parent, false);
        greeting_card_adapter.MyViewHolder vh = new greeting_card_adapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final greeting_card_adapter.MyViewHolder holder, final int i) {
        final CardsSuccess categoriesDataStorea = lstdata.get(i);
        Picasso.with(context.getApplicationContext())
                .load(categoriesDataStorea.getImage())
                .resize(512, 512)
                .into(holder.img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {

                    }
                });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab_One.flag_check = 3;
                 s= categoriesDataStorea.getImage();
                Log.d("Send Image",""+s);
                Intent intent = new Intent();
                intent.setClass(context, image_from_gallery.class);
                intent.putExtra("Grid_Image",s);
                context.startActivity(intent);
            }
        });


         holder.img_fav.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 holder.img_un_fav.setVisibility(View.VISIBLE);
                 holder.img_fav.setVisibility(View.GONE);
                 // Inserting Contacts
                 Log.d("Insert: ", "Inserting ..");
                db.addContact(new Favourtites(i,s));
                 Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
             }
         });
        holder.img_un_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img_fav.setVisibility(View.VISIBLE);
                holder.img_un_fav.setVisibility(View.GONE);
                db.deleteContact(new Favourtites(i,s));
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lstdata.size();
    }
}