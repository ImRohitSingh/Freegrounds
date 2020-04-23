package com.rohit.freepaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rohit.freepaint.dodger.DodgerMainActivity;

public class Dodger extends AppCompatActivity {

    private long backPressed = 0;

    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodger);

        back = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
    }

    public void start(View view) {
        Intent intent = new Intent(getApplicationContext(), DodgerMainActivity.class);
        startActivity(intent);
        //finish();
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
