package com.example.himani_k.greeting_card;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.ViewType;

public class image_from_gallery extends AppCompatActivity implements  s.StickerListener, frame.FrameListener, MyCustomdialog.OnInputListener{
    private int IMAGE_GALLERY_REQUEST = 0;
    static  PhotoEditorView imageView;
    Uri imageUri,share_uri = null;
    static Bitmap bitmap;
    InputStream inputStream;
    LinearLayout linearLayout;
    private s mStickerBSFragment;
    private frame mframeBSFragment;
    public static Context contextOfApplication;
    PhotoEditorView portrait; PhotoEditorView landscape;
    private String stringUri;   int flag=0;
    static PhotoEditor mPhotoEditor; static int count, back;
    private ArrayList<String> categories;
    private ArrayAdapter<String> dataAdapter;
    private ArrayList<String> categories1;
    private ArrayAdapter<String> dataAdapter1;
    int undo,download;
    public static int first;
    View view;
    Typeface mTextRobotoTf;
    int new_dialog=0;


    public static void changeImage(Bitmap bitmap_one, crop_image_class crop_image_class){
        if(count>0)
        { if(imageView.getSource().getDrawable()!=null && first==1)
        {   BitmapDrawable drawable = (BitmapDrawable)imageView.getSource().getDrawable();
            bitmap= drawable.getBitmap(); //checking the current image for delete
            imageView.getSource().setImageDrawable(null);
            imageView.getSource().setImageBitmap(bitmap_one);
            imageView.getSource().setAdjustViewBounds(true);
            imageView.getSource().setScaleType(ImageView.ScaleType.FIT_XY); }
        else{
            mPhotoEditor.undo();
            mPhotoEditor.addImage(bitmap_one);
        } }
        else if(count==0)
        {   BitmapDrawable drawable = (BitmapDrawable)imageView.getSource().getDrawable();
            bitmap= drawable.getBitmap(); //checking the current image for delete
            imageView.getSource().setImageDrawable(null);
            imageView.getSource().setImageBitmap(bitmap_one);
            imageView.getSource().setAdjustViewBounds(true);
            imageView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_from_gallery);
        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        contextOfApplication = getApplicationContext();
        mStickerBSFragment = new s();
        mStickerBSFragment.setStickerListener(this);
        mframeBSFragment = new frame();
        mframeBSFragment.setmFrameListener(this);
        imageView = findViewById(R.id.imageView_plus);
        landscape = findViewById(R.id.imageView_plus);
        linearLayout = findViewById(R.id.scroll_layout);
        portrait=findViewById(R.id.imageView_portrait);

//        final MyCustomdialog dialog = new MyCustomdialog();

        mTextRobotoTf= ResourcesCompat.getFont(this, R.font.roboto_medium);
        mPhotoEditor = new PhotoEditor.Builder(this, imageView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(mTextRobotoTf)
                .build();

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            back++;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //starts the crop window
        ImageButton crop = findViewById(R.id.crop_1);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( undo!=1 &&(imageView.getSource().getDrawable()!=null || !mPhotoEditor.isCacheEmpty())) {
                    try {   imageView.buildDrawingCache();
                        bitmap = imageView.getDrawingCache();
                        String stringUri;
                        stringUri = imageUri.toString();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setClass(image_from_gallery.this, crop_image_class.class);
                        intent.putExtra("KEY", stringUri);
                        startActivity(intent);
                    }
                    catch (Exception e) {
                        Log.e("error ::", "" + e);
                    }}

                else if(undo==1){
                    Toast.makeText(image_from_gallery.this, "You have undo the action", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(image_from_gallery.this, "Please Set the Image First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //quotes
        final ImageButton quotes1=findViewById(R.id.quotes);
        quotes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(image_from_gallery.this,quotes.class);
                startActivity(i);
            }
        });

        // text dialog box
        ImageButton text= findViewById(R.id.textt);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCustomdialog dialog=new MyCustomdialog();
                dialog.show(getSupportFragmentManager(),null);
            }
        });

        //long press
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onEditTextChangeListener(View rootView, String text, int colorCode) {
                new_dialog++;
                rootView.setVisibility(View.GONE);
                MyCustomdialog dialog=new MyCustomdialog();
                dialog.show(getSupportFragmentManager(),null);
            }
            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
            }
            @Override
            public void onRemoveViewListener(int numberOfAddedViews) { }

            @Override
            public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
            }

            @Override public void onStartViewChangeListener(ViewType viewType) {
            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) { }
        });


        //stickers
        final ImageButton stickers=findViewById(R.id.sticker);
        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());}
        });


        //frame
        final ImageButton frames=findViewById(R.id.frame);
        frames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mframeBSFragment.show(getSupportFragmentManager(), mframeBSFragment.getTag());}
                catch(Exception e){
                    Log.e("error",""+e);
                }
            }
        });

        //receiving the image first time
        int v= tab_One.getVariable();
        int v1= create_custom_card.getLayout();
        if (v==1){
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("KEY")){
                stringUri = extras.getString("KEY");
                Constants.imageuripath="";
                imageUri = Uri.parse(stringUri);
            }
            //setting image on image view
            try {
                imageView.getSource().setImageDrawable(null);
                inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.getSource().setImageBitmap(bitmap);
                imageView.getSource().setAdjustViewBounds(true);
                imageView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
            }}
        else if(v==2) {
            imageView.getSource().setImageDrawable(null);
            if (v1 == 1) {
                landscape.setVisibility(View.VISIBLE);
                portrait.setVisibility(View.GONE);
            } else if (v1 == 2) {
                portrait.setVisibility(View.VISIBLE);
                landscape.setVisibility(View.GONE);
                imageView=findViewById(R.id.imageView_portrait);
                mPhotoEditor = new PhotoEditor.Builder(this, imageView)
                        .setPinchTextScalable(true)
                        .setDefaultTextTypeface(mTextRobotoTf)
                        .build(); } }
        else{
            imageView.getSource().setImageDrawable(null);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("Grid_Image")) {
                stringUri = extras.getString("Grid_Image");
                imageUri = Uri.parse(stringUri); }

            //setting image on image view
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.getSource().setImageBitmap(bitmap);
                imageView.getSource().setAdjustViewBounds(true);
                imageView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
            }}

        //rotating the fab button
        FloatingActionsMenu fb = findViewById(R.id.fab_button);
        fb.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuCollapsed() {
                linearLayout.setVisibility(View.GONE);
                    flag=0;}
            @Override
            public void onMenuExpanded() {
                linearLayout.setVisibility(View.VISIBLE);
            }});

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });

        //setting undo and redo
        ImageView ig2= findViewById(R.id.imageButton2);
        ImageView ig3= findViewById(R.id.imageButton3);
        ig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo=1;
                 if(imageView.getSource().getDrawable()==null){
                    Toast.makeText(image_from_gallery.this, "Please set the image first", Toast.LENGTH_SHORT).show();
                }
                mPhotoEditor.undo();

            }});
        ig3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo=0;
               if(imageView.getSource().getDrawable()==null){
                    Toast.makeText(image_from_gallery.this, "Please set the image first", Toast.LENGTH_SHORT).show();
                }
                mPhotoEditor.redo();
            }});

        //sharing image
        final ImageView share = findViewById(R.id.imageButton5);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (download==1 && imageView.getSource().getDrawable()!=null )  {
                        shareImage(share_uri);
                    }
                    else if(download==0){
                         share_before_download();
                    }

                     if(imageView.getSource().getDrawable()==null){
                        Toast.makeText(image_from_gallery.this, "Please set the image first", Toast.LENGTH_SHORT).show();
                    }
            }});

        //open gallery
        ImageButton gal = findViewById(R.id.gallery);
        gal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count++;
                        on_Image_from_Gallery();
                    }
                });

        //adding a delete option
        ImageView del = findViewById(R.id.imageButton1);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getSource().getDrawable()!= null || !mPhotoEditor.isCacheEmpty()) {
                    imageView.getSource().setImageDrawable(null);
                    mPhotoEditor.clearAllViews(); }
                else{
                    Toast.makeText(image_from_gallery.this, "Please Set the Image First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //downloading image
        ImageView i4 = findViewById(R.id.imageButton4);
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }});}


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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                imageUri = data.getData();
                try {
                    inputStream =getContentResolver().openInputStream(imageUri);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    //setting the image in the imageView
                    if(imageView.getSource().getDrawable()==null)
                    {   first=1;
                        imageView.getSource().setImageBitmap(bitmap);
                        imageView.getSource().setAdjustViewBounds(true);
                        imageView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else{
                        first=0;
                        mPhotoEditor.addImage(bitmap);}
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } } } }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            count=0;
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }

    // Share image
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using")); }

    //show dialog box
    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit without saving image ?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(imageView.getSource().getDrawable()!=null || !mPhotoEditor.isCacheEmpty()){
                    saveImage();}
                else{
                    Toast.makeText(image_from_gallery.this, "Please Set the Image First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }
    @SuppressLint("MissingPermission")
    private void saveImage()
    {
        if (imageView.getVisibility() == View.GONE) {
            if (portrait.getSource().getDrawable() != null || !mPhotoEditor.isCacheEmpty()) {
                try {
                    ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.
                            WRITE_EXTERNAL_STORAGE}, 1);
                    File file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + ""
                            + System.currentTimeMillis() + ".png");
                    Log.d("file path", file + "");
                    try {
                        file.createNewFile();
                        SaveSettings saveSettings = new SaveSettings.Builder()
                                .setClearViewsEnabled(true)
                                .setTransparencyEnabled(true)
                                .build();

                        mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                            @Override
                            public void onSuccess(@NonNull String imagePath) {
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());
                                Toast.makeText(image_from_gallery.this, "Success", Toast.LENGTH_SHORT).show();
                                imageView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                                share_uri=Uri.fromFile(new File(imagePath)); download=1;
                            }
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(image_from_gallery.this, "Failure" + exception, Toast.LENGTH_SHORT).show();
                                Log.e("Error save image::", "" + exception); download=0;

                            }
                        });
                    } catch (IOException e) {
                        Log.e("error", "" + e);
                    }
                    refresh_gallery(file);
                } catch (Exception e) {
                    Log.e("error", "" + e);
                }
            }
        }
        else {
            if (imageView.getSource().getDrawable() != null || !mPhotoEditor.isCacheEmpty()) {
                try {
                    ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.
                            WRITE_EXTERNAL_STORAGE}, 1);
                    File file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + ""
                            + System.currentTimeMillis() + ".png");
                    Log.d("file path", file + "");
                    try {
                        file.createNewFile();
                        SaveSettings saveSettings = new SaveSettings.Builder()
                                .setClearViewsEnabled(true)
                                .setTransparencyEnabled(true)
                                .build();

                        mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                            @Override
                            public void onSuccess(@NonNull String imagePath) {
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());
                                Toast.makeText(image_from_gallery.this, "Success", Toast.LENGTH_SHORT).show();
                                imageView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                                share_uri=Uri.fromFile(new File(imagePath)); download=1;
                            }
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(image_from_gallery.this, "Failure" + exception, Toast.LENGTH_SHORT).show();
                                download=0;
                                Log.e("Error save image::", "" + exception);
                            }
                        });
                    } catch (IOException e) {
                        Log.e("error", "" + e);
                    }
                    refresh_gallery(file);
                } catch (Exception e) {
                    Log.e("error", "" + e);
                }
            }
        }
    }
    private void refresh_gallery(File fileName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri.fromFile(fileName));
        sendBroadcast(intent); }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         //share if not downloaded
        @SuppressLint("MissingPermission")
        private void share_before_download()
        {
            if (imageView.getVisibility() == View.GONE) {
                if (portrait.getSource().getDrawable() != null || !mPhotoEditor.isCacheEmpty()) {
                    try {
                        ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, 1);
                        File file = new File(Environment.getExternalStorageDirectory()
                                + File.separator + ""
                                + System.currentTimeMillis() + ".png");
                        Log.d("file path", file + "");
                        try {
                            file.createNewFile();
                            SaveSettings saveSettings = new SaveSettings.Builder()
                                    .setClearViewsEnabled(true)
                                    .setTransparencyEnabled(true)
                                    .build();

                            mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                                @Override
                                public void onSuccess(@NonNull String imagePath) {
                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());
                                    imageView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                                    share_uri=Uri.fromFile(new File(imagePath)); shareImage(share_uri);
                                }
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.d("Error","Sorry");
                                }
                            });
                        } catch (IOException e) {
                            Log.e("error", "" + e);
                        }
                        refresh_gallery_on_share(file);
                    } catch (Exception e) {
                        Log.e("error", "" + e);
                    }
                }
            }
            else {
                if (imageView.getSource().getDrawable() != null || !mPhotoEditor.isCacheEmpty()) {
                    try {
                        ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, 1);
                        File file = new File(Environment.getExternalStorageDirectory()
                                + File.separator + ""
                                + System.currentTimeMillis() + ".png");
                        Log.d("file path", file + "");
                        try {
                            file.createNewFile();
                            SaveSettings saveSettings = new SaveSettings.Builder()
                                    .setClearViewsEnabled(true)
                                    .setTransparencyEnabled(true)
                                    .build();

                            mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                                @Override
                                public void onSuccess(@NonNull String imagePath) {
                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());
                                    imageView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
                                    share_uri=Uri.fromFile(new File(imagePath)); shareImage(share_uri);
                                }
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.d("Error","Sorry");
                                }
                            });
                        } catch (IOException e) {
                            Log.e("error", "" + e);
                        }
                        refresh_gallery_on_share(file);
                    } catch (Exception e) {
                        Log.e("error", "" + e);
                    }
                }
            }
        }
    private void refresh_gallery_on_share(File fileName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri.fromFile(fileName));
        sendBroadcast(intent); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        count=0;
        bitmap=null;
    }

    @Override
    public void onResume() {
        if(!quotes.quote.equals(""))
        { mPhotoEditor.addText(quotes.quote,getColor(R.color.purple));
          quotes.quote="";
        }
        super.onResume();
    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
    }
    @Override
    public void onFrameClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
    }

    @Override
    public void sendInput(String input,int color) {
        mPhotoEditor.addText(input,color);
    }}






