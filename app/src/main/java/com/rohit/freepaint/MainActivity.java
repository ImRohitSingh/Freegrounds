package com.rohit.freepaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rohit.freepaint.anagram.FindAnagram;
import com.rohit.freepaint.dodger.Dodger;
import com.rohit.freepaint.killthemall.KillThemAllActivity;
import com.rohit.freepaint.paint.FreePaint;
import com.rohit.freepaint.tictactoe.TicTacToe;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    private long backPressed = 0;

    private Toast back;

    MediaPlayer tictactoe;
    MediaPlayer paint;
    MediaPlayer killthemall;
    MediaPlayer dodger;
    MediaPlayer have_a_great_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        tictactoe = MediaPlayer.create(getApplicationContext(), R.raw.tictactoe);
        paint = MediaPlayer.create(getApplicationContext(), R.raw.paint);
        killthemall = MediaPlayer.create(getApplicationContext(), R.raw.killthemall);
        dodger = MediaPlayer.create(getApplicationContext(), R.raw.dodger);
        have_a_great_time = MediaPlayer.create(getApplicationContext(), R.raw.have_a_great_time);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);
    }

    public void onChoosingPaint(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                paint.start();
                Intent intent = new Intent(getApplicationContext(), FreePaint.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    public void onChoosingAnagram(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), FindAnagram.class);
                startActivity(intent);
            }}, 1500);
    }

    public void onChoosingTicTacToe(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                tictactoe.start();
                Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    public void onChoosingKillThemAll(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                killthemall.start();
                Intent intent = new Intent(getApplicationContext(), KillThemAllActivity.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    public void onChoosingDodger(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                dodger.start();
                Intent intent = new Intent(getApplicationContext(), Dodger.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            have_a_great_time.start();
            back.cancel();
            super.onBackPressed();
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }
}
