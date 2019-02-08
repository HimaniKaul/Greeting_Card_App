package com.example.himani_k.greeting_card;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class image_from_gallery extends AppCompatActivity {
    private int IMAGE_GALLERY_REQUEST = 0;
    static  PhotoEditorView imageView;
    Uri imageUri = null;
    Bitmap bitmap;
    InputStream inputStream;
    LinearLayout linearLayout;
    public static Context contextOfApplication;
    private String stringUri;
    static PhotoEditor mPhotoEditor; static int count;

    public static void changeImage(Bitmap bitmap, Context context){
        if(count>0)
        {  mPhotoEditor.undo();
            mPhotoEditor.addImage(bitmap);}
        else if(count ==0 )
        {   imageView.getSource().setImageBitmap(null);
            imageView.getSource().setImageBitmap(bitmap);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_from_gallery);
        contextOfApplication = getApplicationContext();

        imageView = findViewById(R.id.imageView_plus);
        linearLayout = findViewById(R.id.scroll_layout);
        mPhotoEditor = new PhotoEditor.Builder(this, imageView).build();

        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //starts the crop window
        ImageButton crop = findViewById(R.id.crop_1);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getSource().getDrawable()!=null || !mPhotoEditor.isCacheEmpty()) {
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
            }
        });

        //receiving the image first time
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("KEY")) {
            stringUri = extras.getString("KEY");
        }
        imageUri = Uri.parse(stringUri);
        //setting image on image view
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.getSource().setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //rotating the fab button
        FloatingActionsMenu fb = findViewById(R.id.fab_button);
        fb.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuCollapsed() {
                linearLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onMenuExpanded() {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        //setting undo and redo
        ImageView ig2= findViewById(R.id.imageButton2);
        ImageView ig3= findViewById(R.id.imageButton3);
        ig2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.undo();
            }});
            ig3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   mPhotoEditor.redo();
                }});
            //open gallery
        ImageButton gal = findViewById(R.id.gallery);
        gal.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_Image_from_Gallery();
                count++;
            }
        });

        //adding a delete option
        ImageView del = findViewById(R.id.imageButton1);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getSource().getDrawable() != null) {
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getSource().getDrawable();
                    bitmap = drawable.getBitmap(); //checking the current image for delete
                    imageView.getSource().setImageBitmap(null);
                    mPhotoEditor.clearAllViews(); }}
        });

        //sharing image
        ImageView share = findViewById(R.id.imageButton5);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPhotoEditor.isCacheEmpty() && imageView.getSource().getDrawable() != null)  {
                    shareImage(imageUri);
                } }});

        //downloading image
        ImageView i4 = findViewById(R.id.imageButton4);
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPhotoEditor.isCacheEmpty() && imageView.getSource().getDrawable() != null) {
                    ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getSource().getDrawable();
                    bitmap = drawable.getBitmap();
                    //path to sd card
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    //create a folder
                    File dir = new File(path + "/Greeting Card/");
                    if (!dir.exists() && dir.mkdirs()) {
                        //creating for first time
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmsshhmmss");
                        String date = sdf.format(new Date());
                        String name = "Img" + date + ".jpg";
                        String fileName = dir.getAbsolutePath() + "/" + name;
                        File new_file = new File(fileName);
                        try {
                            if (ActivityCompat.checkSelfPermission(image_from_gallery.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mPhotoEditor.saveAsFile(fileName, new PhotoEditor.OnSaveListener() {
                                @Override
                                public void onSuccess(@NonNull String imagePath) {
                                    Toast.makeText(image_from_gallery.this, "Image Saved", Toast.LENGTH_SHORT).show();
                                    Log.e("PhotoEditor", "Image Saved Successfully");
                                    imageView.getSource().setImageBitmap(null);
                                    mPhotoEditor.clearAllViews();}
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.e("PhotoEditor", "Failed to save Image");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        refresh_gallery(new_file);
                    }
                    // if directory already exists
                    if (!mPhotoEditor.isCacheEmpty() ||imageView.getSource().getDrawable() != null)  {
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyymmsshhmmss");
                    String date=sdf.format(new Date());
                    String name="Img"+date+".jpg";
                    String fileName = dir.getAbsolutePath()+"/"+name;
                    File new_file=new File(fileName);

                    try {if (ActivityCompat.checkSelfPermission(image_from_gallery.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return; }
                        mPhotoEditor.saveAsFile(fileName, new PhotoEditor.OnSaveListener() {
                            @Override
                            public void onSuccess(@NonNull String imagePath) {
                                Toast.makeText(image_from_gallery.this, "Image Saved", Toast.LENGTH_SHORT).show();
                                imageView.getSource().setImageBitmap(null);
                                mPhotoEditor.clearAllViews();
                                Log.e("PhotoEditor", "Image Saved Successfully");
                            }
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("PhotoEditor", "Failed to save Image");
                            }
                        });}
                        catch (Exception e) {e.printStackTrace(); }
                    refresh_gallery(new_file); }} }});
    }   private void refresh_gallery(File fileName) {
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri.fromFile(fileName));
        sendBroadcast(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                imageUri = data.getData();
                try {
                    inputStream =getContentResolver().openInputStream(imageUri);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    //setting the image in the imageView
                    mPhotoEditor.addImage(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } } } }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }

    // Share image
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }

}

