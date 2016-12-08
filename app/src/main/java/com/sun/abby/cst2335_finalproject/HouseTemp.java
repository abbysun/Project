package com.sun.abby.cst2335_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sun.abby.cst2335_finalproject.dbhelperTemp.HouseTempDatabaseHelper;


/**
 * This is the main activity of House Temperature setting extends AppCompatActivity
 * @author yun
 */
public class HouseTemp extends AppCompatActivity{
    /**
     *  btnTimePicker Button object for TimePicker widget
     */
    Button btnTimePicker;
    /**
     * btnSentSeting Button object to add temperature setting
     */
    Button btnSentSeting;
    /**
     * txtTime EditText object to hold time value
     */
    EditText txtTime;
    /**
     * txtTemperatue EditText object to hold tempture value
     */
    EditText txtTemperatue;

    //Variables for ListView
    /**
     * listView ListView object
     */
    private ListView listView;
    /**
     *   timeTempArray ArrayList<String> object
     */
    public ArrayList<String> timeTempArray;
    /**
     * ACTIVITY_NAME is HouseTemp
     */
    protected static final String ACTIVITY_NAME = "HouseTemp";
    /**
     * dbHelper HouseTempDatabaseHelper object
     */
    private HouseTempDatabaseHelper dbHelper;
    /**
     *  database SQLiteDatabase object
     */
    private SQLiteDatabase database;
    /**
     * cursor Cursor object
     */
    private Cursor cursor;

    /**
     * To initialize the HouseTemp activity
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeTempArray = new ArrayList<String>();
        //Snackbar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtTime = (EditText) findViewById(R.id.in_time);

        //Database listview
        listView = (ListView) findViewById(R.id.temp_data_listview);
        final TempAdapter messageAdapter = new TempAdapter(this);
        listView.setAdapter(messageAdapter);

        txtTemperatue = (EditText) findViewById(R.id.input_temp);
        btnSentSeting = (Button) findViewById(R.id.sent_db);

        dbHelper = new HouseTempDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        //retrieve data
        cursor = database.rawQuery("select * from " + HouseTempDatabaseHelper.TABLE_NAME + " WHERE ? not null",
                new String[]{HouseTempDatabaseHelper.KEY_TEMPERATURE});

        Log.i(ACTIVITY_NAME, "Cursor's row count = " + cursor.getCount());
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
            Log.i(ACTIVITY_NAME, "column " + columnIndex + " " + cursor.getColumnName(columnIndex));
        }
        //Display Stored temp data
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

           // String storedTemp = cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TEMPERATURE))
             //       .concat("  ").concat(cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TIME)));
            String storedTemp = cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TIME))+"     "
                    + cursor.getString(cursor.getColumnIndex(HouseTempDatabaseHelper.KEY_TEMPERATURE))+"C\u00b0";
            Log.i(ACTIVITY_NAME, "Stored Temperatures: " + storedTemp);
            timeTempArray.add(storedTemp);
            cursor.moveToNext();
        }
        cursor.close();

        // click to send message to array list(yun add)
        btnSentSeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inTime = txtTime.getText().toString().trim();
                String inTemp = txtTemperatue.getText().toString().trim();

                if((!inTime.equals("")) && (!inTemp.equals(""))){
                    timeTempArray.add(inTime + "  " + inTemp + "C\u00b0");

                    //insert chat messages to database
                    ContentValues initialValues = new ContentValues();
                    initialValues.put(HouseTempDatabaseHelper.KEY_TIME, inTime);
                    initialValues.put(HouseTempDatabaseHelper.KEY_TEMPERATURE, inTemp);
                    database.insert(HouseTempDatabaseHelper.TABLE_NAME, null, initialValues);
                    //lab4
                    //this restarts the process of getCount()/ getView()
                    messageAdapter.notifyDataSetChanged();
                    // make chat window to blank after sent message
                    txtTime.setText(" ");
                    txtTemperatue.setText(" ");
                }

            }

        });

        // delete listView item from database
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //item index
                final int itemPosition = position;

                //Item value
                String itemValue = (String) listView.getItemAtPosition(position);

                AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(HouseTemp.this);
                DialogBuilder.setTitle("Do you want to delete this setting?");
                // Add the buttons
                DialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        //finish();
                        Log.d("DEBUg", itemPosition+" position");
                        String item = timeTempArray.get(itemPosition).split(" ")[0];
                        timeTempArray.remove(itemPosition);

                        if(database.delete(HouseTempDatabaseHelper.TABLE_NAME, HouseTempDatabaseHelper.KEY_TIME
                                + "= '" + item+"' ", null)> 0){
                            Log.d("DEBUg", "deleted item");
                        };
                        messageAdapter.notifyDataSetChanged();
                    }
                });
                DialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();

                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = DialogBuilder.create();
                dialog.show();

            }

        });

        //timepicker
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(HouseTemp.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(HouseTemp.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        //snackbar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Set your room temperature here", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }// end of onCreate()

    /**
     *The activity is finishing or has been destroyed by system
     */
    public void onDestroy(){

        if(cursor !=null) {
            cursor.close();
        }
        if (dbHelper !=null){
            dbHelper.close();
        }
        database.close();
        super.onDestroy();
    }


    // menu bar

    /**
     *To create option menu
     * @param m Menu object
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_garage, m);
        return true;

    }

    /**
     * To specify the selected item on menu
     * @param mi MenuItem object
     * @return specified the selected item
     */
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch (id) {
            case R.id.garage_goback:
                Log.d("Toolbar", "Option 1 selected");
                //  Snackbar.make(getWindow().getDecorView(), "Go back menu has been selected", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(HouseTemp.this);
                builder.setTitle("Do you want to go Main menu?");
                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();

                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

        }// end of switch
        return true;
    }//onOptionsItemSelected

    /**
     * inner class extends ArrayAdapter to record in a array
     */
    private class TempAdapter extends ArrayAdapter<String> {
        public TempAdapter(Context ctx){
            super(ctx,0);


        }

        /**
         * number of rows in listView
         * @return how many records in the listview
         */
        public int getCount(){
            return timeTempArray.size();
        }

        /**
         * position of item in the array list
         * @param position position of item in the array list
         * @return the String value of the position
         */
        @Override
        public String getItem(int position){
            return (String)timeTempArray.get(position);
        }

        /**
         * position of item in the array list
         * @param position position of item in the array list
         * @return value of the position
         */
        @Override
        public long getItemId(int position){
            return position;
        }
        //the layout that wil be positioned at the specified position in the list
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = HouseTemp.this.getLayoutInflater();

            //recreate your view that you made in the resource file.
            View result = null ;
                result = inflater.inflate(R.layout.temp_listview, null);

            //From the resulting view, get the TextView which holds the string message:
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText( getItem(position)  ); // get the string at position

            return result;

        }


    }// end of inner class TempAdapter

}
