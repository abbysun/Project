package com.sun.abby.cst2335_finalproject;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The freezer saves food items and descriptions in a database using asynchtask
 * The database access is gained through FridgeDatabaseHelper.java
 * This is the freezer fragment class for the freezer activity
 * This is the freezer fragment class for the freezer activity
 * It contains behavioural implementations for views in the fragment_freezer.xml
 * Created by Abby on 2016-12-03.
 */
public class FreezerFragment extends Fragment {
    private ArrayList<FoodItem> food = new ArrayList<FoodItem>();
    private ListView freezerItem;
    private FridgeDatabaseHelper helper;
    private FoodItemAdapter adapter;
    private FoodItem selectedItem = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //linking layout to view
        View rootView = inflater.inflate(R.layout.fragment_freezer, container, false);

        //find fridge ListView and populate it
        adapter = new FoodItemAdapter(this.getContext(), R.layout.kitchen_listview_layout, food);
        freezerItem = (ListView) rootView.findViewById(R.id.freezer_item);
        freezerItem.setAdapter(adapter);

        //using AsyncTask to open database and populate arraylist
        new OpenFreezerDatabase().execute();

        //add listview click function
        freezerItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (int i = 0; i < food.size(); i++){
                    if(position == i){
                        selectedItem = food.get(i); //remember selected item for item removal purposes
                        Toast.makeText(getContext(), selectedItem.getName() + ": " + selectedItem.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //add seekbar and temperature display features
        final TextView tempText = (TextView) rootView.findViewById(R.id.freezer_temp_display);
        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.freezer_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i - 20; //progress value
                tempText.setText(progress + "°C");
                Log.i("Seekbar_progressChanged","progress value is " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        return rootView;
    }

    /**
     * Updates array list when user adds/deletes new food items
     */
    public void updateListAdd(String name, String description){
        food.add(new FoodItem(name, description));
        adapter.notifyDataSetChanged();
    }

    /**
     * Remove fridge data
     */
    public void updateListDelete(){
        FoodItem temp = null;
        for (FoodItem item : food){
            if (item.getId() == getSelectedItem().getId()){
                temp = item;
            }
        }
        food.remove(temp);
        adapter.notifyDataSetChanged();
    }

    /**
     * @return the selected listview item
     */
    public FoodItem getSelectedItem(){
        return selectedItem;
    }

    private class OpenFreezerDatabase extends AsyncTask<String, String, Cursor> {
        // Open database and access table values
        @Override
        protected Cursor doInBackground(String... tableName) {
            helper = new FridgeDatabaseHelper(getActivity());
            Cursor cursor = helper.getFreezerData();

            Log.i("OpenFreezer_background", "check cursor number:" + cursor.getCount());
            return cursor;
        }

        // populate listview with database data
        protected void onPostExecute(Cursor cursor) {
            Log.i("OpenFreezer_done", "check cursor number:" + cursor.getCount());

            while(cursor.moveToNext()){
                FoodItem item = new FoodItem(cursor
                        .getInt(cursor.getColumnIndex(helper.COLUMN1)), cursor.getString(cursor.getColumnIndex(helper.COLUMN2)), cursor.getString(cursor.getColumnIndex(helper.COLUMN3)));
                food.add(item);
                Log.i("OpenFridge_Done", "Fridge Item: " + item.toString());
            }
        }
    }
}
