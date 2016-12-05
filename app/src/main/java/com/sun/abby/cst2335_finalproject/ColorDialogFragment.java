package com.sun.abby.cst2335_finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.SeekBar;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by null on 2016-12-05.
 */

public class ColorDialogFragment extends DialogFragment {

    SeekBar alpha;
    SeekBar red;
    SeekBar green;
    SeekBar blue;
    String colorText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        alpha = (SeekBar) getActivity().findViewById(R.id.color_seekbar_alpha);
        red = (SeekBar) getActivity().findViewById(R.id.color_seekbar_red);
        green = (SeekBar) getActivity().findViewById(R.id.color_seekbar_green);
        blue = (SeekBar) getActivity().findViewById(R.id.color_seekbar_blue);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.light_color_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.color_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        colorText = "You've changed the color of the light!";
//                        colorText = "Alpha: " + alpha.getProgress()
//                                + ", Red: "+ red.getProgress()
//                                + ", Green: "+ green.getProgress()
//                                + ", Blue: "+ blue.getProgress();
                        Snackbar.make(getActivity().findViewById(R.id.light_3_seekbar), colorText, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                })
                .setNegativeButton(R.string.color_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ColorDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}