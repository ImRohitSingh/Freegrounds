package com.rohit.freepaint.anagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rohit.freepaint.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FindAnagram extends AppCompatActivity {

    private TextView aWord;
    private EditText aGuess;
    private ImageView playBtn;
    private ImageView stopBtn;
    private ImageView clueBtn;

    private ImageView b_check;

    private TextView currScore;
    private TextView highScore;
    private TextView clueText;

    private ProgressBar pb;
    private int counter = 0;
    private int attempt = 0;
    private int timerTime = 0;
    private long startTime = 0;

    private String[] dictionary = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
            "Twelve", "Thirteen", "Anagram", "Chemistry", "Physics", "Geomtery", "India", "Mathematics", "Squirrel", "Champion",
            "Xerox", "Mango", "Park", "Taste", "Test", "Therapist", "Message", "Eclipse", "Sun", "Moon", "Universe",
            "Intel", "Knowledge", "Java", "Tea", "Coffee", "Drink", "Toast", "Bread", "Alphabet", "Cheers", "Draw",
            "Window", "Door" };
    private String currWord;

    private Random random;

    private Timer timer;
    private Timer myTimer;

    private boolean gameActive;
    private long backPressed = 0;

    private Toast back;

    private int score = 0;

    private int highScoreUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_anagram);

        aWord = (TextView) findViewById(R.id.aWord);
        aGuess = (EditText) findViewById(R.id.aGuess);
        playBtn = (ImageView) findViewById(R.id.playbutton);
        stopBtn = (ImageView) findViewById(R.id.stopbutton);
        clueBtn = (ImageView) findViewById(R.id.cluebtn);

        b_check = (ImageView) findViewById(R.id.b_check);

        pb = (ProgressBar) findViewById(R.id.progress_bar);

        currScore = (TextView) findViewById(R.id.currentScore);
        highScore = (TextView) findViewById(R.id.highestScore);
        clueText = (TextView) findViewById(R.id.clue);

        SharedPreferences prefs = getSharedPreferences("anagram", MODE_PRIVATE);
        highScore.setText("HighScore: " + prefs.getInt("anagramhighscore", 0));

        gameActive = false;

        highScoreUpdated = 0;

        random = new Random();

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        b_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFinalHighScore();
                timer.cancel();
                Toast.makeText(getApplicationContext(), "Total Score: " + score, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), FindAnagram.class));
                finish();
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActive = true;
                b_check.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);
                clueBtn.setVisibility(View.VISIBLE);
                playBtn.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                loadGame();
            }
        });

        clueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis() - startTime < 4000) {
                    Toast.makeText(getApplicationContext(), "Try after 4 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    int length = currWord.length() - 2;
                    String clue = currWord.charAt(0) + generateStars(length) + currWord.charAt(currWord.length() - 1);
                    clueText.setText(clue);
                }
            }
        });

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);

    }

    private String generateStars(int length) {
        String stars = "";
        for(int i = 1; i <= length; ++i) {
            stars += "*";
        }
        return stars;
    }

    private void loadGame() {
        currWord = dictionary[random.nextInt(dictionary.length)];
        aWord.setText(shuffledWord(currWord));

        startTime = System.currentTimeMillis();

        currScore.setText("" + score);

        clueText.setText("");

        notifyHighScore();

        aGuess.setText("");

        attempt = 0;
        counter = 0;
        timerTime = 0;

        stopBtn.setVisibility(View.VISIBLE);
        playBtn.setVisibility(View.GONE);
        pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#4081af")));

        if(timer != null) {
            timer.cancel();
            timer.purge();
        }

        gameActive = true;

        loadProgressBar();
    }

    private String shuffledWord(String word) {
        List<String> letters = Arrays.asList(word.split(""));
        Collections.shuffle(letters);
        String shuffledWord = "";
        for(String letter: letters) {
            shuffledWord += letter;
        }
        return shuffledWord;
    }

    private void loadProgressBar() {
        counter = 0;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);
                if(counter == 100) {
                    timer.cancel();
                    timerTime = 1;
                }
                if(counter == 75) {
                    pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
            }
        };

        timer.schedule(timerTask, 0, 300);
    }

    private void checkGuess() {
        if(aGuess.getText().toString().equalsIgnoreCase(currWord)) {
            score += 3;
            loadGame();
        } else {
            ++attempt;
            if((3 - attempt) > 0) {
                Toast.makeText(getApplicationContext(), "Remaining Attempts: " + (3 - attempt), Toast.LENGTH_SHORT).show();
            } else {
                score -= 2;
            }
        }
        if(attempt > 2 || timerTime == 1 && gameActive) {
            loadGame();
        }
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.
            //Do something to the UI thread here
            if(timerTime == 1 && gameActive) {
                --score;
                loadGame();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()) {
            if(gameActive) {
                Toast.makeText(getApplicationContext(), "Total Score: " + score, Toast.LENGTH_SHORT).show();
                updateFinalHighScore();
            }
            back.cancel();
            super.onBackPressed();
            finish();
        } else {
            back.show();
        }
        backPressed = System.currentTimeMillis();
    }

    private void notifyHighScore() {
        SharedPreferences prefs = getSharedPreferences("anagram", 0);
        if (prefs.getInt("anagramhighscore", 0) < score) {
            ++highScoreUpdated;
            if(highScoreUpdated == 1) {
                Toast.makeText(getApplicationContext(), "New High Score", Toast.LENGTH_SHORT).show();
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("anagramhighscore", score);
            editor.apply();
        }
    }

    private void updateFinalHighScore() {
        SharedPreferences prefs = getSharedPreferences("anagram", 0);
        if (prefs.getInt("anagramhighscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("anagramhighscore", score);
            editor.apply();
        }
    }

}
