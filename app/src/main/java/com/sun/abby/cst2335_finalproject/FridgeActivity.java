package com.sun.abby.cst2335_finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * This class contains behavioural implementations for views in activity_fridge.xml
 * It uses fragmentation to display temperature control and food items in the fridge and freezer section
 * FreezerFragment.java and FridgeFragment.java are the classes that implement behaviours of those fragment classes
 * Food items can be added or deleted using the menu icons. Customized dialogues will be displayed when those icons are clicked
 * @author Abby
 */
public class FridgeActivity extends AppCompatActivity {
    private CoordinatorLayout cl;
    private FridgeFragment fridgeFragment = new FridgeFragment();
    private FreezerFragment freezerFragment = new FreezerFragment();
    private FridgeDatabaseHelper helper;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find layout and views by id
        cl = (CoordinatorLayout) findViewById(R.id.main_content);

        // Create the adapter that will return a fragment for each of the three primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //access database
        helper = new FridgeDatabaseHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fridge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(item.getItemId()){
            case R.id.action_add_item:
                newLayoutDialog().show();
                break;
            case R.id.action_drop_item:
                confirmDelete().show();
                break;
            case R.id.action_help:
                Snackbar.make(cl, "Click on the list for item details. Click plus/minus sign to add/delete food item", Snackbar.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @return dialog for users to add items
     */
    public Dialog newLayoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.fridge_add_dialog, null);
        builder.setView(dialogView);

        final EditText foodName = (EditText) dialogView.findViewById(R.id.custom_dialog_name);
        final EditText foodDescription = (EditText) dialogView.findViewById(R.id.custom_dialog_description);
        final RadioButton fridge =  (RadioButton) dialogView.findViewById(R.id.is_fridge);

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //if user click add
                if(fridge.isChecked()){

                    if(!foodName.getText().toString().isEmpty()){
                        helper.insertFridgeData(foodName.getText().toString(), foodDescription.getText().toString()); //insert data
                        fridgeFragment.updateListAdd(foodName.getText().toString(), foodDescription.getText().toString()); //update list
                    }else{
                        Toast.makeText(getApplicationContext(), "cannot add empty item", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    if(!foodName.getText().toString().isEmpty()){
                        helper.insertFreezerData(foodName.getText().toString(), foodDescription.getText().toString()); //insert data
                        freezerFragment.updateListAdd(foodName.getText().toString(), foodDescription.getText().toString()); //update list
                    }else{
                        Toast.makeText(getApplicationContext(), "cannot add empty item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {  } //do nothing
        });
        return builder.create();
    }

    /**
     * @return dialog to confirm item deletion
     */
    public Dialog confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.fridge_delete_dialog, null);
        builder.setView(dialogView);

        final RadioButton fridge =  (RadioButton) dialogView.findViewById(R.id.is_fridge);

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(fridge.isChecked()){
                    if(fridgeFragment.getSelectedItem() != null){
                        helper.removeFridgeData(fridgeFragment.getSelectedItem().getId());
                        fridgeFragment.updateListDelete();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please select an item in the fridge to delete", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(freezerFragment.getSelectedItem() != null){
                        helper.removeFreezerData(freezerFragment.getSelectedItem().getId());
                        freezerFragment.updateListDelete();
                    }else {
                        Toast.makeText(getApplicationContext(), "Please select an item in the freezer to delete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { } //do nothing
        });
        return builder.create();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return fridgeFragment;
                case 1:
                    return freezerFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;  // Show 2 total pages.
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Fridge";
                case 1:
                    return "Freezer";
            }
            return null;
        }
    }
}
