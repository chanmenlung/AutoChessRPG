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


public class MenuPanel extends FrameLayout {
    private TextView titleView;
    private View background;
    public MenuPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_menu_panel, this, true);

        String title;
        Integer iconRid;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ButtonMenuPanel, 0, 0);

        try {
            title = a.getString(R.styleable.ButtonMenuPanel_btMenuPanelTitle);
            iconRid = a.getResourceId(R.styleable.ButtonMenuPanel_btMenuPanelIcon,0);
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
                return MenuPanel.super.onTouchEvent(event);
            }
        });

        ImageView icon = (ImageView)findViewById(R.id.icon);
        icon.setImageResource(iconRid);
    }

    public void setText(String text){
        titleView.setText(text);
    }
}
