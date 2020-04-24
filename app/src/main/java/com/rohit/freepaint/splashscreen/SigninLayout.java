package com.rohit.freepaint.splashscreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;

public class SigninLayout extends AppCompatDialogFragment {

    AppCompatButton signin;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.login_layout, null);

        final MediaPlayer snareRoll = MediaPlayer.create(view.getContext(), R.raw.snareroll);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                snareRoll.start();
             } }, 1500);

        builder.setView(view)
                .setTitle("Sign In")
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

        signin = (AppCompatButton) view.findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Feature under development", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        System.exit(0);
        super.onCancel(dialog);
    }
}
