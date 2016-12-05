package com.sun.abby.cst2335_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class KitchenLightActivity extends AppCompatActivity {
    private SeekBar dim ;
    private WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_light);

        Button on = (Button) findViewById(R.id.kitchen_light_on);
        Button off = (Button) findViewById(R.id.kitchen_light_off);
        dim = (SeekBar) findViewById(R.id.kitchen_light_seekbar);
        dim.setProgress(50);

        //set dimming control
        lp = getWindow().getAttributes();
        lp.screenBrightness = 50 / 100.0f;
        getWindow().setAttributes(lp);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dim.setProgress(100);
                lp.screenBrightness = 100 / 100.0f;
                getWindow().setAttributes(lp);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dim.setProgress(0);
                lp.screenBrightness = 0 / 100.0f;
                getWindow().setAttributes(lp);
            }
        });

        dim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lp.screenBrightness =  i / 100.0f;
                getWindow().setAttributes(lp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {} //DO NOTHING

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}  //DO NOTHING
        });
    }
}
