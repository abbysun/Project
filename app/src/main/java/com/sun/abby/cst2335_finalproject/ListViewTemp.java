package com.sun.abby.cst2335_finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sun.abby.cst2335_finalproject.dbhelperTemp.HouseTempDatabaseHelper;

import java.util.ArrayList;

public class ListViewTemp extends AppCompatActivity {
    private ListView listView;
    public static ArrayList<String> timeTempArray = new ArrayList<String>();
    protected static final String ACTIVITY_NAME = "ListViewTemp";
    private HouseTempDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listViewTemp);
        final TempAdapter messageAdapter = new TempAdapter(this);
        listView.setAdapter(messageAdapter);

        dbHelper = new HouseTempDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        //retrieve data
        cursor = database.rawQuery("select * from " + HouseTempDatabaseHelper.TABLE_NAME + " WHERE ? not null",
                new String[]{HouseTempDatabaseHelper.KEY_TEMPERATURE,HouseTempDatabaseHelper.KEY_TIME});

        Log.i(ACTIVITY_NAME, "Cursor's row count = " + cursor.getCount());
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
            Log.i(ACTIVITY_NAME, "column " + columnIndex + " " + cursor.getColumnName(columnIndex));
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String storedTemp =
                    cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TEMPERATURE));
            storedTemp += cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TIME));
            Log.i(ACTIVITY_NAME, "Stored Temperatures: " + storedTemp);
            timeTempArray.add(storedTemp);
            cursor.moveToNext();
        }
        cursor.close();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }



    private class TempAdapter extends ArrayAdapter<String>{
        public TempAdapter(Context ctx){
            super(ctx,0);


        }
        // number of rows in listView
        public int getCount(){
            return timeTempArray.size();
        }

        //position of item in the array list
        @Override
        public String getItem(int position){
            return (String)timeTempArray.get(position);
        }
        @Override
        public long getItemId(int position){
            return position;
        }

        //the layout that wil be positioned at the specified position in the list
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ListViewTemp.this.getLayoutInflater();

            //recreate your view that you made in the resource file.
            View result = inflater.inflate(R.layout.activity_list_view_temp, null);


            //From the resulting view, get the TextView which holds the string message:
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText( getItem(position)  ); // get the string at position
            return result;
        }
    }
    public void onDestory(){
        if(cursor !=null) {
            cursor.close();
        }
        if (dbHelper !=null){
            dbHelper.close();
        }
    }
}
