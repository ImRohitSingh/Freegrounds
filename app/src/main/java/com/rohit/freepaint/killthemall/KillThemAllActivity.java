package com.rohit.freepaint.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.rohit.freepaint.killthemall.KillThemAllGameActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class KillThemAllActivity extends AppCompatActivity {

    private boolean isMute;

    private long backPressed = 0;
    private Toast back;

    private GoogleSignInClient mGoogleSignInClient;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kill_them_all);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), KillThemAllGameActivity.class));
                finish();
            }
        });

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        TextView highScoreTxt = findViewById(R.id.highscoretext);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("highscore", 0));

        isMute = prefs.getBoolean("isMute", false);

        final ImageView volumeCtrl = findViewById(R.id.volumeControl);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_kta);

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

        if (isMute)
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_white_24dp);
        else
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_white_24dp);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isMute = !isMute;
                if (isMute)
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off_white_24dp);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up_white_24dp);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();

            }
        });

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
