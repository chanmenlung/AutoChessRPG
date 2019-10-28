package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.longtail360.autochessrpg.R;


public class AboutActivity extends BaseActivity{
    private ViewGroup thisLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        thisLayout = findViewById(R.id.thisLayout);

        View innerBackBt = findViewById(R.id.innerBackBt);
        innerBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
