package com.example.himani_k.greeting_card.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.drm.ProcessedData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.himani_k.greeting_card.Constant.Constants;
import com.example.himani_k.greeting_card.Interface.RequestInterface;
import com.example.himani_k.greeting_card.Module.Frames.FrameStoreData;
import com.example.himani_k.greeting_card.Module.Frames.FrameSuccess;
import com.example.himani_k.greeting_card.Module.Stickers.StickerStoreData;
import com.example.himani_k.greeting_card.Module.Stickers.StickerSuccess;
import com.example.himani_k.greeting_card.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class s extends BottomSheetDialogFragment {

    private ArrayList<StickerStoreData> arrayListCat;
    private RecyclerView rvEmoji;
    ProgressDialog progressDialog;

    public s() {
        // Required empty public constructor
    }

    private StickerListener mStickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        mStickerListener = stickerListener;
    }

    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            } }
            @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);

         arrayListCat= new ArrayList<>();
         rvEmoji=contentView.findViewById(R.id.rvEmoji);

         progressDialog=new ProgressDialog(getActivity());
         progressDialog.setMessage("Loading...");

        ImageView back=contentView.findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.setContentView(contentView);
        if(dialog != null) {
            contentView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
             ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
         }
        ((View) contentView.getParent()).setFitsSystemWindows(true);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
        contentView.measure(0,0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);
        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        params.height = screenHeight;
        ((View) contentView.getParent()).setLayoutParams(params);


        callStickersApi();

    }

    private void callStickersApi() {

        showProgressDialog();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        final Call<StickerSuccess> callFbLogin = requestInterface.getStickerData();
        callFbLogin.enqueue(new Callback<StickerSuccess>() {
            @Override
            public void onResponse(Call<StickerSuccess> call, Response<StickerSuccess> response) {
                try {
                    hideProgressDialog();
                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getStickers().size(); i++) {
                            String sticker = response.body().getStickers().get(i).getImage();
                            StickerStoreData categoriesDataStore = new StickerStoreData();
                            categoriesDataStore.setSticker(sticker);

                            arrayListCat.add(categoriesDataStore);

                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                            rvEmoji.setLayoutManager(gridLayoutManager);
                            StickerAdapter stickerAdapter = new StickerAdapter(getActivity(), arrayListCat);
                            rvEmoji.setAdapter(stickerAdapter);
                        }
                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println("exception" + e.toString());
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.somethingwrong) + ": " + e.toString(), Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<StickerSuccess> call, Throwable t) {
                System.out.println("api fail" + t.getMessage());
                Toast.makeText(getActivity(), "Make Sure You Are Connected To Internet", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if(progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
     class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        public final ArrayList<StickerStoreData> lstdata;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imgframe;

            public ViewHolder(View itemView) {
                super(itemView);
                imgframe = itemView.findViewById(R.id.imgSticker);
            }
        }

        public StickerAdapter(Context applicationContext, ArrayList<StickerStoreData> arrayListCat) {
            this.context = applicationContext;
            this.lstdata = arrayListCat;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new ViewHolder(view);
        }

         @Override
          public void onBindViewHolder(ViewHolder holder, final int i) {
            final StickerStoreData categoriesDataStorea = lstdata.get(i);
            Picasso.with(context.getApplicationContext())
                    .load(categoriesDataStorea.getSticker())
                    .resize(512, 512)
                    .into(holder.imgframe, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {

                        }
                    });
            final String bitmap=categoriesDataStorea.getSticker();
            holder.imgframe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mStickerListener != null) {
                        mStickerListener.onStickerClick(
                                stringToBitmap(bitmap)
                        );
                    }
                    dismiss();
                }
            });
        }

        private Bitmap stringToBitmap(String bitmap) {
            try {
                URL url = new URL(bitmap);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap1 = BitmapFactory.decodeStream(input);
                Log.e("convert bitmap", "" + myBitmap1);
                return myBitmap1;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return lstdata.size();
        }

    }}
