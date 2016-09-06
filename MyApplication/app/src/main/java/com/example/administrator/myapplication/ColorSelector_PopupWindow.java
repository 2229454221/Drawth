package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ColorSelector_PopupWindow extends PopupWindow implements View.OnClickListener{
    private View contentView;

    private ImageView iv_color_picker,iv_color_range;
    private TextView et_black,et_white;

    private int range_radius;               // 圆盘半径
    private int picker_radius;              // 颜色选择半径
    private int centralX;
    private int centralY;                    // 圆盘中心坐标
    private int picker_centralX;
    private int picker_centralY;            // 选择器中心坐标
    private Bitmap bitmap;                    // 颜色选择盘图片
    private int r,g,b;

    public  ColorSelector_PopupWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popupwindow_color, null);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置PopupWindow的View
        this.setContentView(contentView);
        // 设置PopupWindow弹出框的宽高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出框可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(colorDrawable);

        // 事件
        iv_color_picker = (ImageView)contentView.findViewById(R.id.iv_color_picker);
        iv_color_range = (ImageView)contentView.findViewById(R.id.iv_color_range);
        et_black = (TextView) contentView.findViewById(R.id.tv_black);
        //    et_black.setOnClickListener(this);
        et_white = (TextView) contentView.findViewById(R.id.tv_white);
        //     et_white.setOnClickListener(this);
        // 选择器触摸监听
        iv_color_picker.setOnTouchListener(new View.OnTouchListener() {
            int lastX,lastY; // 上次触摸坐标
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果未初始化
                if(range_radius == 0){
                    range_radius = iv_color_range.getWidth()/2;  // 圆盘半径
                    picker_radius = iv_color_picker.getWidth()/2;// 选择器半径
                    centralX = iv_color_range.getRight() - range_radius;
                    centralY = iv_color_range.getBottom() - (iv_color_range.getHeight()/2); // 中心坐标
                    bitmap = ((BitmapDrawable)iv_color_range.getDrawable()).getBitmap(); // 获取圆盘图片
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX(); //相对于屏幕的坐标
                        lastY = (int) event.getRawY();
                        contentView.getParent().requestDisallowInterceptTouchEvent(true);// 通知父控件不要拦截本控件Touch事件
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 拖动距离
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        // 拖动后相对于父控件的新坐标
                        int left = v.getLeft() + dx;
                        int right = v.getRight() + dx;
                        int top = v.getTop() + dy;
                        int bottom = v.getBottom() + dy;
                        // 选择器圆心坐标
                        picker_centralX = right - picker_radius;
                        picker_centralY = bottom - picker_radius;
                        // 选择器圆心与圆盘圆心距离
                        double diff = Math.sqrt((centralY - picker_centralY) * (centralY - picker_centralY)
                                + (centralX - picker_centralX) * (centralX - picker_centralX)) + picker_radius / 2; // 两个圆心坐标+颜色选择器半径
                        // 在边距内，则拖动
                        if (diff <= range_radius - ((double) range_radius / 7.5)) {
                            v.layout(left, top, right, bottom);    // 相对于父控件新位置
                            double height_proportion = 1791/570,width_proportion = 1794/570;
                            // 获取选择器圆心像素（使用picker相对于圆盘的坐标）
                            int pixel = bitmap.getPixel((int)((picker_centralX-iv_color_range.getLeft())*width_proportion),
                                    (int)((picker_centralY-iv_color_range.getTop())*height_proportion));//获取选择器圆心像素
                            //读取颜色
                            r = Color.red(pixel);
                            g = Color.green(pixel);
                            b = Color.blue(pixel);

                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                        }
                        // 通知父控件不要拦截本控件Touch事件
                        contentView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
        });
    }
    public void showPopupWindow_color(View parent){
        if(!this.isShowing()){
            this.showAsDropDown(parent,500,0);
        }
        else{
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_black:
                r = 0;
                g = 0;
                b = 0;
                break;
            case R.id.tv_white:
                r = 255;
                g = 255;
                b = 255;
                break;
        }
    }
}
