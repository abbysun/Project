package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class HouseSettingFragment extends Fragment {
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.house_setting, container, false);



            // Get ListView object
            listView = (ListView) rootView.findViewById(R.id.home_List);

            //Define Array value to show in ListView
            String[] listValues= new String[]{"Garage","House Temperature","Outdoor Weather"};

            // Define a new Adapter
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (getContext(),android.R.layout.simple_list_item_1,listValues);

            listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //item index
                int itemPosition = position;

                //Item value
                String itemValue = (String)listView.getItemAtPosition(position);

                //Alert
                Toast.makeText(getContext(),"Position: "+ itemPosition+" List Item: "
                        +itemValue, Toast.LENGTH_LONG).show();


            }
        });



            return rootView;
    }
}// end of HouseSettingFragment