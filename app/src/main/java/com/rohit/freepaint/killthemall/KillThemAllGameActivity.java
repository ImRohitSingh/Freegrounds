package com.rohit.freepaint.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class KillThemAllGameActivity extends AppCompatActivity {

    private KillThemAllView gameView;

    private long backPressed = 0;
    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new KillThemAllView(this, point.x, point.y);

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            back.cancel();
            super.onBackPressed();
            startActivity(new Intent(getApplicationContext(), KillThemAllActivity.class));
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }
}
