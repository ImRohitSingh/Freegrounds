package com.rohit.freepaint.dodger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dodger extends AppCompatActivity {

    private long backPressed = 0;
    private Toast back;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageView muteBtn;
    private ImageView unmuteBtn;

    private GoogleSignInClient mGoogleSignInClient;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodger);

        TextView highScoreTxt = findViewById(R.id.highscoretext);

        SharedPreferences prefs = getSharedPreferences("dodgergame", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("dodgerhighscore", 0));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        muteBtn = (ImageView) findViewById(R.id.dodgermutebtn);
        unmuteBtn = (ImageView) findViewById(R.id.dodgerunmutebtn);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_dodger);

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TextView highScoreTxt = findViewById(R.id.highscoretext);

                SharedPreferences prefs = getSharedPreferences("dodgergame", MODE_PRIVATE);
                highScoreTxt.setText("HighScore: " + prefs.getInt("dodgerhighscore", 0));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);
    }

    public void start(View view) {
        openDialog();
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

    private void openDialog() {
        DodgerModalLayout modalLayout = new DodgerModalLayout();
        modalLayout.show(getSupportFragmentManager(), "Choose Avatar");
    }

    private int getMuteStatus() {
        SharedPreferences prefs = getSharedPreferences("dodgergame", 0);
        return prefs.getInt("dodgermmute", 0);
    }

    private void updateMuteStatus(int status) {
        SharedPreferences prefs = getSharedPreferences("dodgergame", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("dodgermmute", status);
        editor.apply();
    }
}
