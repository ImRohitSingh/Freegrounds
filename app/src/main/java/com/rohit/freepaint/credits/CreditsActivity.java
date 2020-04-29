package com.rohit.freepaint.credits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.rohit.freepaint.R;

public class CreditsActivity extends AppCompatActivity {

    private long backPressed = 0;
    private Toast back;

    private TextView creditsTextView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        String creditText = "This app was developed for learning purposes.\n" +
                "It contains three 2-D games - TicTacToe, KillThemAll! and Dodger. They " +
                "can be perceived easily while playing.\n" +
                "It also contains a Paint application.\n" +
                "And also has an Anagram game to guess a word within a given time.\n" +
                "Authored By: Rohit Singh\n" +
                "The following mentions helped me:\n" +
                "Android's official documentation\n" +
                "Google's official documentation\n" +
                "Call particular method at regular intervals - Stackoverflow\n" +
                "Drawing mirrored bitmaps - Stackoverflow\n" +
                "hdodenhof/CircleImageView - Github\n" +
                "medyo/FancyButtons- Github\n" +
                "OpenGameArt.org\n" +
                "Youtube channels of - Philip Vujovic, Alro Developers, Stevdza San, Coding in Flow, " +
                "AndroidMinutes, Tihomir RAdeff, Retro Chicken and Hey! Let's Code";

        creditsTextView = (TextView) findViewById(R.id.creditTexts);
        creditsTextView.setText(creditText);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.credits);

        creditsTextView.startAnimation(animation);

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
