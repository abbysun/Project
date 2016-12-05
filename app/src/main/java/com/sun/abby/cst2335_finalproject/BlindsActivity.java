package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by Chase Thorne on 2016-12-05.
 */

public class BlindsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blinds);


        final LinearLayout mLayout = (LinearLayout) findViewById(R.id.blinds_layout);

        mLayout.post(new Runnable() {

            @Override
            public void run() {
                LinearLayout.LayoutParams mParams;
                mParams = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
                double width = mLayout.getHeight() * 0.8;
                mParams.width = (int)width;
                mLayout.setLayoutParams(mParams);
                mLayout.postInvalidate();
            }
        });

    }

}
