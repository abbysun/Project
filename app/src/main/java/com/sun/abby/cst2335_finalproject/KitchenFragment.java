package com.sun.abby.cst2335_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A view class representing
 * Created by Abby on 2016-11-06.
 */

public class KitchenFragment extends Fragment implements View.OnClickListener{
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //linking layout to view
        View rootView = inflater.inflate(R.layout.kitchen, container, false);

        //find button and implement button click listener
        button = (Button) rootView.findViewById(R.id.kitchen_index_button);
        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v){
        Intent toKitchen = new Intent(this.getContext(), KitchenActivity.class);
        getActivity().startActivity(toKitchen);
    }
}
