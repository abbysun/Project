package com.sun.abby.cst2335_finalproject;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static com.sun.abby.cst2335_finalproject.LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_ACCESSCOUNT;
import static com.sun.abby.cst2335_finalproject.LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_STRINGID;
import static com.sun.abby.cst2335_finalproject.LivingRoomDbHelper.LivingRoomEntry.COLUMN_NAME_TITLE;
import static com.sun.abby.cst2335_finalproject.LivingRoomDbHelper.LivingRoomEntry.TABLE_NAME;

public class LivingRoomFragment extends Fragment {

    View rootView;

    ListView lrListView;
    ArrayList<String> lrArray;
    ArrayAdapter<String> lrAdapter;
    boolean mTwoPane;
    ImageButton about;
    ImageButton add;
    ImageButton del;
    EditText addInput;
    LinearLayout inputBar;

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

        inputBar = (LinearLayout) rootView.findViewById(R.id.livingroom_input_bar);
        inputBar.setVisibility(View.INVISIBLE);

        about = (ImageButton) rootView.findViewById(R.id.livingroom_help);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AboutDialogFragment();
                newFragment.show(getFragmentManager(), getString(R.string.dialog_color_title));
            }
        });

        progBar = (ProgressBar) rootView.findViewById(R.id.livingroom_progressbar);

        // invoking the inner class to populate the listview
        CreateListView clv = new CreateListView();
        clv.execute();

        // remove item from the listview
        del = (ImageButton) rootView.findViewById(R.id.livingroom_del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = addInput.getText().toString();
                boolean del = delItem(item);
                if (del) {
                    Toast.makeText(view.getContext(),"Removed " + item + ".", LENGTH_SHORT).show();
                    lrArray.remove(item);
                    lrAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(view.getContext(), item + " isn't in the list. Try another name.", LENGTH_LONG).show();
                }
            }
        });

        // instantiating the input box for dialog_livingroom_add
        addInput = (EditText) rootView.findViewById(R.id.livingroom_input_box);

        // add item to the listview
        add = (ImageButton) rootView.findViewById(R.id.livingroom_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = addInput.getText().toString();
                boolean add = addItem(addInput.getText().toString());
                if (add) {
                    Toast.makeText(view.getContext(),"Added " + item + ".", LENGTH_SHORT).show();
                    lrArray.add(item);
                    lrAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(view.getContext(), item + " already exists. Try another name.", LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    /**
     * Adds an item to the database.
     * @param item String representing the name of the item.
     * @return True if added successfully, false if not.
     */
    public boolean addItem(String item) {
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_NAME_ACCESSCOUNT, 0);
        insertValues.put(COLUMN_NAME_STRINGID, 0);
        insertValues.put(COLUMN_NAME_TITLE, item);
        try {
            return db.insert(TABLE_NAME, null, insertValues) > 0;
        } catch (SQLiteConstraintException sqlce) {
            return false;
        }
    }

    /**
     * Deletes an item from the database.
     * @param item String representing the name of the item.
     * @return True if added successfully, false if not.
     */
    public boolean delItem(String item) {
        return db.delete(TABLE_NAME, COLUMN_NAME_TITLE + "=" + "'" + item + "'", null) > 0;
    }

    /**
     * Inner class that inflates a DialogFragment containing the "About" information.
     */
    public static class AboutDialogFragment extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_livingroom_about, null))
                    // Add action buttons
                    .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // close dialog
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }

    }

    /**
     * Private inner class that creates an asynchronous task to query the database
     * and populate a ListView for navigating the livingroom portion of the app.
     */
    private class CreateListView extends AsyncTask<Object, Object, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Object... strings) {

            mDbHelper = new LivingRoomDbHelper(getContext());

            // Gets the data repository in write mode
            db = mDbHelper.getWritableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    COLUMN_NAME_ACCESSCOUNT,
                    COLUMN_NAME_STRINGID,
                    COLUMN_NAME_TITLE
            };

            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    COLUMN_NAME_ACCESSCOUNT + " DESC";

            Cursor c = db.query(
                    TABLE_NAME,      // The table to query
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

            int stringId;

            // Adding results from the database to the ListView
            while (!c.isAfterLast()) {

                // For the purposes of demoing the progressbar
                try { Thread.sleep(500); }
                catch (InterruptedException e) { e.printStackTrace(); }

                publishProgress("Working...");

                // Retrieve R.string.* value
                stringId = c.getInt(c.getColumnIndexOrThrow(COLUMN_NAME_STRINGID));

                // if column has no stringid, use title
                if (stringId == 0) {
                    lrArray.add(c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
                } else {
                    lrArray.add(getString(stringId));
                }
                c.moveToNext();
            }

            return lrArray;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);

            lrAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lrArray);

            lrListView = (ListView) rootView.findViewById(R.id.livingroom_listview);

            lrListView.setAdapter(lrAdapter);
            lrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String item = (lrListView.getItemAtPosition(position)).toString();

                    switch (item) {

                        // Navigate to the television fragment/activity
                        case "Television":
                        case "Télévision":

                            updateAccessCount(String.valueOf(R.string.livingroom_television));

                            if (mTwoPane) {
                                loadFragment(new TelevisionFragment());
                            } else {
                                loadActivity(TelevisionActivity.class);
                            }
                            break;

                        // Navigate to the light fragment/activity
                        case "Lights":
                        case "Lumières":

                            updateAccessCount(String.valueOf(R.string.livingroom_lights));

                            if (mTwoPane) {
                                loadFragment(new LightsFragment());
                            } else {
                                loadActivity(LightsActivity.class);
                            }
                            break;

                        // Navigate to the blinds fragment/activity
                        case "Blinds":
                        case "Stores":

                            updateAccessCount(String.valueOf(R.string.livingroom_blinds));

                            if (mTwoPane) {
                                loadFragment(new BlindsFragment());
                            } else {
                                loadActivity(BlindsActivity.class);
                            }
                            break;

                    }
                }
            });

            inputBar.setVisibility(View.VISIBLE);

            progBar.setVisibility(progBar.INVISIBLE);
        }

        /**
         * Updates the navigation database column storing the number of times an item
         * has been accessed. Used for ordering the navigation menu at build time.
         * @param id The resource id of the item to be updated.
         */
        public void updateAccessCount(String id) {
            db.execSQL("UPDATE " + TABLE_NAME
                    + " SET " + COLUMN_NAME_ACCESSCOUNT + " = " + COLUMN_NAME_ACCESSCOUNT + " +1" +
                    " WHERE " + COLUMN_NAME_STRINGID + " = " + id);
        }

        /**
         * Method to load a fragment into the livingroom app's container.
         * @param fragment The fragment to be loaded.
         */
        public void loadFragment(Fragment fragment) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.living_room_container, fragment)
                    .commit();
        }

        /**
         * Method to inflate an activity in the app.
         * @param myClass The activity to be inflated.
         */
        public void loadActivity(Class myClass) {
            Context context = rootView.getContext();
            Intent intent = new Intent(context, myClass);

            context.startActivity(intent);
        }
    }
}