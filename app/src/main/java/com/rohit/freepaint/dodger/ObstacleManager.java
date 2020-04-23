package com.rohit.freepaint.dodger;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class ObstacleManager {

    Context activity;

    private List<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    public ObstacleManager(Context activity, int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        this.activity = activity;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for(Obstacle obstacle: obstacles) {
            if(obstacle.playerCollide(player)) {
                updateHighScore();
                return true;
            }
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) { //obstacles.get(obstacles.size() - 1).getRectangle().bottom < 0
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }

    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1 + (startTime - initTime) / 2000)) * Constants.SCREEN_HEIGHT / 10000.0f; // 1000 = fast, 2000 = slow
        for(Obstacle obstacle: obstacles) {
            obstacle.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >=  Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle obstacle: obstacles) {
            obstacle.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(125f);
        paint.setColor(Color.GRAY);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

    public void updateHighScore() {
        SharedPreferences prefs = activity.getSharedPreferences("dodgergame", 0);
        if (prefs.getInt("dodgerhighscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("dodgerhighscore", score);
            editor.apply();
        }
    }
}
