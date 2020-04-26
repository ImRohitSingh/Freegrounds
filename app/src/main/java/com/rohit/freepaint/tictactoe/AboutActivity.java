package com.rohit.freepaint.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rohit.freepaint.R;

public class AboutActivity extends AppCompatActivity {

    private long backPressed = 0;

    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);
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
