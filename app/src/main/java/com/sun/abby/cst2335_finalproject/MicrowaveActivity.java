package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MicrowaveActivity extends AppCompatActivity {
    private Button onOff;
    private EditText inputTime;
    private ImageView microwaveImage;
    private static boolean on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microwave);

        //link to all view items
        onOff = (Button) findViewById(R.id.onOffButton);
        inputTime = (EditText)findViewById(R.id.inputTime);
        microwaveImage = (ImageView) findViewById(R.id.microwaveImage);

        //button function - when clicked, it also changes the image
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(on){
                    // turning off microwave
                    on = false;
                    microwaveImage.setImageResource(R.drawable.microwaveoff);
                    onOff.setText(R.string.turn_on);
                }
                else{
                    // turning on microwave
                    on = true;
                    microwaveImage.setImageResource(R.drawable.microwaveon);
                    onOff.setText(R.string.turn_off);
                }
            }
        });
    }
}
