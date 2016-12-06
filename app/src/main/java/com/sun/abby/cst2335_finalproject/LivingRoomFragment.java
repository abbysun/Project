package com.sun.abby.cst2335_finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class LivingRoomFragment extends Fragment {

    View rootView;

    ListView lrListView;
    ArrayList<String> lrArray;
    ArrayAdapter<String> lrAdapter;
    boolean mTwoPane;
    ImageButton about;

    LivingRoomDbHelper mDbHelper;
    SQLiteDatabase db;

    ProgressBar progBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.living_room, container, false);

        // check if screen is wide enough for fragment use
        if (rootView.findViewById(R.id.living_room_container) != null) {
            mTwoPane = true;
        }

        about = (ImageButton) rootView.findViewById(R.id.livingroom_help);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AboutDialogFragment();
                newFragment.show(getFragmentManager(), "Colors");
            }
        });

        progBar = (ProgressBar) rootView.findViewById(R.id.livingroom_progressbar);

        CreateListView clv = new CreateListView();
        clv.doInBackground();

//        mDbHelper = new LivingRoomDbHelper(getContext());
//
//        // Gets the data repository in write mode
//        db = mDbHelper.getWritableDatabase();
//
//        // Define a projection that specifies which columns from the database
//        // you will actually use after this query.
//        String[] projection = {
//                LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT,
//                LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID
//        };
//
//        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " DESC";
//
//        Cursor c = db.query(
//                LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME,      // The table to query
//                projection,                                         // The columns to return
//                null,                                               // The columns for the WHERE clause
//                null,                                               // The values for the WHERE clause
//                null,                                               // don't group the rows
//                null,                                               // don't filter by row groups
//                sortOrder                                           // The sort order
//        );
//
//        // Moving to first result
//        c.moveToFirst();
//
//        // Listing of objects to control
//        lrArray = new ArrayList<>();
//
//        // Adding results from the database to the ListView
//        while (!c.isAfterLast()) {
//            // Retrieve R.string.* value
//            int itemId = c.getInt(c.getColumnIndexOrThrow(LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID));
//            lrArray.add(getString(itemId));
//            c.moveToNext();
//        }
//
//        lrAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lrArray);
//
//        lrListView = (ListView) rootView.findViewById(R.id.livingroom_listview);
//        lrListView.setAdapter(lrAdapter);
//        lrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                String item = (lrListView.getItemAtPosition(position)).toString();
//
//                switch (item) {
//
//                    case "Television":
//                        db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
//                                + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
//                                " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_television));
//
//                        if (mTwoPane) {
//                            TelevisionFragment fragment = new TelevisionFragment();
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.living_room_container, fragment)
//                                    .commit();
//                        } else {
//                            Context context = view.getContext();
//                            Intent intent = new Intent(context, TelevisionActivity.class);
//
//                            context.startActivity(intent);
//                        }
//                        break;
//
//                    case "Lights":
//                        db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
//                                + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
//                                " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_lights));
//
//                        if (mTwoPane) {
//                            LightsFragment fragment = new LightsFragment();
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.living_room_container, fragment)
//                                    .commit();
//                        } else {
//                            Context context = view.getContext();
//                            Intent intent = new Intent(context, LightsActivity.class);
//
//                            context.startActivity(intent);
//                        }
//                        break;
//
//                    case "Blinds":
//                        db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
//                                + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
//                                " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_blinds));
//
//                        if (mTwoPane) {
//                            BlindsFragment fragment = new BlindsFragment();
//                            getActivity().getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.living_room_container, fragment)
//                                    .commit();
//                        } else {
//                            Context context = view.getContext();
//                            Intent intent = new Intent(context, BlindsActivity.class);
//
//                            context.startActivity(intent);
//                        }
//                        break;
//
//                }
//            }
//        });


        return rootView;
    }

    public static class AboutDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.livingroom_about_dialog, null))
                    // Add action buttons
                    .setNeutralButton(R.string.color_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // close dialog
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }

    }

    private class CreateListView extends AsyncTask<Object, Object, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Object... strings) {

            mDbHelper = new LivingRoomDbHelper(getContext());

            // Gets the data repository in write mode
            db = mDbHelper.getWritableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT,
                    LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID
            };

            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " DESC";

            Cursor c = db.query(
                    LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME,      // The table to query
                    projection,                                         // The columns to return
                    null,                                               // The columns for the WHERE clause
                    null,                                               // The values for the WHERE clause
                    null,                                               // don't group the rows
                    null,                                               // don't filter by row groups
                    sortOrder                                           // The sort order
            );

            // Moving to first result
            c.moveToFirst();

            // Listing of objects to control
            lrArray = new ArrayList<>();

            // Adding results from the database to the ListView
            while (!c.isAfterLast()) {
                publishProgress("Working...");
                // Retrieve R.string.* value
                int itemId = c.getInt(c.getColumnIndexOrThrow(LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID));
                lrArray.add(getString(itemId));
                c.moveToNext();
            }

            lrAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lrArray);

            return lrArray;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);

            lrListView = (ListView) rootView.findViewById(R.id.livingroom_listview);

            lrListView.setAdapter(lrAdapter);
            lrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String item = (lrListView.getItemAtPosition(position)).toString();

                    switch (item) {

                        case "Television":
                            db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
                                    + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
                                    " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_television));

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

                        case "Lights":
                            db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
                                    + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
                                    " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_lights));

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

                        case "Blinds":
                            db.execSQL("UPDATE " + LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME
                                    + " SET " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " = " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT + " +1" +
                                    " WHERE " + LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID + " = " + String.valueOf(R.string.livingroom_blinds));

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

                    }
                }
            });

            progBar.setVisibility(progBar.INVISIBLE);
        }
    }
}