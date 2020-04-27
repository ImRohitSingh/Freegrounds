package com.rohit.freepaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rohit.freepaint.anagram.FindAnagram;
import com.rohit.freepaint.dodger.Dodger;
import com.rohit.freepaint.killthemall.KillThemAllActivity;
import com.rohit.freepaint.paint.FreePaint;
import com.rohit.freepaint.tictactoe.TicTacToe;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    private long backPressed = 0;

    private Toast back;

    private TextView welcomeText;

    private CircleImageView profileImage;
    private FancyButton signOut;

    MediaPlayer tictactoe;
    MediaPlayer paint;
    MediaPlayer killthemall;
    MediaPlayer dodger;
    MediaPlayer have_a_great_time;

    private GoogleSignInClient mGoogleSignInClient;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_main);
        signOut = (FancyButton) findViewById(R.id.sign_out_button);

        welcomeText = (TextView) findViewById(R.id.welcomeTextMain);

        tictactoe = MediaPlayer.create(getApplicationContext(), R.raw.tictactoe);
        paint = MediaPlayer.create(getApplicationContext(), R.raw.paint);
        killthemall = MediaPlayer.create(getApplicationContext(), R.raw.killthemall);
        dodger = MediaPlayer.create(getApplicationContext(), R.raw.dodger);
        have_a_great_time = MediaPlayer.create(getApplicationContext(), R.raw.have_a_great_time);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        if(getSigninStatus() == 1) {
            signOut.setVisibility(View.VISIBLE);
        }

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            welcomeText.setText("Welcome, " + personName + "!");

            Glide.with(this).load(String.valueOf(personPhoto)).into(profileImage);
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        signOut();
                        break;
                    // ...
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshmain);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateSigninStatus();
                       Toast.makeText(getApplicationContext(), "Successfully Signed Out", Toast.LENGTH_LONG).show();
                       finish();
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
    }

    public void onChoosingPaint(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                paint.start();
                Intent intent = new Intent(getApplicationContext(), FreePaint.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    public void onChoosingAnagram(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), FindAnagram.class);
                startActivity(intent);
            }}, 1500);
    }

    public void onChoosingTicTacToe(View view) {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
                tictactoe.start();
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
                killthemall.start();
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
                dodger.start();
                Intent intent = new Intent(getApplicationContext(), Dodger.class);
                startActivity(intent);
                finish();
            }}, 1500);
    }

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            have_a_great_time.start();
            back.cancel();
            super.onBackPressed();
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }

    private int getSigninStatus() {
        SharedPreferences prefs = getSharedPreferences("freegrounds", 0);
        return prefs.getInt("signin", 0);
    }

    private void updateSigninStatus() {
        SharedPreferences prefs = getSharedPreferences("freegrounds", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("signin", 0);
        editor.apply();
    }
}
