package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * Class to handle the creation and events in the television fragment of the app.
 *
 * Created by Chase Thorne.
 */
public class TelevisionActivity extends AppCompatActivity {

    ImageButton ibUp;
    ImageButton ibLeft;
    ImageButton ibEnter;
    ImageButton ibRight;
    ImageButton ibDown;

    TextView tvChannel;
    EditText etInput;
    ImageButton ibMic;
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_television);

        etInput = (EditText) findViewById(R.id.television_input);
        tvChannel = (TextView) findViewById(R.id.television_channel);
        tvChannel.setText("Channel: 1");

        // Placeholder OnClickListener for the remote.
        View.OnClickListener tvListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                switch (v.getId()) {
                    case R.id.television_up:
                        toast = Toast.makeText(v.getContext(),"UP", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_left:
                        toast = Toast.makeText(v.getContext(),"LEFT", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_enter:
                        tvChannel.setText("Channel: " + etInput.getText());
                        etInput.setText("");
                        break;
                    case R.id.television_right:
                        toast = Toast.makeText(v.getContext(),"RIGHT", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_down:
                        toast = Toast.makeText(v.getContext(),"DOWN", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_back:
                        toast = Toast.makeText(v.getContext(),"BACK", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_mic:
                        toast = Toast.makeText(v.getContext(),"RIGHT", LENGTH_SHORT);
                        toast.show();
                        break;
                }
            }
        };

        ibUp= (ImageButton) findViewById(R.id.television_up);
        ibUp.setOnClickListener(tvListener);

        ibLeft= (ImageButton) findViewById(R.id.television_left);
        ibLeft.setOnClickListener(tvListener);

        ibEnter= (ImageButton) findViewById(R.id.television_enter);
        ibEnter.setOnClickListener(tvListener);

        ibRight= (ImageButton) findViewById(R.id.television_right);
        ibRight.setOnClickListener(tvListener);

        ibDown= (ImageButton) findViewById(R.id.television_down);
        ibDown.setOnClickListener(tvListener);

        ibMic = (ImageButton) findViewById(R.id.television_mic);
        ibMic.setOnClickListener(tvListener);

        ibBack = (ImageButton) findViewById(R.id.television_back);
        ibBack.setOnClickListener(tvListener);
    }
}
