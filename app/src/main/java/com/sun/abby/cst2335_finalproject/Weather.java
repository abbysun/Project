package com.sun.abby.cst2335_finalproject;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class Weather extends FragmentActivity{
 private ViewPager viewPager;
    private WeatherViewPageAdapter pgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pgAdapter = new WeatherViewPageAdapter(getSupportFragmentManager());

            viewPager.setAdapter(pgAdapter);

        }//end of onCteate
        @Override
        public void onBackPressed(){
            if (viewPager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                viewPager.setCurrentItem((viewPager.getCurrentItem() - 1));
            }
        }

}

