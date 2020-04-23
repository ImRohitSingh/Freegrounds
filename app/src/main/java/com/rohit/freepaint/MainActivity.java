package com.rohit.freepaint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rohit.freepaint.killthemall.KillThemAllActivity;
import com.rohit.freepaint.paint.FreePaint;
import com.rohit.freepaint.tictactoe.TicTacToe;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    private long backPressed = 0;

    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        back = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
    }

    public void onChoosingPaint(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), FreePaint.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    public void onChoosingAnagram(View view) {
        //progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Feature under development", Toast.LENGTH_SHORT).show();
        /*new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), FindAnagram.class);
                startActivity(intent);
            }}, 2500);*/
    }

    public void onChoosingTicTacToe(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
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
                Intent intent = new Intent(getApplicationContext(), Dodger.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            back.cancel();
            super.onBackPressed();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }
}
