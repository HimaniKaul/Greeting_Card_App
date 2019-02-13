package com.example.himani_k.greeting_card;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.Objects;

public class stickers extends AppCompatActivity{
        protected void onCreate(Bundle savedInstanceState)
        {   super.onCreate(savedInstanceState);
            setContentView(R.layout.stickers);
            //setting back button
            Toolbar toolbar = findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            // add back arrow to toolbar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);}
                //Grid layout
                GridView gridview = findViewById(R.id.grid_view1);
                gridview.setAdapter(new sticker_adapter(this));
        }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }
}