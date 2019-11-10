package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.games.Game;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.fragment.AllCardFragment;
import com.longtail360.autochessrpg.fragment.HelpFragment;

public class HomeActivity extends BaseActivity {
    private static String tag = "HomeActivity";
    private TextView levelValue;
    private TextView expValue;
    private TextView populationValue;
    private TextView hpValue;
    private TextView coinValue;
    private View helpBt;
    private View allCardBt;
    private Adventure adventure;
    private  Fragment helpFragment;
    private Fragment allCardFragment;
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
        helpBt = findViewById(R.id.helpBt);
        allCardBt = findViewById(R.id.allCardBt);
        adventure = GameContext.gameContext.adventure;
        initPopupBox();
        updateTopBarValue();
        helpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpFragment();
            }
        });
        allCardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(AllCardActivity.class);
            }
        });
    }

    private void updateTopBarValue() {
        int totalExp = 1;
        for(int i=0; i<adventure.level; i++){
            totalExp = totalExp * 2;
        }
        levelValue.setText(adventure.level+"");
        expValue.setText(adventure.exp+"/"+totalExp);
        populationValue.setText(adventure.level+"/"+adventure.level);
        hpValue.setText(adventure.hp+"/100");
        coinValue.setText(adventure.coin+"");
    }

    private void showHelpFragment() {
        // Create new fragment and transaction
        if(helpFragment == null){
            helpFragment = new HelpFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, helpFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void showAllCardFragment() {
        // Create new fragment and transaction
        if(allCardFragment == null){
            allCardFragment = new AllCardFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, allCardFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}

