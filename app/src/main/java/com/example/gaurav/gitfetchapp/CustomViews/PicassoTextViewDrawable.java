package com.example.gaurav.gitfetchapp.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by GAURAV on 14-01-2017.
 */

public class PicassoTextViewDrawable extends TextView implements Target {

    public PicassoTextViewDrawable(Context context){
        super(context);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Drawable mDrawable = new BitmapDrawable(getResources(), bitmap);
        setCompoundDrawablesWithIntrinsicBounds(mDrawable,null,null,null);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
