package com.sun.abby.cst2335_finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class LivingRoomFragment extends Fragment {

    ListView lrListView;
    ArrayList<String> lrArray;
    ArrayAdapter<String> lrAdapter;
    boolean mTwoPane;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.living_room, container, false);


        // check if screen is wide enough for fragment use
        if (rootView.findViewById(R.id.living_room_container) != null) {
            mTwoPane = true;
        }


//        // setting up toolbar
//        toolbar = (Toolbar) rootView.findViewById(R.id.living_room_toolbar);
//        getActivity().setActionBar(toolbar);




        // Listing of objects to control
        lrArray = new ArrayList<>();
        lrArray.add(getString(R.string.livingroom_television));
        lrArray.add(getString(R.string.livingroom_lights));
        lrArray.add("Blinds");

        lrAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lrArray);

        lrListView = (ListView) rootView.findViewById(R.id.livingroom_listview);
        lrListView.setAdapter(lrAdapter);
        lrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {

                    case 0:
                        if (mTwoPane) {
                            TelevisionFragment fragment = new TelevisionFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.living_room_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, TelevisionActivity.class);

                            context.startActivity(intent);
                        }
                        break;

                    case 1:
                        if (mTwoPane) {
                            LightsFragment fragment = new LightsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.living_room_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, LightsActivity.class);

                            context.startActivity(intent);
                        }
                        break;

                    case 2:
                        if (mTwoPane) {
                            BlindsFragment fragment = new BlindsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.living_room_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, BlindsActivity.class);

                            context.startActivity(intent);
                        }
                        break;

                    case 3:
                        if (mTwoPane) {
                            LightsFragment fragment = new LightsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.living_room_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, LightsActivity.class);

                            context.startActivity(intent);
                        }
                        break;
                }
            }
        });

        return rootView;
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getActivity().getMenuInflater();
////        inflater.inflate(R.menu.menu_living_room, menu);
////        return true;
//
//        toolbar.inflateMenu(R.menu.menu_living_room);
//        toolbar.setOnMenuItemClickListener(
//                new Toolbar.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        return onOptionsItemSelected(item);
//                    }
//                });
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // action with ID action_refresh was selected
//            case R.id.menu_living_room_about:
//                // open dialog w/ author, version, instructions
//
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }
}