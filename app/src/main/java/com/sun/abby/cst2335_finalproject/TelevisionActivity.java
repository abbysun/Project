package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class TelevisionActivity extends AppCompatActivity {

    ImageButton ibUp;
    ImageButton ibLeft;
    ImageButton ibEnter;
    ImageButton ibRight;
    ImageButton ibDown;

    EditText etInput;
    ImageButton ibMic;
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_television);

        ibUp= (ImageButton) findViewById(R.id.television_up);
        ibUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "UP", LENGTH_SHORT);
                toast.show();
            }
        });

        ibLeft= (ImageButton) findViewById(R.id.television_left);
        ibLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "LEFT", LENGTH_SHORT);
                toast.show();
            }
        });

        ibEnter= (ImageButton) findViewById(R.id.television_enter);
        ibEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Changing to " + etInput.getText(), LENGTH_SHORT);
                etInput.setText("");
                toast.show();
            }
        });

        ibRight= (ImageButton) findViewById(R.id.television_right);
        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "RIGHT", LENGTH_SHORT);
                toast.show();
            }
        });

        ibDown= (ImageButton) findViewById(R.id.television_down);
        ibDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "DOWN", LENGTH_SHORT);
                toast.show();
            }
        });

        etInput = (EditText) findViewById(R.id.television_input);

        ibMic = (ImageButton) findViewById(R.id.television_input_mic);
        ibMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Voice commannd...", LENGTH_SHORT);
                toast.show();
            }
        });

        ibBack = (ImageButton) findViewById(R.id.television_input_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Previous channel...", LENGTH_SHORT);
                toast.show();
            }
        });

    }
}
