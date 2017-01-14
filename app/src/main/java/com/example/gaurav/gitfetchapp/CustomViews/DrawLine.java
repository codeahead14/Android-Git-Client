package com.example.gaurav.gitfetchapp.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by GAURAV on 14-01-2017.
 */

public class DrawLine extends View {
    private static final String TAG = DrawLine.class.getName();
    Paint linePaint;
    private float viewLeft;
    private float viewTop;
    private float viewBottom;
    private float viewRight;
    private float viewPadding;

    public DrawLine(Context context){
        super(context);
    }

    public DrawLine(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    public void init(){
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(40.0f,0.0f,40.0f,getHeight(),linePaint);
    }
}
