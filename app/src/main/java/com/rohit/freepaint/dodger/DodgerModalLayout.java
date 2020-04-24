package com.rohit.freepaint.dodger;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.rohit.freepaint.R;
import com.rohit.freepaint.dodger.Constants;
import com.rohit.freepaint.dodger.DodgerMainActivity;

public class DodgerModalLayout extends AppCompatDialogFragment {

    private ImageView animation;
    private ImageView rectangle;
    private int chosenAvatar = -1;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dodger_modal_layout, null);

        builder.setView(view)
                .setTitle("Choose Avatar")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Begin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(chosenAvatar == -1) {
                            chosenAvatar = 1;
                            Toast.makeText(getActivity(), "Default Avatar Chosen", Toast.LENGTH_SHORT).show();
                        }
                        Constants.AVATAR = chosenAvatar;
                        Intent intent = new Intent(getActivity(), DodgerMainActivity.class);
                        startActivity(intent);
                    }
                });

        animation = view.findViewById(R.id.animation);
        rectangle = view.findViewById(R.id.rectangle);

        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rectangle.setAlpha(0.1f);
                animation.setAlpha(1.0f);
                chosenAvatar = 1;
            }
        });

        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.setAlpha(0.1f);
                rectangle.setAlpha(1.0f);
                chosenAvatar = 2;
            }
        });

        return builder.create();
    }
}
