package com.rohit.freepaint.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rohit.freepaint.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        openDialog();

    }

    private void openDialog() {
        SigninLayout modalLayout = new SigninLayout();
        modalLayout.show(getSupportFragmentManager(), "Sign In");
    }
}
