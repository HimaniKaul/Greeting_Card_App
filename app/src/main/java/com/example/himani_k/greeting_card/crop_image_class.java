package com.example.himani_k.greeting_card;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class crop_image_class extends AppCompatActivity
{   Bitmap bitmap;
    ImageView ig;
    Uri uri;
    String stringUri;
    InputStream inputStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image1);
        ig=findViewById(R.id.bitmap_iv);

        //accepting the image
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("KEY")) {
            stringUri= extras.getString("KEY");
        }
        uri = Uri.parse(stringUri);
        //setting image on image view
        try {
            inputStream = getContentResolver().openInputStream(uri);
            bitmap= BitmapFactory.decodeStream(inputStream);
            ig.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //setting back button
        Toolbar toolbar =findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        { getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);}

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
