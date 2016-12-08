package com.sun.abby.cst2335_finalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.sun.abby.cst2335_finalproject.WeatherFragmentOne;
import com.sun.abby.cst2335_finalproject.Weather_Fragment;

/**
 * This class extends FragmentPagerAdapter which  host ViewPager
 * Created by yun on 2016-12-04.
 */

public class WeatherViewPageAdapter extends FragmentPagerAdapter {
    public WeatherViewPageAdapter (FragmentManager fm){
        super(fm);
    }

    /**
     * To get each fragment page
     * @param arg0 get the page of each fragment
     * @return null
     */

    @Override
    public Fragment getItem(int arg0){
        switch (arg0){
            case 0:
                return new WeatherFragmentOne();
            case 1:
                return new Weather_Fragment();
            default:
                break;
        }
        return null;
    }

    /**
     * To count how many of fragment page in the viewPager
     * @return fragment page number
     */
    @Override
    public int getCount(){
        return 2;
    }
}// end of class WeatherViewPageAdapter
