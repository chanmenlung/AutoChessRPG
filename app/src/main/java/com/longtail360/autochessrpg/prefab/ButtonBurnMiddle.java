package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.longtail360.autochessrpg.R;

public class ButtonBurnMiddle extends FrameLayout {
    private TextView titleView;
    public ButtonBurnMiddle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String title;
        String color;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ButtonBurnMiddle, 0, 0);

        try {
            title = a.getString(R.styleable.ButtonBurnMiddle_btBurnMiddleText);
            color = a.getString(R.styleable.ButtonBurnMiddle_btBurnMiddleColor);
        } finally {
            a.recycle();
        }
        if("green".equals(color)){
            LayoutInflater.from(context).inflate(R.layout.button_burn_middle_green, this, true);
        }else if("orange".equals(color)){
            LayoutInflater.from(context).inflate(R.layout.button_burn_middle_orange, this, true);
        }else {
            LayoutInflater.from(context).inflate(R.layout.button_burn_middle_gray, this, true);
        }

        // Throw an exception if required attributes are not set
        if (title == null) {
            throw new RuntimeException("No title provided");
        }

        final View thisLayout = findViewById(R.id.thisLayout);
        final View overlay = findViewById(R.id.overlay);
        overlay.setVisibility(INVISIBLE);
        titleView = (TextView) findViewById(R.id.buttonText);
        titleView.setText(title);

        thisLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    overlay.setVisibility(VISIBLE);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    overlay.setVisibility(INVISIBLE);
                }
                return ButtonBurnMiddle.super.onTouchEvent(event);
            }
        });
    }

    public void setText(String text){
        titleView.setText(text);
    }
}
