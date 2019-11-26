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
import com.longtail360.autochessrpg.fragment.MyItemFragment;
import com.longtail360.autochessrpg.prefab.CardIcon;
import com.longtail360.autochessrpg.prefab.HomeCardDesc;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends BaseActivity implements HomeCardDesc.CallBack{
    private static String tag = "HomeActivity";
    private TextView levelValue;
    private TextView expValue;
    private TextView populationValue;
    private TextView hpValue;
    private TextView coinValue;
    private View helpBt;
    private View allCardBt;
    private View myItemBt;
    private View iconUnlock;
    private View iconLock;
    private View logBt;
    private View refreshBuyingCard;
    private TextView currentPlaceValue;
    private Adventure adventure;
    private Fragment logFragment;
    private Fragment myItemFragment;
    private View placeInfo;
    public ProgressBar progressBar;

    private List<FrameLayout> handCardLayout = new ArrayList<>();
    private List<FrameLayout> buyCardLayout = new ArrayList<>();
    private List<TextView> buyCardPriceViews = new ArrayList<>();
    private Dungeon currentDungeon;
    private List<CardInHand> cardInHands;
    private  HomeCardDesc homeCardDesc;
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
        refreshBuyingCard = findViewById(R.id.refreshBuyingCard);
        allCardBt = findViewById(R.id.allCardBt);
        iconUnlock = findViewById(R.id.iconUnlock);
        iconLock = findViewById(R.id.iconLock);
        currentPlaceValue = findViewById(R.id.currentPlaceValue);
        placeInfo = findViewById(R.id.placeInfo);
        progressBar = findViewById(R.id.progressBar);
        myItemBt = findViewById(R.id.myItemBt);
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
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard1Price));
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard2Price));
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard3Price));
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard4Price));
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard5Price));
        buyCardPriceViews.add((TextView)findViewById(R.id.buyCard6Price));
        currentDungeon = GameContext.gameContext.dungeonDAO.get(adventure.currentDungeonId);
        cardInHands = CardInHand.listAll();
        initPopupBox();
        updateTopBarValue();
        initHelp();
        initLock();
        initAllCard();
        initCurrentPlace();
        initBuyingCard();
        initCardDesc();
        initMyItem();
    }

    private void initMyItem() {
        myItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyItemFragment();
            }
        });
    }
    private void initBuyingCard(){
        List<CardForBuying> cardForBuyings = CardForBuying.listAll();
        List<Card> cards = new ArrayList<>();
        for(CardForBuying cardForBuying : cardForBuyings) {
            cards.add(cardForBuying.card);
        }
        reloadBuyingCard(cards);
        refreshBuyingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(adventure.level){
                    case 1:
                        reloadBuyingCard(generateCardListByRandomNumber(100,0,0,0,0));
                        break;
                    case 2:
                        reloadBuyingCard(generateCardListByRandomNumber(70,30,0,0,0));
                    case 3:
                        reloadBuyingCard(generateCardListByRandomNumber(60,35,5,0,0));
                    case 4:
                        reloadBuyingCard(generateCardListByRandomNumber(50,35,15,0,0));
                    case 5:
                        reloadBuyingCard(generateCardListByRandomNumber(40,35,23,2,0));
                    case 6:
                        reloadBuyingCard(generateCardListByRandomNumber(33,30,30,7,0));
                    case 7:
                        reloadBuyingCard(generateCardListByRandomNumber(30,30,30,10,0));
                    case 8:
                        reloadBuyingCard(generateCardListByRandomNumber(24,30,30,15,1));
                    case 9:
                        reloadBuyingCard(generateCardListByRandomNumber(22,30,25,20,3));
                    case 10:
                        reloadBuyingCard(generateCardListByRandomNumber(19,25,25,25,6));
                }
            }
        });
    }

    private List<Card> generateCardListByRandomNumber(int basicPos, int normalPos, int rarePos, int epicPos, int legendaryPos){
        List<Card> result = new ArrayList<>();
        Random random = new Random();
        int dbNumberOfBasic = GameContext.gameContext.cardDAO.countByRare(1);
        int dbNumberOfNormal = GameContext.gameContext.cardDAO.countByRare(2);
        int dbNumberOfRare = GameContext.gameContext.cardDAO.countByRare(3);
        int dbNumberOfEpic = GameContext.gameContext.cardDAO.countByRare(4);
        int dbNumberOfLegndary = GameContext.gameContext.cardDAO.countByRare(5);
        List<Card> basics = Card.getAllByRare(1);
        List<Card> normals = Card.getAllByRare(2);
        List<Card> rares = Card.getAllByRare(3);
        List<Card> epics = Card.getAllByRare(4);
        List<Card> legendarys = Card.getAllByRare(5);

        for(int i=0; i<6; i++){
            int whichRare = random.nextInt(100)+1;
            if(whichRare < basicPos){
                int whichCard = random.nextInt(dbNumberOfBasic)+1;
                result.add(basics.get(whichCard));
            }else if(whichRare < (basicPos+normalPos)) {
                int whichCard = random.nextInt(dbNumberOfNormal)+1;
                result.add(normals.get(whichCard));
            }else if(whichRare < (basicPos+normalPos+rarePos)) {
                int whichCard = random.nextInt(dbNumberOfRare)+1;
                result.add(rares.get(whichCard));
            }else if(whichRare < (basicPos+normalPos+rarePos+epicPos)) {
                int whichCard = random.nextInt(dbNumberOfEpic)+1;
                result.add(epics.get(whichCard));
            }else if(whichRare < (basicPos+normalPos+rarePos+epicPos+legendaryPos)) {
                int whichCard = random.nextInt(dbNumberOfLegndary)+1;
                result.add(legendarys.get(whichCard));
            }
        }
        return result;
    }

    private void reloadBuyingCard(List<Card> cards) {
        for(int i=0; i<buyCardLayout.size(); i++){
            final CardIcon icon = new CardIcon(this, cards.get(i));
            final int finalI = i;
            buyCardPriceViews.get(i).setText("$"+icon.card.price);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cardInHands.size() > handCardLayout.size()){
                        Logger.toast( getString(R.string.ui_home_handCardIsFull),HomeActivity.this);
                        return;
                    }
                    final CardInHand cardInHand = new CardInHand();
                    cardInHand.cardId = icon.card.id;
                    cardInHand.card = icon.card;
                    cardInHand.adventureId = adventure.id;
                    Logger.log(tag, "cardInHands-size:"+cardInHands.size());
                    Logger.log(tag, "cardInHand.location:"+cardInHand.location);
                    final CardIcon cardIcon = new CardIcon(HomeActivity.this, icon.card);
                    for(int i=0; i<handCardLayout.size(); i++){
                        if(handCardLayout.get(i).getChildCount() == 0){
                            handCardLayout.get(i).addView(cardIcon);
                            cardInHand.location = i;
                            GameContext.gameContext.cardInHandDAO.insert(cardInHand);
                            cardInHands.add(cardInHand);
                            break;
                        }
                    }
                    buyCardLayout.get(finalI).removeAllViews();
                    buyCardPriceViews.get(finalI).setText("");
                    cardIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            homeCardDesc.reloadCard(HomeActivity.this, handCardLayout.get(cardInHand.location), icon.card);
                            homeCardDesc.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
            buyCardLayout.get(i).addView(icon);
        }
    }
    private void initCardDesc() {
        homeCardDesc = new HomeCardDesc(this, this);
        homeCardDesc.setVisibility(View.GONE);
        thisLayout.addView(homeCardDesc);
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

    private void showMyItemFragment() {
        // Create new fragment and transaction
        if(myItemFragment == null){
            myItemFragment = new MyItemFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, myItemFragment);
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


    @Override
    public void doActionAfterSellCard(ViewGroup parentLayout, Card card) {
        coinValue.setText(adventure.coin+"");
        for(FrameLayout fl : handCardLayout) {
            if(fl == parentLayout){
                Logger.log(tag, "remove icon");
                fl.removeAllViews();
            }
        }
        CardInHand toDeleteHandCard = null;
        for(CardInHand handCard : cardInHands){
            if(handCard.card == card){
                toDeleteHandCard = handCard;
            }
        }
        if(toDeleteHandCard != null) {
            cardInHands.remove(toDeleteHandCard);
            GameContext.gameContext.cardInHandDAO.delete(toDeleteHandCard.id);
        }else {
            Logger.log(tag, "toDeleteHandCard is null");
        }
    }
}

