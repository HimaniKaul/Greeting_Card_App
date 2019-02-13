package com.example.himani_k.greeting_card;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
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

public class image_from_gallery extends AppCompatActivity  {
    private int IMAGE_GALLERY_REQUEST = 0;
    static  PhotoEditorView imageView;
    Uri imageUri = null;
    static Bitmap bitmap;
    InputStream inputStream;
    LinearLayout linearLayout;
    public static Context contextOfApplication;
    PhotoEditorView portrait; PhotoEditorView landscape;
    private String stringUri;   int flag=0;
    static PhotoEditor mPhotoEditor; static int count, back;

    public static void changeImage(Bitmap bitmap_one, crop_image_class crop_image_class){
        if(count>0)
        {   mPhotoEditor.undo();
            mPhotoEditor.addImage(bitmap_one);}
            else if(count==0)
        {   BitmapDrawable drawable = (BitmapDrawable)imageView.getSource().getDrawable();
            bitmap= drawable.getBitmap(); //checking the current image for delete
            imageView.getSource().setImageDrawable(null);
            imageView.getSource().setImageBitmap(bitmap_one);}
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_from_gallery);
        contextOfApplication = getApplicationContext();

        imageView = findViewById(R.id.imageView_plus);
        landscape = findViewById(R.id.imageView_plus);
        linearLayout = findViewById(R.id.scroll_layout);
        portrait=findViewById(R.id.imageView_portrait);
        Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        mPhotoEditor = new PhotoEditor.Builder(this, imageView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(mTextRobotoTf)
                .build();

        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
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

        //quotes
        final ImageButton quotes1=findViewById(R.id.quotes);
        quotes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(image_from_gallery.this,quotes.class);
                startActivity(i);
            }
        });

        //stickers
        final ImageButton stickers=findViewById(R.id.sticker);
        stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(image_from_gallery.this,stickers.class);
                startActivity(i);
            }
        });

        //frame
        final ImageButton frames=findViewById(R.id.frame);
        frames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(image_from_gallery.this,Frames.class);
                startActivity(i);
            }
        });


        //receiving the image first time
        int v= tab_One.getVariable();
        int v1= create_custom_card.getLayout();
        if (v==1){ System.out.println(v);
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
        }}
        else if(v==2){
            imageView.getSource().setImageDrawable(null);
            if(v1==1){
                  landscape.setVisibility(View.VISIBLE);
                  portrait.setVisibility(View.GONE);}
                  else if(v1==2)
              {   imageView=findViewById(R.id.imageView_portrait);
                  portrait.setVisibility(View.VISIBLE);
                  landscape.setVisibility(View.GONE);
                  mPhotoEditor = new PhotoEditor.Builder(this, imageView).build(); }

        }

        //rotating the fab button
         final LinearLayout lay= findViewById(R.id.text_design);
        FloatingActionsMenu fb = findViewById(R.id.fab_button);
        fb.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuCollapsed() {
                linearLayout.setVisibility(View.GONE);
                if(flag==1){lay.setVisibility(View.GONE);flag=0;} }
            @Override
            public void onMenuExpanded() {
                linearLayout.setVisibility(View.VISIBLE);
                // calling the text layout
                 ImageButton ig_text= findViewById(R.id.textt);
                ig_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag==0)
                          {   linearLayout.setVisibility(View.GONE);
                              lay.setVisibility(View.VISIBLE);
                              flag=1;
                              TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(image_from_gallery.this);
                              textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
                              @Override
                              public void onDone(String inputText, int colorCode) {
                               mPhotoEditor.addText(inputText, colorCode);
                              }});}}});}});

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
                count++;
                on_Image_from_Gallery();}
        });

        //adding a delete option
        ImageView del = findViewById(R.id.imageButton1);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getSource().getDrawable()!= null) {
                    imageView.getSource().setImageDrawable(null);
                    mPhotoEditor.clearAllViews(); count=0; }}
        });

        //sharing image
        ImageView share = findViewById(R.id.imageButton5);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getSource().getDrawable()!=null)  {
                    shareImage(imageUri);
                } }});

        //downloading image
        ImageView i4 = findViewById(R.id.imageButton4);
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPhotoEditor.isCacheEmpty() || imageView.getSource().getDrawable()!=null) {
                    ActivityCompat.requestPermissions(image_from_gallery.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    //path to sd card
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    //create a folder
                    File dir = new File(path + "/Greeting Card/");
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
                            public void onSuccess(String imagePath) {
                                Toast.makeText(image_from_gallery.this, "Image Saved", Toast.LENGTH_SHORT).show();
                                imageView.getSource().setImageDrawable(null);
                                mPhotoEditor.clearAllViews(); count=0;}
                            @Override
                            public void onFailure(Exception exception) {
                                Log.e("PhotoEditor", "Failed to save Image");
                                Log.e("error ::",""+exception);
                            }});}
                        catch (Exception e) {e.printStackTrace(); }
                    refresh_gallery(new_file); } }}); }
                    private void refresh_gallery(File fileName) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        count=0;
    }


}

