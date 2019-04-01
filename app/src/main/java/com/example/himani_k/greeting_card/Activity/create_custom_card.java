package com.example.himani_k.greeting_card.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.himani_k.greeting_card.R;

import java.util.Objects;

public class create_custom_card extends AppCompatActivity {
    public static int layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_card);
        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            final LinearLayout landscape=findViewById(R.id.ls1);
            final LinearLayout portrait=findViewById(R.id.pt1);
            final LinearLayout l=findViewById(R.id.card_layout);
            final LinearLayout p=findViewById(R.id.card_layout_portrait);
            landscape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p.setVisibility(View.GONE);
                    l.setVisibility(View.VISIBLE);
                }
            });
            portrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    l.setVisibility(View.GONE);
                    p.setVisibility(View.VISIBLE);
                }
            });

            FloatingActionButton land= findViewById(R.id.custom_landscape_fab);
            FloatingActionButton fab_pot= findViewById(R.id.custom_portrait_fab);
            land.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{layout=1;
                        Intent i= new Intent(create_custom_card.this, image_from_gallery.class);
                        startActivity(i);}
                    catch (Exception e)
                    {Log.e("error ::",""+e); }
                }
            });
            fab_pot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{layout=2;
                    Intent i= new Intent(create_custom_card.this, image_from_gallery.class);
                    startActivity(i);}
                    catch (Exception e)
                    {Log.e("error ::",""+e); }
                }
            }); }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }

    public static int getLayout()
    {
        return layout;
    }

}