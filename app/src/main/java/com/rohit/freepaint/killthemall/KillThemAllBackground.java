package com.rohit.freepaint.killthemall;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rohit.freepaint.R;

public class KillThemAllBackground {

    int x = 0, y = 0;
    Bitmap background;

    KillThemAllBackground (int screenX, int screenY, Resources res) {

        background = BitmapFactory.decodeResource(res, R.drawable.killthemallbackground);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }
}
