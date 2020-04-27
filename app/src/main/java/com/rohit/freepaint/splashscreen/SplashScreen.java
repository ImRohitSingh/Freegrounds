package com.rohit.freepaint.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final MediaPlayer snareRoll = MediaPlayer.create(getApplicationContext(), R.raw.snareroll);

        if(getSigninStatus() == 0) {
            openDialog();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    snareRoll.start();
                } }, 1500);

            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } }, 1500);

        }


    }

    private void openDialog() {
        SigninLayout modalLayout = new SigninLayout();
        modalLayout.show(getSupportFragmentManager(), "Sign In");
    }

    private int getSigninStatus() {
        SharedPreferences prefs = getSharedPreferences("freegrounds", 0);
        return prefs.getInt("signin", 0);
    }
}
