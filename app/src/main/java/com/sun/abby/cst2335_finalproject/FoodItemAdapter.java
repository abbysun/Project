package com.sun.abby.cst2335_finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abby on 2016-12-05.
 */

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<FoodItem> foodItems;

    public FoodItemAdapter(Context ctx, int layoutResourceId, ArrayList<FoodItem> items){
        super(ctx, layoutResourceId, items);
        this.context = ctx;
        this.layoutResourceId = layoutResourceId;
        this.foodItems = items;
    }

    public int getCount(){
        return foodItems.size();
    }

    public FoodItem getItem(int position){
        return foodItems.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View addView = inflater.inflate(layoutResourceId, parent, false);
        TextView text  = (TextView) addView.findViewById(R.id.food_item_list);
        text.setText(getItem(position).toString());
        return addView;
    }
}
