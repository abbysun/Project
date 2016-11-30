package com.sun.abby.cst2335_finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class Garage extends AppCompatActivity {
    ToggleButton t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        t1 = (ToggleButton)findViewById(R.id.toggleButton_light);
        t2 =(ToggleButton)findViewById(R.id.toggleButton_door);
    }// end of onCreate

   /*     ToggleButton light = (ToggleButton)findViewById(R.id.toggleButton_light);
        ToggleButton door =(ToggleButton)findViewById(R.id.toggleButton_door);

       // light.toggle();
       // door.toggle();
       // set Light ToggleButton text
        light.setTextOn("Light Off");
        light.setTextOn("Light On");
        light.setChecked(false);

        //set Door ToggleButton text
        door.setTextOn("Door Opened");
        door.setTextOn("Door Closed");
        door.setChecked(false);

*/
 public void lightClick(View view) {
     boolean on = ((ToggleButton) view).isChecked();
     ImageView iView = (ImageView) findViewById(R.id.lightOnOff);
     if (on) {
         on = false;
         iView.setImageResource(R.drawable.lighton);
     } else {
         on = true;
         iView.setImageResource(R.drawable.lightoff);
     }

 }// end of lightClick

    public void doorClick (View v){
        boolean on = ((ToggleButton) v).isChecked();
        ImageView iView = (ImageView) findViewById(R.id.doorOpenClosed);
        if (on) {
            on = false;
            iView.setImageResource(R.drawable.dooropen);
        } else {
            on = true;
            iView.setImageResource(R.drawable.doorclosed);
        }

    }// end of doorClick


}// end of Garage class
