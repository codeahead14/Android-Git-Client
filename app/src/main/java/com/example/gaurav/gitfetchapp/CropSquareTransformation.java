package com.example.gaurav.gitfetchapp;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by GAURAV on 21-05-2016.
 */
public class CropSquareTransformation implements Transformation {
    @Override public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, size/2, size/2);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override public String key() { return "square()"; }
}
