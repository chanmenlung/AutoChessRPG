package com.longtail360.autochessrpg.activity;

import android.app.Activity;

import android.app.Activity;

import android.content.ClipData;
import android.content.ClipDescription;

import android.os.Bundle;
import android.util.Log;

import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.longtail360.autochessrpg.R;

public class HelloActivity extends Activity {  //testing drag and drop, not working, can delete
    ImageView img;
    String msg;
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
    }
}
