package com.sun.abby.cst2335_finalproject;

import android.content.Context;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class HouseSettingFragment extends Fragment {
    private ListView listView;
    ArrayList<String> listValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.house_setting, container, false);



            // Get ListView object
            listView = (ListView) rootView.findViewById(R.id.home_List);

            //Define Array value to show in ListView
          //  String[] listValues= new String[]{"Garage","House Temperature","Outdoor Weather"};
        listValue = new ArrayList<>();
        listValue.add(getString(R.string.house_garage));
        listValue.add(getString(R.string.house_tempsetting));
        listValue.add(getString(R.string.house_weather));



            // Define a new Adapter
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (getContext(),android.R.layout.simple_list_item_1,listValue);

            listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //item index
                int itemPosition = position;

                //Item value
                String itemValue = (String)listView.getItemAtPosition(position);

                //Alert
                Toast.makeText(getContext(),
                        itemValue+" has been selected", Toast.LENGTH_LONG).show();

                //click to open each activity
                switch (position)
                {
                    case 0:
                        Context context = view.getContext();
                        Intent garageActivity = new Intent(context, Garage.class);
                        startActivity(garageActivity);
                        break;

                    case 1:
                        Context ctx = view.getContext();
                        Intent houseTempActivity = new Intent(ctx, HouseTemp.class);
                        startActivity(houseTempActivity);
                        break;

                    case 2:
                        Context context1 = view.getContext();
                        Intent weatherActivity = new Intent(context1, Weather.class);
                        startActivity(weatherActivity);
                        break;
                }


            }
        });

        Button button = (Button) rootView.findViewById(R.id.about);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),
                        "House Setting by Yun Luo, Version 1.0.0."+'\n' +'\n'+
                                "Click on the Items to setup the control on House temprature, Garage Setting and Weather Forecast.", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }//end of onCreateView



}// end of HouseSettingFragment