package com.rohit.freepaint.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                snareRoll.start();
                finish(); } }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); } }, 2000);
    }
}
