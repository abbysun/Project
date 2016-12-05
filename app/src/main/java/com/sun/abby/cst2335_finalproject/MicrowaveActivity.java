package com.sun.abby.cst2335_finalproject;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MicrowaveActivity extends AppCompatActivity {
    private Button onOff;
    private Button stop;
    private EditText inputTime;
    private ImageView microwaveImage;
    private ProgressBar cookingProgress;
    private static boolean turnOn = true;
    private int cookTime=0;
    private final static String ACTIVITY_NAME="Microwave_Activity";
    private int progress=0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microwave);

        //link to all view items
        onOff = (Button) findViewById(R.id.onOffButton);
        stop = (Button) findViewById(R.id.microwave_stop);
        inputTime = (EditText)findViewById(R.id.inputTime);
        microwaveImage = (ImageView) findViewById(R.id.microwaveImage);
        cookingProgress = (ProgressBar) findViewById(R.id.microwave_progress);
        cookingProgress.setProgress(0);

        //button function - when clicked, it also changes the image
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turnOn){
                    try{
                        cookTime = Integer.parseInt( inputTime.getText().toString() );
                        turnOn();

                        timer = new CountDownTimer(cookTime * 1000, 1000){
                            public void onTick(long millisUntilFinished) {
                                Log.i(ACTIVITY_NAME, "timer counting");
                                int timeCooked = cookTime - (int)(millisUntilFinished / 1000);
                                Log.i(ACTIVITY_NAME, "timer cooked: " + timeCooked + "/" + cookTime);
                                progress = (timeCooked * 100) / cookTime;
                                cookingProgress.setProgress(progress);
                                Log.i(ACTIVITY_NAME, "PROGRESS: " + progress);
                            }

                            public void onFinish() {
                                cookingProgress.setProgress(100);

                                // vibrate and show text when done cooking
                                Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                mVibrator.vibrate(500);
                                Toast.makeText(getApplicationContext(), "Finished cooking", Toast.LENGTH_SHORT).show();

                                //automatically turn off the microwave
                                turnOff();
                            }
                        };
                        timer.start();

                    }catch(NumberFormatException e ){
                        Toast.makeText(getApplicationContext(), R.string.enter_time, Toast.LENGTH_SHORT).show();
                        Log.d(ACTIVITY_NAME, e.getMessage());
                    }
                    Log.i(ACTIVITY_NAME," microwave is off, turning it on");

                }
                else{
                    Log.i(ACTIVITY_NAME," microwave is on, turning it off");
                    turnOff();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                turnOff();
                Toast.makeText(getApplicationContext(), R.string.stop_microwave, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void turnOn(){
        // microwave is off
        stop.setText(R.string.stop);
        stop.setEnabled(true);
        cookingProgress.setVisibility(View.VISIBLE);

        //turn on microwave and allow microwave to be turned off
        turnOn = false;
        microwaveImage.setImageResource(R.drawable.microwaveon);
        onOff.setText(R.string.turn_off);
    }

    public void turnOff(){
        // microwave is on
        stop.setEnabled(false);
        cookTime = 0;
        progress = 0;
        cookingProgress.setVisibility(View.INVISIBLE);
        inputTime.setText("");
        //turn off microwave and allow microwave to be turned on
        turnOn = true;
        microwaveImage.setImageResource(R.drawable.microwaveoff);
        onOff.setText(R.string.turn_on);
    }


}
