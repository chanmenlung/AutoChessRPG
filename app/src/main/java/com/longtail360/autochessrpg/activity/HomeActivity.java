package com.longtail360.autochessrpg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.games.Game;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CardForBuying;
import com.longtail360.autochessrpg.entity.CardInHand;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.fragment.AllCardFragment;
import com.longtail360.autochessrpg.fragment.HelpFragment;
import com.longtail360.autochessrpg.fragment.LogFragment;
import com.longtail360.autochessrpg.prefab.CardIcon;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private static String tag = "HomeActivity";
    private TextView levelValue;
    private TextView expValue;
    private TextView populationValue;
    private TextView hpValue;
    private TextView coinValue;
    private View helpBt;
    private View allCardBt;
    private View iconUnlock;
    private View iconLock;
    private View logBt;
    private TextView currentPlaceValue;
    private Adventure adventure;
    private  Fragment helpFragment;
    private Fragment allCardFragment;
    private Fragment logFragment;
    private View placeInfo;
    public ProgressBar progressBar;

    private List<FrameLayout> handCardLayout = new ArrayList<>();
    private List<FrameLayout> buyCardLayout = new ArrayList<>();
    private Dungeon currentDungeon;
    private List<CardForBuying> cardForBuying;
    private List<CardInHand> cardInHands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Logger.log(tag, "start HomeActivity");
        thisLayout = findViewById(R.id.thisLayout);
        levelValue = findViewById(R.id.levelValue);
        expValue = findViewById(R.id.expValue);
        populationValue = findViewById(R.id.populationValue);
        hpValue = findViewById(R.id.hpValue);
        coinValue = findViewById(R.id.coinValue);
        helpBt = findViewById(R.id.helpBt);
        allCardBt = findViewById(R.id.allCardBt);
        iconUnlock = findViewById(R.id.iconUnlock);
        iconLock = findViewById(R.id.iconLock);
        currentPlaceValue = findViewById(R.id.currentPlaceValue);
        placeInfo = findViewById(R.id.placeInfo);
        progressBar = findViewById(R.id.progressBar);
        logBt = findViewById(R.id.logBt);
        adventure = GameContext.gameContext.adventure;
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard1));
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard2));
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard3));
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard4));
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard5));
        buyCardLayout.add((FrameLayout)findViewById(R.id.buyCard6));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard1));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard2));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard3));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard4));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard5));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard6));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard7));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard8));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard9));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard10));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard11));
        handCardLayout.add((FrameLayout)findViewById(R.id.mycard12));
        currentDungeon = GameContext.gameContext.dungeonDAO.get(adventure.currentDungeonId);
        cardForBuying = CardForBuying.listAll();
        cardInHands = CardInHand.listAll();
        initPopupBox();
        updateTopBarValue();
        initHelp();
        initLock();
        initAllCard();
        initCurrentPlace();
        initBuyingCard();
    }

    private void initBuyingCard(){

        for(int i=0; i<buyCardLayout.size(); i++){
            final CardIcon icon = new CardIcon(this, cardForBuying.get(i).card);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(handCardLayout.size() >= cardInHands.size()){
                        Logger.toast( getString(R.string.ui_home_handCardIsFull),HomeActivity.this);
                        return;
                    }
                    CardInHand cardInHand = new CardInHand();
                    cardInHand.cardId = icon.card.id;
                    cardInHand.adventureId = adventure.id;
                    cardInHand.location = cardInHands.size();
                    GameContext.gameContext.cardInHandDAO.insert(cardInHand);
                    CardIcon cardIcon = new CardIcon(HomeActivity.this, icon.card);
                    handCardLayout.add(cardIcon);

                }
            });
            buyCardLayout.get(i).addView(icon);
        }

    }
    private void initCurrentPlace() {
        Dungeon dungeon = GameContext.gameContext.dungeonDAO.get(GameContext.gameContext.adventure.currentDungeonId);
        currentPlaceValue.setText(dungeon.name+"");
        placeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.title.setText(currentDungeon.name);
                popupBox.content.setText(getString(R.string.ui_home_numOfFloor)+currentDungeon.numFloor);
                popupBox.centerConfirmHideBox();
                popupBox.show();
            }
        });
        logBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogFragment();
            }
        });

    }
    private void initAllCard() {
        allCardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(AllCardActivity.class);
            }
        });
    }
    private void initHelp() {
        helpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.title.setText(getString(R.string.ui_home_help));
                popupBox.content.setText(getString(R.string.ui_home_help_content));
                popupBox.centerConfirmHideBox();
                popupBox.show();
            }
        });
    }
    private void initLock() {
        iconLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconLock.setVisibility(View.GONE);
                iconUnlock.setVisibility(View.VISIBLE);
            }
        });
        iconUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconUnlock.setVisibility(View.GONE);
                iconLock.setVisibility(View.VISIBLE);
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

    private void showLogFragment() {
        // Create new fragment and transaction
        if(logFragment == null){
            logFragment = new LogFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, logFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}

