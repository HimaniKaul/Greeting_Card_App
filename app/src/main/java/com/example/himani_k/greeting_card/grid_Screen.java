                package com.example.himani_k.greeting_card;

                import android.content.Context;
                import android.content.Intent;
                import android.os.Bundle;
                import android.support.v4.widget.DrawerLayout;
                import android.support.v7.app.ActionBarDrawerToggle;
                import android.support.v7.app.AppCompatActivity;
                import android.support.v7.widget.Toolbar;
                import android.view.Gravity;
                import android.view.Menu;
                import android.view.View;
                import android.widget.AdapterView;
                import android.widget.GridView;

                public class grid_Screen extends AppCompatActivity {
                    Toolbar myToolbar;
                    private DrawerLayout drawerLayout;
                    Context context;

                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_grid__screen);

                        //setting toolbar
                        myToolbar = (Toolbar) findViewById(R.id.toolbar1);
                        setSupportActionBar(myToolbar);

                        //centre align text in toolbar
                       // TextView textView=(TextView) myToolbar.findViewById(R.id.toolbar_title);
                        //textView.setText(myToolbar.getTitle());
                        getSupportActionBar().setDisplayShowTitleEnabled(false);

                        if(getSupportActionBar() != null)
                        { getSupportActionBar().setDisplayHomeAsUpEnabled(true); }

                        //Grid layout
                        GridView gridview = (GridView) findViewById(R.id.gridview);
                        gridview.setAdapter(new ImageAdapter(this));
                        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v,
                                                    int position, long id) {
                                Intent i= new Intent(grid_Screen. this, greeting_cards_class.class);
                                startActivity(i);
                            }
                        });

                        //code for navigation bar
                        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
                        ActionBarDrawerToggle drawerToggle; // takes care of everything
                        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_closed);
                        drawerLayout.addDrawerListener(drawerToggle);
                        drawerToggle.syncState(); //rotating the hamburger icon
                    }

                   @Override
                        public void onBackPressed() {
                        if(drawerLayout.isDrawerOpen(Gravity.START))
                        {drawerLayout.closeDrawer(Gravity.START);}
                        else{
                        super.onBackPressed();
                    }}
                    @Override
                    public boolean onCreateOptionsMenu(Menu menu) {
                        getMenuInflater().inflate(R.menu.app_bar_contents,menu);
                        return super.onCreateOptionsMenu(menu);
                    }
                }
