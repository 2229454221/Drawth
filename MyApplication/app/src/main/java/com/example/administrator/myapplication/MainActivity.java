package com.example.administrator.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private DrawView drawView;
    private SeekBar seekBar;
    private TextView textView;
    private ImageButton imageButton_width,imageButton_color;
    private LinearLayout showPop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 宽度选择器
        showPop = (LinearLayout)findViewById(R.id.showPopup);
        imageButton_color = (ImageButton)findViewById(R.id.ib_color);
        imageButton_width = (ImageButton)findViewById(R.id.ib_width);
        imageButton_width.setOnClickListener(this);
        imageButton_color.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_color:
                ColorSelector_PopupWindow csp = new ColorSelector_PopupWindow(MainActivity.this);
                csp.showPopupWindow_color(showPop);
                break;
            case R.id.ib_width:{
                WidthSelector_PopupWindow wsp = new WidthSelector_PopupWindow(MainActivity.this);
                wsp.showPopupWindow_width(showPop);
                break;
            }
        }
    }
}

