package com.example.mypaintv0;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PaintView extends View {

    Activity2 activity2Obj;

    public static int BRUSH_SIZE = 0;
    public int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    public int strokeWidth;
    private boolean emboss;
    private boolean blur;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    private Bitmap mBitmap;
    public Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    int normalColor;



    public PaintView(Context context) {
        //super(context);
        this(context, null);
        setSaveEnabled(true);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
        setSaveEnabled(true);

        mEmboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(5,BlurMaskFilter.Blur.NORMAL);


    }

    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    public void normal(){
        emboss = false;
        blur = false;
        setColor(getNormalColor1());
    }

    public void emboss(){
        emboss = true;
        blur = false;
    }

    public void blur(){
        emboss = false;
        blur = true;
    }

    public void effacer() {
        if(getColor() == -1){

        }
        else {
            setNormalColor1(getColor());
        }
        if(getNormalColor1() != DEFAULT_BG_COLOR){
            setColor(DEFAULT_BG_COLOR);
        }

    }

    /*public void pipette(){
        if(touchStart(mX, mY)){
            mBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        }
    }*/

    public static Bitmap getBitmap(Canvas canvas) {
        try {
            java.lang.reflect.Field field = Canvas.class.getDeclaredField("mBitmap");
            field.setAccessible(true);
            return (Bitmap)field.get(canvas);
        }
        catch (Throwable t) {
            return null;
        }
    }




    public void setNormalColor1(int x){
        normalColor = x;
    }
    public int getNormalColor1(){
        return this.normalColor;
    }


    public void clear(){
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        //normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for(FingerPath fp : paths){
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);

            if(fp.emboss)
                mPaint.setMaskFilter(mEmboss);
            else if(fp.blur)
                mPaint.setMaskFilter(mBlur);
            mCanvas.drawPath(fp.path, mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
        //Log.i("",String.valueOf(getStrokeWidth()));
    }
    public int getStrokeWidth(){
        return this.strokeWidth;
    }

    public void setColor(int color) {
        this.currentColor = color;
        invalidate();
        //Log.i("",String.valueOf(getStrokeWidth()));
    }
    public int getColor(){
        return this.currentColor;
    }

    public void touchStart(float x, float y){
        mPath = new Path();
        //Log.i("", String.valueOf(getStrokeWidth()));
        //setStrokeWidth(getStrokeWidth());


        FingerPath fp = new FingerPath(currentColor, emboss, blur, strokeWidth, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }
}
