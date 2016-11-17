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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class LivingRoomFragment extends Fragment {

    ListView lrListView;
    ArrayList<String> lrArray;
    ArrayAdapter<String> lrAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.living_room, container, false);

        lrArray = new ArrayList<>();
        lrArray.add(getString(R.string.livingroom_television));
        lrArray.add(getString(R.string.livingroom_lamp1));


        lrAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lrArray);


        lrListView = (ListView) rootView.findViewById(R.id.livingroom_listview);
        lrListView.setAdapter(lrAdapter);
        lrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {

                    case 0:
                        Context context = view.getContext();
                        Intent intent = new Intent(context, TelevisionActivity.class);

                        context.startActivity(intent);
                        break;

                    case 1:
                        Toast toast = Toast.makeText(view.getContext(), "Lamp1 selected...", LENGTH_SHORT);
                        toast.show();
                        break;
                }
            }
        });

        return rootView;
    }
}
