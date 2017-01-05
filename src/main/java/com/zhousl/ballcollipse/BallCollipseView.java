package com.zhousl.ballcollipse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/5.
 */

public class BallCollipseView extends View {

    private Paint mPaint;
    private int height;
    private int width;
    private Path framePath;
    private float lineLength;
    private float angle = -30;//线与坚直方向的夹角
    private final int left = 1;
    private final int right = 2;
    private int Col = right;
    private boolean up=true;
    private float t;
    private float a=1.0f/40;

    public BallCollipseView(Context context) {
        this(context, null);
    }

    public BallCollipseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        framePath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getMeasuredHeight();
        width = getMeasuredWidth();

        framePath.moveTo(width / 4, height * 3 / 4);
        framePath.lineTo(width / 4, height / 4);
        framePath.lineTo(width * 3 / 4, height / 4);
        framePath.lineTo(width * 3 / 4, height * 3 / 4);

        lineLength = height / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (angle <= -30) {
            up=true;
        }
        if (angle >= 30) {
            up=false;
        }
        if (up){//往右走，增加
            if (Col==right){//-30~0
                angle=-45+a*t*t/2;
            }else {//0~30
//                angle=//TODO 有空再做
            }
//            angle++;
        }else {//往左走，减小
//            if (Col==left){//30~0
//
//            }else {//0~-30
//
//            }
            angle=45-a*t*t/2;
        }
        if (angle == 0 && Col == right) {
            Col = left;
        } else if (angle == 0 && Col == left) {
            Col = right;
        }
        drawFrame(canvas);
        drawPendants(canvas);
        invalidate();
    }

    private void drawFrame(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(framePath, mPaint);
    }

    private void drawPendants(Canvas canvas) {
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        if (angle <=0) {
            canvas.drawLine((float) (width * 1.4 / 4), height / 4,
                    (float) (width * 1.4 / 4 + lineLength * Math.sin(angle / 360 * Math.PI)), (float) (height / 4 + lineLength * Math.cos(angle / 360 * Math.PI)),
                    mPaint);
        }
        if (angle >= 0) {
            canvas.drawLine((float) (width * 2.6 / 4), height / 4,
                    (float) (width * 2.6 / 4 + lineLength * Math.sin(angle / 360 * Math.PI)), (float) (height / 4 + lineLength * Math.cos(angle / 360 * Math.PI)),
                    mPaint);
        }
        for (int i = 1; i < 5; i++) {
            if (Col == right) {
                canvas.drawLine((float) (width * 1.4 / 4 + width * 0.3 * i / 4), height / 4, (float) (width * 1.4 / 4 + width * 0.3 * i / 4), height * 2 / 4, mPaint);
            } else if (Col == left) {
                canvas.drawLine((float) (width * 1.4 / 4 + width * 0.3 * (i - 1) / 4), height / 4, (float) (width * 1.4 / 4 + width * 0.3 * (i - 1) / 4), height * 2/ 4, mPaint);
            }
        }

        mPaint.setStyle(Paint.Style.FILL);

        if (angle <=0) {
            canvas.drawCircle((float) (width * 1.4 / 4 + lineLength * Math.sin(angle / 360 * Math.PI)),
                    (float) (height / 4 + lineLength * Math.cos(angle / 360 * Math.PI)),
                    (float) (width * 0.15 / 4), mPaint);
        }
        if (angle >= 0) {
            canvas.drawCircle((float) (width * 2.6 / 4 + lineLength * Math.sin(angle / 360 * Math.PI)),
                    (float) (height / 4 + lineLength * Math.cos(angle / 360 * Math.PI)),
                    (float) (width * 0.15 / 4), mPaint);
        }
        for (int i = 1; i < 5; i++) {
            if (Col == right) {
                canvas.drawCircle((float) (width * 1.4 / 4 + width * 0.3 / 4 * i), height * 2 / 4, (float) (width * 0.15 / 4), mPaint);
            } else if (Col == left) {
                canvas.drawCircle((float) (width * 1.4 / 4 + width * 0.3 / 4 * (i - 1)), height * 2 / 4, (float) (width * 0.15 / 4), mPaint);
            } else {

            }
        }
    }
}
