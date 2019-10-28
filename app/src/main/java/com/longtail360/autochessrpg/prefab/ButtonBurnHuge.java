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

/**
 * Created by chanmenlung on 29/1/2019.
 */

public class ButtonBurnHuge extends FrameLayout {
    private TextView titleView;
    private View background;
    public ButtonBurnHuge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_burn_huge, this, true);

        String title;
        Integer iconRid;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ButtonBurnHuge, 0, 0);

        try {
            title = a.getString(R.styleable.ButtonBurnHuge_btBurnHugeText);
        } finally {
            a.recycle();
        }

        // Throw an exception if required attributes are not set
        if (title == null) {
            throw new RuntimeException("No title provided");
        }

        final View thisLayout = findViewById(R.id.thisLayout);
        final View overlay = findViewById(R.id.overlay);
        background = findViewById(R.id.background);
        overlay.setVisibility(INVISIBLE);
        titleView = (TextView) findViewById(R.id.buttonText);
        titleView.setText(title);

        thisLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    background.setVisibility(INVISIBLE);
                    overlay.setVisibility(VISIBLE);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    background.setVisibility(VISIBLE);
                    overlay.setVisibility(INVISIBLE);
                }
                return ButtonBurnHuge.super.onTouchEvent(event);
            }
        });

    }

    public void setText(String text){
        titleView.setText(text);
    }
}
