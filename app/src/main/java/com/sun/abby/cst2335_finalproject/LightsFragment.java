package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by null on 2016-12-05.
 */

public class LightsFragment extends Fragment {

    Button colorSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View blindsView = inflater.inflate(R.layout.fragment_lights, container, false);

        colorSelect = (Button) blindsView.findViewById(R.id.light_3_color);
        colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new ColorDialogFragment();
                newFragment.show(getFragmentManager(), "colors");
            }
        });

        return blindsView;
    }

}