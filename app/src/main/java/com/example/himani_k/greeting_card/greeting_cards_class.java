package com.example.himani_k.greeting_card;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;



public class greeting_cards_class extends AppCompatActivity
        implements tab_One.OnFragmentInteractionListener,tab_two.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greeting_cards_tab);

        //tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Greeting Cards"));
        tabLayout.addTab(tabLayout.newTab().setText("Favourite"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //setting up the pages
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final page_adapter adapter = new page_adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //fragment
        tab_One fragment = new tab_One();
        fragment.getActivity();
    }

        @Override
        public void onFragmentInteraction(Uri uri) { }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) { }
}
