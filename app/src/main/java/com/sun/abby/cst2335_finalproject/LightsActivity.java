package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Class to handle the creation and events in the lights activity of the app.
 *
 * Created by Chase Thorne on 2016-12-05.
 */

public class LightsActivity extends AppCompatActivity {

    Button colorSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lights);

        // loads the color selection dialog
        colorSelect = (Button) findViewById(R.id.light_3_color);
        colorSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new ColorDialogFragment();
                newFragment.show(getSupportFragmentManager(), "colors");
            }
        });

    }

}
