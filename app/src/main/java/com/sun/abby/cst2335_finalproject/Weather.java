package com.sun.abby.cst2335_finalproject;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Weather Class extends FragmentActivity. This is the base page(container for 2 fragment pages)
 */
public class Weather extends FragmentActivity{
    /**
     * viewPager ViewPager object
     */
 private ViewPager viewPager;
    /**
     * pgAdapter WeatherViewPageAdapter object which is ViewPageAdapter object
     */
    private WeatherViewPageAdapter pgAdapter;

    /**
     * This method is to initilize all activities of weather page contains
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pgAdapter = new WeatherViewPageAdapter(getSupportFragmentManager());

            viewPager.setAdapter(pgAdapter);

        }//end of onCreate

    /**
     *User leave the activity
     */
        @Override
        public void onBackPressed(){
            if (viewPager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                viewPager.setCurrentItem((viewPager.getCurrentItem() - 1));
            }
        }

}

