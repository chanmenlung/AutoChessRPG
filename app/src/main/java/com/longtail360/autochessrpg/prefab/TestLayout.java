package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.longtail360.autochessrpg.R;

public class TestLayout extends FrameLayout {
    public TestLayout(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.test, this);
    }
}
