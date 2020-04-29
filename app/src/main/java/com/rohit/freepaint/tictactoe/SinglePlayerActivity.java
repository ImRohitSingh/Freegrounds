package com.rohit.freepaint.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.rohit.freepaint.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePlayerActivity extends AppCompatActivity {

    int gameState;
    boolean playWithApp;
    String winningMessage;
    String losingMessage;
    String startMessage;

    int activePlayer;
    List<Integer> Player1; //= new ArrayList<Integer>();
    List<Integer> Player2; //= new ArrayList<Integer>();

    private long backPressed = 0;
    private Toast back;

    private MediaPlayer cheer;
    private MediaPlayer defeat;
    private MediaPlayer draw;

    private int matchStatus;
    private int difficultyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        gameState = 1; // 1-can Play  2-GameOver  3-Draw
        activePlayer = 1; // user is active player
        Player1 = new ArrayList<>();
        Player2 = new ArrayList<>();

        cheer = MediaPlayer.create(getApplicationContext(), R.raw.cheer);
        defeat = MediaPlayer.create(getApplicationContext(), R.raw.tumble3);
        draw = MediaPlayer.create(getApplicationContext(), R.raw.its_a_draw);

        difficultyLevel = getDifficultyLevel();

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
        b.setTitle("Please Confirm?")
                .setMessage("Play with App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playWithApp = true;
                        winningMessage = "Congratulations! You won.";
                        losingMessage = "Oops! You lost.";
                        startMessage = "You are starting";
                        Toast.makeText(getApplicationContext(), startMessage, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Free Hands", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playWithApp = false;
                        winningMessage = "Congratulations! Player 1 won.";
                        losingMessage = "Oops! Player 1 lost.";
                        startMessage = "Player 1 is starting";
                        Toast.makeText(getApplicationContext(), startMessage, Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    public void GameBoardClick(View view){
        ImageView selectedImage = (ImageView) view;

        int selectedBlock = 0;
        switch ((selectedImage.getId())) {
            case R.id.iv_11: selectedBlock = 1; break;
            case R.id.iv_12: selectedBlock = 2; break;
            case R.id.iv_13: selectedBlock = 3; break;

            case R.id.iv_21: selectedBlock = 4; break;
            case R.id.iv_22: selectedBlock = 5; break;
            case R.id.iv_23: selectedBlock = 6; break;

            case R.id.iv_31: selectedBlock = 7; break;
            case R.id.iv_32: selectedBlock = 8; break;
            case R.id.iv_33: selectedBlock = 9; break;
        }

        PlayGame(selectedBlock, selectedImage);
    }

    void PlayGame(int selectedBlock, ImageView selectedImage){
        if(gameState == 1) {
            if (activePlayer == 1) {
                selectedImage.setImageResource(R.drawable.ttt_x);
                Player1.add(selectedBlock);
                activePlayer = 2;
                CheckWinner();
                if(playWithApp) {
                    AutoPlay();
                }
            }else if (activePlayer == 2) {
                selectedImage.setImageResource(R.drawable.ttt_o);
                Player2.add(selectedBlock);
                activePlayer = 1;
                CheckWinner();
            }

            selectedImage.setEnabled(false);
        }
    }

    void CheckWinner(){
        int winner = 0;

        /********* for Player 1 *********/
        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)){ winner = 1; }
        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)){ winner = 1; }
        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9)){ winner = 1; }

        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7)){ winner = 1; }
        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9)){ winner = 1; }

        if(Player1.contains(1) && Player1.contains(5) && Player1.contains(9)){ winner = 1; }
        if(Player1.contains(3) && Player1.contains(5) && Player1.contains(7)){ winner = 1; }

        /********* for Player 2 *********/
        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3)){ winner = 2; }
        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6)){ winner = 2; }
        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9)){ winner = 2; }

        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7)){ winner = 2; }
        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8)){ winner = 2; }
        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9)){ winner = 2; }

        if(Player2.contains(1) && Player2.contains(5) && Player2.contains(9)){ winner = 2; }
        if(Player2.contains(3) && Player2.contains(5) && Player2.contains(7)){ winner = 2; }

        if(winner != 0 && gameState == 1){
            if(winner == 1){
                matchStatus = 1;
                ShowAlert(winningMessage);
            }else if(winner == 2){
                matchStatus = 2;
                ShowAlert(losingMessage);
            }
            gameState = 2;
        }

        if(!playWithApp) {
            List<Integer> emptyBlocks = new ArrayList<>();

            for(int i=1; i<=9; i++){
                if(!(Player1.contains(i) || Player2.contains(i))){
                    emptyBlocks.add(i);
                }
            }

            if(emptyBlocks.size() == 0) {
                //CheckWinner();
                if(gameState == 1) {
                    matchStatus = 3;
                    AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                    ShowAlert(" Its a Draw.");
                }
                gameState = 3;
            }
        }
    }

    void AutoPlay(){
        List<Integer> emptyBlocks = new ArrayList<>();

        for(int i=1; i<=9; i++){
            if(!(Player1.contains(i) || Player2.contains(i))){
                emptyBlocks.add(i);
            }
        }

        if(emptyBlocks.size() == 0) {
            CheckWinner();
            if(gameState == 1) {
                matchStatus = 3;
                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                ShowAlert(" Its a Draw.");
            }
            gameState = 3;
        } else {
            Random r = new Random();
            int randomIndex = r.nextInt(emptyBlocks.size());
            int selectedBock;

            if(difficultyLevel == 0) {
                selectedBock = emptyBlocks.get(randomIndex);
            } else {
                selectedBock = findBestMove(emptyBlocks, randomIndex);
            }

            ImageView selectedImage = (ImageView) findViewById(R.id.iv_11);
            switch (selectedBock) {
                case 1: selectedImage = (ImageView) findViewById(R.id.iv_11); break;
                case 2: selectedImage = (ImageView) findViewById(R.id.iv_12); break;
                case 3: selectedImage = (ImageView) findViewById(R.id.iv_13); break;

                case 4: selectedImage = (ImageView) findViewById(R.id.iv_21); break;
                case 5: selectedImage = (ImageView) findViewById(R.id.iv_22); break;
                case 6: selectedImage = (ImageView) findViewById(R.id.iv_23); break;

                case 7: selectedImage = (ImageView) findViewById(R.id.iv_31); break;
                case 8: selectedImage = (ImageView) findViewById(R.id.iv_32); break;
                case 9: selectedImage = (ImageView) findViewById(R.id.iv_33); break;
            }
            PlayGame(selectedBock, selectedImage);
        }
    }

    private int findBestMove(List<Integer> emptyBlocks, int randomIndex) {
        int bestMove = 0;

        /* ATTACK */
        if(Player2.contains(1) && Player2.contains(2) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove; }
        if(Player2.contains(2) && Player2.contains(3) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player2.contains(1) && Player2.contains(3) && emptyBlocks.contains(2)){ bestMove = 2; return bestMove; }

        if(Player2.contains(4) && Player2.contains(5) && emptyBlocks.contains(6)){ bestMove = 6; return bestMove; }
        if(Player2.contains(5) && Player2.contains(6) && emptyBlocks.contains(4)){ bestMove = 4; return bestMove; }
        if(Player2.contains(4) && Player2.contains(6) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        if(Player2.contains(7) && Player2.contains(8) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player2.contains(8) && Player2.contains(9) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player2.contains(7) && Player2.contains(9) && emptyBlocks.contains(3)){ bestMove = 8; return bestMove; }

        if(Player2.contains(1) && Player2.contains(4) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player2.contains(4) && Player2.contains(7) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player2.contains(1) && Player2.contains(7) && emptyBlocks.contains(4)){ bestMove = 4; return bestMove; }

        if(Player2.contains(2) && Player2.contains(5) && emptyBlocks.contains(8)){ bestMove = 8; return bestMove; }
        if(Player2.contains(5) && Player2.contains(8) && emptyBlocks.contains(2)){ bestMove = 2; return bestMove; }
        if(Player2.contains(2) && Player2.contains(8) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        if(Player2.contains(3) && Player2.contains(6) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player2.contains(6) && Player2.contains(9) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove; }
        if(Player2.contains(3) && Player2.contains(9) && emptyBlocks.contains(6)){ bestMove = 6; return bestMove; }

        if(Player2.contains(1) && Player2.contains(5) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player2.contains(5) && Player2.contains(9) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player2.contains(1) && Player2.contains(9) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }


        if(Player2.contains(3) && Player2.contains(5) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player2.contains(5) && Player2.contains(7) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove; }
        if(Player2.contains(3) && Player2.contains(7) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        /* DEFENSE */
        if(Player1.contains(1) && Player1.contains(2) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove;}
        if(Player1.contains(2) && Player1.contains(3) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player1.contains(1) && Player1.contains(3) && emptyBlocks.contains(2)){ bestMove = 2; return bestMove; }

        if(Player1.contains(4) && Player1.contains(5) && emptyBlocks.contains(6)){ bestMove = 6; return bestMove; }
        if(Player1.contains(5) && Player1.contains(6) && emptyBlocks.contains(4)){ bestMove = 4; return bestMove; }
        if(Player1.contains(4) && Player1.contains(6) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        if(Player1.contains(7) && Player1.contains(8) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player1.contains(8) && Player1.contains(9) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player1.contains(7) && Player1.contains(9) && emptyBlocks.contains(8)){ bestMove = 8; return bestMove; }

        if(Player1.contains(1) && Player1.contains(4) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player1.contains(4) && Player1.contains(7) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player1.contains(1) && Player1.contains(7) && emptyBlocks.contains(4)){ bestMove = 4; return bestMove; }

        if(Player1.contains(2) && Player1.contains(5) && emptyBlocks.contains(8)){ bestMove = 8; return bestMove; }
        if(Player1.contains(5) && Player1.contains(8) && emptyBlocks.contains(2)){ bestMove = 2; return bestMove; }
        if(Player1.contains(2) && Player1.contains(8) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        if(Player1.contains(3) && Player1.contains(6) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player1.contains(6) && Player1.contains(9) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove; }
        if(Player1.contains(3) && Player1.contains(9) && emptyBlocks.contains(6)){ bestMove = 6; return bestMove; }

        if(Player1.contains(1) && Player1.contains(5) && emptyBlocks.contains(9)){ bestMove = 9; return bestMove; }
        if(Player1.contains(5) && Player1.contains(9) && emptyBlocks.contains(1)){ bestMove = 1; return bestMove; }
        if(Player1.contains(1) && Player1.contains(9) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }


        if(Player1.contains(3) && Player1.contains(5) && emptyBlocks.contains(7)){ bestMove = 7; return bestMove; }
        if(Player1.contains(5) && Player1.contains(7) && emptyBlocks.contains(3)){ bestMove = 3; return bestMove; }
        if(Player1.contains(3) && Player1.contains(7) && emptyBlocks.contains(5)){ bestMove = 5; return bestMove; }

        /* OCCUPY CENTER */
        if(emptyBlocks.contains(5)) {
            bestMove = 5; return bestMove;
        }

        /* IMPROVISE */
        if(Player1.contains(5)) {
            if(emptyBlocks.contains(1) && !Player1.contains(1) && !Player2.contains(1) ) { bestMove = 1; return bestMove; }
            if(emptyBlocks.contains(3) && !Player1.contains(3) && !Player2.contains(3) ) { bestMove = 3; return bestMove; }
            if(emptyBlocks.contains(7) && !Player1.contains(7) && !Player2.contains(7) ) { bestMove = 7; return bestMove; }
            if(emptyBlocks.contains(9) && !Player1.contains(9) && !Player2.contains(9) ) { bestMove = 9; return bestMove; }
        }
        if(Player2.contains(5)) {
            if(emptyBlocks.contains(2) && !Player1.contains(2) && !Player2.contains(2) ) { bestMove = 2; return bestMove; }
            if(emptyBlocks.contains(4) && !Player1.contains(4) && !Player2.contains(4) ) { bestMove = 4; return bestMove; }
            if(emptyBlocks.contains(6) && !Player1.contains(6) && !Player2.contains(6) ) { bestMove = 6; return bestMove; }
            if(emptyBlocks.contains(8) && !Player1.contains(8) && !Player2.contains(8) ) { bestMove = 8; return bestMove; }
        }

        if(bestMove == 0 || Player2.contains(bestMove)) {
            bestMove = randomIndex;
        }

        return bestMove;
    }

    void ResetGame(){
        gameState = 1;
        activePlayer = 1;
        Player1 = new ArrayList<>();
        Player2 = new ArrayList<>();

        ImageView iv;
        iv = (ImageView) findViewById(R.id.iv_11); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_12); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_13); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_21); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_22); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_23); iv.setImageResource(0); iv.setEnabled(true);

        iv = (ImageView) findViewById(R.id.iv_31); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_32); iv.setImageResource(0); iv.setEnabled(true);
        iv = (ImageView) findViewById(R.id.iv_33); iv.setImageResource(0); iv.setEnabled(true);

    }

    void ShowAlert(String Title){

        if(playWithApp) {
            if(getMuteStatus() == 0) {
                if(matchStatus == 1) {
                    cheer.start();
                } else if(matchStatus == 2) {
                    defeat.start();
                } else if(matchStatus == 3) {
                    draw.start();
                }
            }
        }

        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
        b.setTitle(Title)
                .setMessage("Start a new game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetGame();
                    }
                })
                .setNegativeButton("Return to Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            back.cancel();
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), TicTacToe.class);
            startActivity(intent);
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }

    private int getMuteStatus() {
        SharedPreferences prefs = getSharedPreferences("tttgame", 0);
        return prefs.getInt("tttgamemmute", 0);
    }

    private int getDifficultyLevel() {
        SharedPreferences prefs = getSharedPreferences("tttgame", 0);
        return prefs.getInt("tttdifficulty", 0);
    }
}
