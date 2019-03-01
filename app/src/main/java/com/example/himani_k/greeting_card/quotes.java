package com.example.himani_k.greeting_card;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class quotes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String quote="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotes_recycler_view);
        mRecyclerView =findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        //setting back button
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final String[] myDataset={"You have as much laughter as you have faith",
                             "The brain is wiser than sky",
                             "A goal should scare you a little and excite you a lot",
                             "Great things never come from comfort",
                             "We did not came to fear the future we came to shape it",
                             "There is no genius without the mixture of madness",
                             "Adversity reveals the genius , prosperity conceals it"};
        mAdapter = new MyAdapter(myDataset,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener( // and the click is handled
                new RecyclerClickListener(quotes.this, new RecyclerClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d("click item", String.valueOf(position));
                        TextView textView = view.findViewById(R.id.text_quote);
                        quote=textView.getText().toString();
                        Log.d("click item", quote);
                        onBackPressed();
                    }
                }));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }return super.onOptionsItemSelected(item); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
