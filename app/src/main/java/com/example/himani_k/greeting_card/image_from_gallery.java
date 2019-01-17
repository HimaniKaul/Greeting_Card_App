package com.example.himani_k.greeting_card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class image_from_gallery extends AppCompatActivity {
    private int IMAGE_GALLERY_REQUEST =0 ;
    ImageView imageView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_from_gallery);

        imageView=(ImageView) findViewById(R.id.imageView_plus);

        //starts the crop window
        ImageButton crop= (ImageButton) findViewById(R.id.crop_1);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(image_from_gallery.this,crop_class.class);
                startActivity(i);
            }
        });

        //open gallery
        ImageButton gal= (ImageButton) findViewById(R.id.gallery);
        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_Image_from_Gallery();
            }
        });
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
            if(resultCode==RESULT_OK)
            {
                if(requestCode==IMAGE_GALLERY_REQUEST)
                {
                    Uri imageuri = data.getData();
                    InputStream inputStream;
                    try {
                         inputStream=getContentResolver().openInputStream(imageuri);
                         Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                         //setting the image in the imageView
                        imageView.setImageBitmap(bitmap);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace(); } }
            } }
}
