package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.longtail360.autochessrpg.R;

/**
 * Created by chanmenlung on 30/1/2019.
 */

public class HeadBackButton extends FrameLayout {
    TextView titleText;
    public View backBt;
    public ImageView imageView;
    public HeadBackButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.head_back_button, this, true);

        String title;
        int imageId;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeadBackButton, 0, 0);

        try {
            title = a.getString(R.styleable.HeadBackButton_btHeadBackText);
        } finally {
            a.recycle();
        }

        // Throw an exception if required attributes are not set
        if (title == null) {
            throw new RuntimeException("No title provided");
        }

        backBt = findViewById(R.id.innerBackBt);
        titleText = (TextView)findViewById(R.id.titleText);
        titleText.setText(title);
        imageView = findViewById(R.id.headBackBtImage);
        backBt.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return HeadBackButton.super.onTouchEvent(event);
            }
        });

    }

    public void setText(String text){
        titleText.setText(text);
    }
}
