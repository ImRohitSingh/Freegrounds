package com.rohit.freepaint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rohit.freepaint.dodger.DodgerMainActivity;

public class Dodger extends AppCompatActivity {

    private long backPressed = 0;
    private Toast back;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodger);

        TextView highScoreTxt = findViewById(R.id.highscoretext);

        SharedPreferences prefs = getSharedPreferences("dodgergame", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("dodgerhighscore", 0));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TextView highScoreTxt = findViewById(R.id.highscoretext);

                SharedPreferences prefs = getSharedPreferences("dodgergame", MODE_PRIVATE);
                highScoreTxt.setText("HighScore: " + prefs.getInt("dodgerhighscore", 0));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        back = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
    }

    public void start(View view) {
        Intent intent = new Intent(getApplicationContext(), DodgerMainActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            back.cancel();
            super.onBackPressed();
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }
}
