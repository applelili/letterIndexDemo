package com.example.myletterindex.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.myletterindex.util.DensityUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/31.
 */

public class LetterView extends View {
    private static String[] letters={"县区",
            "定位","最近","热门", "A", "B", "C", "D",
            "E", "F", "G", "H","J", "K", "L", "M", "N","P", "Q",
            "R", "S", "T","W", "X", "Y", "Z"
    };
    private int width;
    private Paint mPaint;
    private int height;
    private OnSlidingListener listener;
    private int priviousPosition = -1;
    private TextView text;
    private boolean showBg;
    private Timer timer;


    public LetterView(Context context) {
        this(context,null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //准备画笔
    private void init(Context context) {
        //画笔抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(DensityUtil.dip2px(context,14));
        mPaint.setColor(Color.parseColor("#0ab6b0"));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);//加粗
    }
    //画
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showBg){
            canvas.drawColor(Color.parseColor("#55666666"));
        }
        for (int i = 0; i <letters.length ; i++) {
            String letter=letters[i];
            //文字的横坐标
            float x=width/2-mPaint.measureText(letter)/2;
            //高度
            Rect bounds=new Rect();
            float y=height/2+bounds.height()/2 +i*height;
            canvas.drawText(letter,x,y,mPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //文本框的高度和宽度
        height = (int) (getMeasuredHeight()/(letters.length*1.0f)+0.5f);
        width = getMeasuredWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentPosition= (int) (event.getY()/height);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                showBg=true;
                if(currentPosition>=0 &&currentPosition<letters.length){
                    String letter=letters[currentPosition];
                    listener.onSliding(letter);
                    text.setVisibility(VISIBLE);
                    text.setText(letter);
                    priviousPosition=currentPosition;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg=false;
                priviousPosition=-1;//不为当前位置时候
                timer = new Timer(false);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        text.post(new TimerTask() {
                            @Override
                            public void run() {
                                text.setVisibility(GONE);

                            }
                        });
                    }
                },500);
                break;

        }
        return true;

    }
    public void setShowText(TextView toast){
        this.text=toast;
    }

    public interface OnSlidingListener{
        void onSliding(String letter);
    }
    public void setOnSlidingListener(OnSlidingListener listener){
        this.listener=listener;
    }
}
