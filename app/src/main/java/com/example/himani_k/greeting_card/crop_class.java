package com.example.himani_k.greeting_card;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

public class crop_class extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.crop_image_);
         //setting back button
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            // add back arrow to toolbar
            if (getSupportActionBar() != null)
             { getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               getSupportActionBar().setDisplayShowHomeEnabled(true);}




    }
}