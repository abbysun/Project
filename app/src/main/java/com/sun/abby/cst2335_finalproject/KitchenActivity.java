package com.sun.abby.cst2335_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class KitchenActivity extends AppCompatActivity {
    private String [] appliance = {"Microwave", "Fridge", "Smart Lighting"};
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        //find ListView and populate it
        ArrayAdapter adapter = new ArrayAdapter<String> (this, R.layout.kitchen_listview_layout, appliance);
        listView = (ListView) findViewById(R.id.kitchen_list_view);
        listView.setAdapter(adapter);

        //set click listerner function to ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch(position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "clicked microwave", Toast.LENGTH_SHORT).show();
                        Intent microwaveIntent = new Intent(getApplicationContext(), MicrowaveActivity.class);
                        startActivity(microwaveIntent);
                        break;

//                    case 1:
//                        Toast.makeText(getApplicationContext(), "clicked Fridge", Toast.LENGTH_SHORT).show();
//                        Intent fridgeIntent = new Intent(getApplicationContext(), FridgeActivity.class);
//                        startActivity(fridgeIntent);
//                        break;

                    case 2:
                        Toast.makeText(getApplicationContext(), "clicked Smart Lighting", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
