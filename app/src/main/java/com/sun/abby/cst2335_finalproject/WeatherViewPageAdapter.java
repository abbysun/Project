package com.sun.abby.cst2335_finalproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.sun.abby.cst2335_finalproject.WeatherFragmentOne;
import com.sun.abby.cst2335_finalproject.Weather_Fragment;

/**
 * Created by yun on 2016-12-04.
 */

public class WeatherViewPageAdapter extends FragmentPagerAdapter {
    public WeatherViewPageAdapter (FragmentManager fm){
        super(fm);
    }

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
    @Override
    public int getCount(){
        return 2;
    }
}// end of class WeatherViewPageAdapter
