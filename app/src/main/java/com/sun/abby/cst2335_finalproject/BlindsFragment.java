package com.sun.abby.cst2335_finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by null on 2016-12-05.
 */

public class BlindsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View blindsView = inflater.inflate(R.layout.fragment_blinds, container, false);

        final LinearLayout mLayout = (LinearLayout) blindsView.findViewById(R.id.blinds_layout);

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

        return blindsView;
    }
}
