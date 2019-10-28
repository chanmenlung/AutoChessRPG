package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;

public class HomeActivity extends BaseActivity {
    private static String tag = "HomeActivity";
    private TextView levelValue;
    private TextView expValue;
    private TextView populationValue;
    private TextView hpValue;
    private TextView coinValue;
    private TextView helpBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        thisLayout = findViewById(R.id.thisLayout);
        levelValue = findViewById(R.id.levelValue);
        expValue = findViewById(R.id.expValue);
        populationValue = findViewById(R.id.populationValue);
        hpValue = findViewById(R.id.hpValue);
        coinValue = findViewById(R.id.coinValue);

        initPopupBox();
    }
}

