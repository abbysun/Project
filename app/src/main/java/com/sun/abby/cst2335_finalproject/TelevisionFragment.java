package com.sun.abby.cst2335_finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class TelevisionFragment extends Fragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tvView = inflater.inflate(R.layout.fragment_television, container, false);

        etInput = (EditText) tvView.findViewById(R.id.television_input);
        tvChannel = (TextView) tvView.findViewById(R.id.television_channel);
        tvChannel.setText("Channel: 1");

        View.OnClickListener tvListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                switch (v.getId()) {
                    case R.id.television_up:
                        Log.d(null, "Up button pressed.");
                        toast = Toast.makeText(v.getContext(),"UP", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_left:
                        Log.d(null, "Left button pressed.");
                        toast = Toast.makeText(v.getContext(),"LEFT", LENGTH_SHORT);
                        toast.show();
                        break;
                    case R.id.television_enter:
                        Log.d(null, "Right button pressed.");
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

        ibUp= (ImageButton) tvView.findViewById(R.id.television_up);
        ibUp.setOnClickListener(tvListener);

        ibLeft= (ImageButton) tvView.findViewById(R.id.television_left);
        ibLeft.setOnClickListener(tvListener);

        ibEnter= (ImageButton) tvView.findViewById(R.id.television_enter);
        ibEnter.setOnClickListener(tvListener);

        ibRight= (ImageButton) tvView.findViewById(R.id.television_right);
        ibRight.setOnClickListener(tvListener);

        ibDown= (ImageButton) tvView.findViewById(R.id.television_down);
        ibDown.setOnClickListener(tvListener);

        ibMic = (ImageButton) tvView.findViewById(R.id.television_mic);
        ibMic.setOnClickListener(tvListener);

        ibBack = (ImageButton) tvView.findViewById(R.id.television_back);
        ibBack.setOnClickListener(tvListener);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_television, container, false);
        return tvView;
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
