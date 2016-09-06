package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class DrawView extends View{

    // 灵敏度
    private static final int SENSITIVITY = 0;
    // 初始坐标
    private float preX = 0;
    private float preY = 0;
    // 画笔 画布 路径
    private Paint mPaint = null;
    private Canvas mCanvas = null;
    private Path mPath = null;
    private List<PathAndPaint> mList = new ArrayList<PathAndPaint>();
    // 其他数值
    private int screenWidth = 0;
    private int screenHeight = 0;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取屏幕大小
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        // 初始化
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint(Paint.DITHER_FLAG);
        // 对画笔进行处理
        mPaint.setDither(true);   // 平滑处理 防抖动
        mPaint.setAntiAlias(true); // 抗锯齿效果
        // 设置初始画笔风格
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10.0f);
        mPaint.setStyle(Paint.Style.STROKE); // 设置填充效果
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 笔刷画曲线的形状
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 笔刷离开时的形状
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸位置
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mPath.reset();
                mPath.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y  - preY);
                if(dx >= SENSITIVITY || dy >= SENSITIVITY){
                        mPath.quadTo(preX,preY,(preX+x)/2,(preY+y)/2);
                        preX = x;
                        preY = y;
                        mList.add(new PathAndPaint(new Path(mPath),new Paint(mPaint)));
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for(PathAndPaint pp:mList){
            canvas.drawPath(pp.getPath(),pp.getPaint());
        }
    }
}
