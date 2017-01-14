package com.example.gaurav.gitfetchapp.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import org.w3c.dom.Text;

/**
 * Created by GAURAV on 11-01-2017.
 */

public class RectShapeView extends TextView {
    Paint paint;
    private AttributeSet attrs;
    private int shapeColor;

    public RectShapeView(Context context){
        super(context);
    }

    public RectShapeView(Context context, AttributeSet attrs){
        super(context,attrs);
        setAttrs(attrs);
        setupPaint();

    }

    private void setAttrs(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RectShapeView, 0, 0);
        try {
            shapeColor = a.getColor(R.styleable.RectShapeView_shapeColor, Color.BLACK);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int color) {
        this.shapeColor = color;
        invalidate();
        requestLayout();
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(shapeColor);
    }


    @Override
    protected void onDraw(Canvas canvas){
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        float left = getLeft();
        float top = getTop();
        float right = getRight();
        float bottom = getBottom();

        canvas.drawRect(left,top,right,bottom,paint);
    }
}
