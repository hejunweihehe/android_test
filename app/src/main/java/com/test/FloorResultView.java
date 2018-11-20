package com.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import test.com.test.R;

/**
 * 底板测试列表控件
 */
public class FloorResultView extends LinearLayout {
    TextView txt_number;
    TextView txt_description;
    TextView txt_result;
    TextView txt_error_content;

    public FloorResultView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_floor, this, true);
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_number.setText("number");
        txt_description = (TextView) findViewById(R.id.txt_description);
        txt_description.setText("txt_description");
        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_result.setText("txt_result");
        txt_error_content = (TextView) findViewById(R.id.txt_error_content);
        txt_error_content.setText("txt_error_content");
    }

    public FloorResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_floor, this, true);
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_number.setText("number");
        txt_description = (TextView) findViewById(R.id.txt_description);
        txt_description.setText("txt_description");
        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_result.setText("txt_result");
        txt_error_content = (TextView) findViewById(R.id.txt_error_content);
        txt_error_content.setText("txt_error_content");
    }

    public FloorResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
