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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rohit.freepaint.anagram.FindAnagram;
import com.rohit.freepaint.credits.CreditsActivity;
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
    MediaPlayer anagram;
    MediaPlayer have_a_great_time;

    private ImageView credits;

    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;

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
        anagram = MediaPlayer.create(getApplicationContext(), R.raw.short_guitar);
        have_a_great_time = MediaPlayer.create(getApplicationContext(), R.raw.have_a_great_time);

        credits = (ImageView) findViewById(R.id.seecredits);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button_main);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        if(getSigninStatus() == 1) {
            signOut.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
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

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button_main:
                        progressBar.setVisibility(View.VISIBLE);
                        signIn();
                        break;
                    // ...
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        progressBar.setVisibility(View.VISIBLE);
                        signOut();
                        break;
                    // ...
                }
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreditsActivity.class));
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
                        updateSigninStatus(0);
                       Toast.makeText(getApplicationContext(), "Successfully Signed Out", Toast.LENGTH_LONG).show();
                       progressBar.setVisibility(View.GONE);
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
                anagram.start();
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

    private void updateSigninStatus(int status) {
        SharedPreferences prefs = getSharedPreferences("freegrounds", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("signin", status);
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateSigninStatus(1);
            Toast.makeText(getApplicationContext(), "Successfully Signed In", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
            finish();
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error:", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
