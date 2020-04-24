package com.rohit.freepaint.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;
import com.rohit.freepaint.tictactoe.AboutActivity;
import com.rohit.freepaint.tictactoe.SinglePlayerActivity;

public class TicTacToe extends AppCompatActivity {

    private long backPressed = 0;

    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        back = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
    }

    public void startGame_singlePlayer(View view) {
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
    }

    public void startGameOnline(View view) {
        Toast.makeText(getApplicationContext(), "Feature under development", Toast.LENGTH_SHORT).show();
    }

    public void ShowAboutNote(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    public void EndGame(View view)  {
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
