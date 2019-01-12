package com.example.himani_k.greeting_card;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class page_adapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public page_adapter(FragmentManager fg, int NoOfTabs)
    {
        super(fg);
        this.mNoOfTabs=NoOfTabs;

    }

    @Override
    public Fragment getItem(int i) {
      switch (i){

          case 0:
              tab_One tab1=new tab_One();
              return tab1;
          case 1:
              tab_two tab2= new tab_two();
              return tab2;
          default:
              return null;
      } }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
