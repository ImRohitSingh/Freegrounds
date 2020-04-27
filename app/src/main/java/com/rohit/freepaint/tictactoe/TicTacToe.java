package com.rohit.freepaint.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;
import com.rohit.freepaint.tictactoe.AboutActivity;
import com.rohit.freepaint.tictactoe.SinglePlayerActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class TicTacToe extends AppCompatActivity {

    private long backPressed = 0;

    private Toast back;

    private ImageView muteBtn;
    private ImageView unmuteBtn;

    private GoogleSignInClient mGoogleSignInClient;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        muteBtn = (ImageView) findViewById(R.id.tttmutebtn);
        unmuteBtn = (ImageView) findViewById(R.id.tttunmutebtn);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_ttt);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Glide.with(this).load(String.valueOf(personPhoto)).into(profileImage);
        }

        if(getMuteStatus() == 0) {
            muteBtn.setVisibility(View.VISIBLE);
        } else {
            unmuteBtn.setVisibility(View.VISIBLE);
        }

        muteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unmuteBtn.setVisibility(View.VISIBLE);
                muteBtn.setVisibility(View.GONE);
                updateMuteStatus(1);
            }
        });

        unmuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unmuteBtn.setVisibility(View.GONE);
                muteBtn.setVisibility(View.VISIBLE);
                updateMuteStatus(0);
            }
        });

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);
    }

    public void startGame_singlePlayer(View view) {
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
        finish();
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

    private int getMuteStatus() {
        SharedPreferences prefs = getSharedPreferences("tttgame", 0);
        return prefs.getInt("tttgamemmute", 0);
    }

    private void updateMuteStatus(int status) {
        SharedPreferences prefs = getSharedPreferences("tttgame", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tttgamemmute", status);
        editor.apply();
    }
}
