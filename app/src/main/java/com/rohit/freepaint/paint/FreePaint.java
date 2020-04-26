package com.rohit.freepaint.paint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rohit.freepaint.MainActivity;
import com.rohit.freepaint.R;

import java.util.UUID;

public class FreePaint extends AppCompatActivity implements View.OnClickListener {

    private ImageButton currPaint, drawBtn, baru, erase, save, exit;
    private FreePaintView drawView;

    private long backPressed = 0;
    private Toast back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_paint);

        drawView = (FreePaintView) findViewById(R.id.drawing);

        drawBtn = (ImageButton) findViewById(R.id.draw_btn);
        baru = (ImageButton) findViewById(R.id.new_btn);
        erase = (ImageButton) findViewById(R.id.erase_btn);
        save = (ImageButton) findViewById(R.id.save_btn);
        exit = (ImageButton) findViewById(R.id.exit_btn);

        back = Toast.makeText(getApplicationContext(), "Press back again", Toast.LENGTH_SHORT);

        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);

        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        drawBtn.setOnClickListener(this);
        baru.setOnClickListener(this);
        erase.setOnClickListener(this);
        save.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    public void paintClicked(View view) {
        if(view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.draw_btn) {
            drawView.setupPaint();
        }
        if(v.getId() == R.id.erase_btn) {
            drawView.setErase(true);
            drawView.setBrushSize(drawView.getBrushSize());
        }
        if(v.getId() == R.id.new_btn) {
            AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
            b.setTitle("New File")
                    .setMessage("Start a new drawing?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            drawView.startNew();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Continue Here", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.alert_dark_frame)
                    .show();
        }
        if(v.getId() == R.id.save_btn) {
            AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
            b.setTitle("Save")
                    .setMessage("Save to Gallery?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            drawView.setDrawingCacheEnabled(true);

                            String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), drawView.getDrawingCache(), UUID.randomUUID().toString() + ".png", "drawing");

                            if(imgSaved != null) {
                                Toast.makeText(getApplicationContext(), "Saved to Gallery", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enable Storage permissions for this app", Toast.LENGTH_SHORT).show();
                            }
                            drawView.destroyDrawingCache();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.alert_dark_frame)
                    .show();
        }
        if(v.getId() == R.id.exit_btn) {
            AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
            b.setTitle("Exit")
                    .setMessage("Have you saved your changes?")
                    .setPositiveButton("Exit Anyway", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "Save before you exit", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setIcon(android.R.drawable.alert_dark_frame)
                    .show();
        }
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
