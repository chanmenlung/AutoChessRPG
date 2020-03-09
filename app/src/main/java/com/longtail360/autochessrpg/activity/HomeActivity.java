package com.longtail360.autochessrpg.activity;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jaydenxiao.guider.HighLightGuideView;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdventureAsyncTask;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.fragment.AdvFragment;
import com.longtail360.autochessrpg.fragment.HelloFragment;
import com.longtail360.autochessrpg.fragment.LogFragment;
import com.longtail360.autochessrpg.fragment.MonsterFragment;
import com.longtail360.autochessrpg.fragment.MyItemFragment;
import com.longtail360.autochessrpg.fragment.TacticsFragment;
import com.longtail360.autochessrpg.listener.DragDropOnDragListener;
import com.longtail360.autochessrpg.listener.DragDropOnTouchListener;
import com.longtail360.autochessrpg.prefab.CardIcon;
import com.longtail360.autochessrpg.prefab.HomeCardDesc;
import com.longtail360.autochessrpg.prefab.PassiveSkillDescLayout;
import com.longtail360.autochessrpg.prefab.PassiveSkillIcon;
import com.longtail360.autochessrpg.prefab.ViewTag;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeActivity extends BaseActivity implements HomeCardDesc.CallBack, CardIcon.CallBack, AdvFragment.AdvFragmentCallback{
    private static String tag = "HomeActivity";
    private TextView levelValue;
    private TextView expValue;
    private TextView hpValue;
    private TextView coinValue;
    private TextView startAdv;
    private TextView stageNo;
    private View helpBt;
    private View allCardBt;
    private View myItemBt;
    private View iconUnlock;
    private View iconLock;
    private View cardForBuyingLayout;
    private View teamLayout;
    private ViewGroup passiveSkillIconLayout;
//    private View logBt;
    private View tacticsBt;
    private View refreshBuyingCard;
    private View startAdvBt;
    private TextView currentPlaceValue;
    private TextView buyCardPosDesc;
    private LogFragment logFragment;
    private Fragment myItemFragment;
    private Fragment monsterFragment;
    private AdvFragment advFragment;
    private HelloFragment helloFragment;
    private View placeInfo;
    private View upgradeBt;
    private View startingPopupBox;
    private View settingBt;
    private PassiveSkillDescLayout passiveSkillDescLayout;
    public ProgressBar progressBar;
    private TacticsFragment tacticsFragment;
    private List<FrameLayout> handCardLayout = new ArrayList<>();
    private List<FrameLayout> buyCardLayout = new ArrayList<>();
    private List<TextView> buyCardPriceViews = new ArrayList<>();
    private Dungeon currentDungeon;
    private List<CardIcon> cardInHands = new ArrayList<>();
    private  HomeCardDesc homeCardDesc;
    private List<FrameLayout> teamCardIconContainers = new ArrayList<>();
    private List<CardIcon> cardInBattles = new ArrayList<>();
    private List<MyCard> uniqueKeyCards = new ArrayList<>(); //do not contain duplicate key card, for checking passive skill
    public CardIcon dragCardIcon; //call by dragDropOnDragListener.java
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Logger.log(tag, "start HomeActivity");
        List<MyCard> teamCards = MyCard.listByAdvIdAndType(this,GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
        for(MyCard card : teamCards){
            Logger.log(tag, "card-hpaaaa:"+card.battleHp);
        }
        thisLayout = findViewById(R.id.thisLayout);
        levelValue = findViewById(R.id.levelValue);
        expValue = findViewById(R.id.expValue);
        hpValue = findViewById(R.id.hpValue);
        coinValue = findViewById(R.id.coinValue);
        stageNo = findViewById(R.id.stageNo);
        helpBt = findViewById(R.id.helpBt);
        refreshBuyingCard = findViewById(R.id.refreshBuyingCard);
        buyCardPosDesc = findViewById(R.id.buyCardPosDesc);
        allCardBt = findViewById(R.id.allCardBt);
        iconUnlock = findViewById(R.id.iconUnlock);
        iconLock = findViewById(R.id.iconLock);
        currentPlaceValue = findViewById(R.id.currentPlaceValue);
        placeInfo = findViewById(R.id.placeInfo);
        progressBar = findViewById(R.id.progressBar);
        startingPopupBox = findViewById(R.id.startingPopupBox);
        myItemBt = findViewById(R.id.myItemBt);
//        logBt = findViewById(R.id.logBt);
        tacticsBt = findViewById(R.id.tacticsBt);
        upgradeBt = findViewById(R.id.upgradeBt);
        startAdvBt = findViewById(R.id.startAdvBt);
        settingBt = findViewById(R.id.settingBt);
        startAdv = findViewById(R.id.startAdv);
        teamLayout = findViewById(R.id.teamLayout);
        passiveSkillIconLayout = findViewById(R.id.passiveSkillIconLayout);
        cardForBuyingLayout = findViewById(R.id.cardForBuyingLayout);
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
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem0));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem1));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem2));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem3));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem4));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem5));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem6));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem7));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem8));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem9));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem10));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem11));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem12));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem13));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem14));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem15));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem16));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem17));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem18));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem19));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem20));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem21));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem22));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem23));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem24));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem25));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem26));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem27));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem28));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem29));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem30));
        teamCardIconContainers.add((FrameLayout) findViewById(R.id.teamMem31));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentDungeon = GameContext.gameContext.dungeonDAO.get(GameContext.gameContext.adventure.currentDungeonId);
//        cardInBattles =  MyCard.listByAdvIdAndType(adventure.id, MyCard.TYPE_IN_TEAM);
        GameContext.gameContext.adventure.currentRootLog = GameContext.gameContext.rootLogDAO.get(GameContext.gameContext.adventure.currentRootLogId);
        initPopupBox();
        updateTopBarValue();
        initHelp();
        initLock();
        initAllCard();
        initCurrentPlace();
        initBuyingCard();
        initCardDesc();
        initMyItem();
        initTactics();
        initUpgradeBt();
        initTeamCardIcon();
        initHandCardIcon();
        initStartAdv();
        updateBuyCardDesc();
        updateAdvBtText();
        initTutorial();
        initPassiveSkillLayout();

    }

//    private void printBattleHp(List<MyCard> cards) {
//        Logger.log(tag, "==== print hp start ========");
//        for(MyCard card : cards){
//            Logger.log(tag, "hp:"+card.battleHp);
//        }
//        Logger.log(tag, "==== print hp end ========");
//    }

    private void initPassiveSkillLayout() {
//        printBattleHp();
        if(advFragment == null){
            advFragment = new AdvFragment();
            advFragment.callback = this;
        }

        List<MyCard> team = MyCard.listByAdvIdAndType(this, GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
        for(MyCard card: team){
            card.setValueOnUpdatePassiveSkill();
            if(!existInUniqueKeyTeam(card)){
                uniqueKeyCards.add(card);
            }
        }
        passiveSkillDescLayout = new PassiveSkillDescLayout(this,null);
        thisLayout.addView(passiveSkillDescLayout);
        passiveSkillDescLayout.setVisibility(View.GONE);
        advFragment.advContext.team.clear();
        for(CardIcon icon : cardInBattles){
            advFragment.advContext.team.add(icon.myCard);
        }
        for(CardIcon card : cardInHands){
            card.myCard.setValueOnLoadHomeActivity();
        }
        for(CardIcon card : cardInBattles){
            card.myCard.setValueOnLoadHomeActivity();
        }
        advFragment.advContext.resetEventPossibility();
        for(BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
            skill.reset();
            skill.doCheckingAndSetActiveNumber(uniqueKeyCards);
        }
        Collections.sort(GameContext.gameContext.passiveSkillList);
//        printBattleHp();
        passiveSkillIconLayout.removeAllViews();
        for(final BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
            if(skill.activeNumber > 0) {
                PassiveSkillIcon icon = new PassiveSkillIcon(this, skill);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passiveSkillDescLayout.reload(skill);
                        passiveSkillDescLayout.setVisibility(View.VISIBLE);
                    }
                });
                passiveSkillIconLayout.addView(icon);
            }

            skill.active(advFragment.advContext);
        }
        Logger.log(tag, "end");
    }

    private boolean existInUniqueKeyTeam(MyCard myCard) {
        for(MyCard card : uniqueKeyCards){
            if(card.card.code.equals(myCard.card.code)){
                return true;
            }
        }
        return false;
    }

    private void updatePassiveSkillIcon() {
        advFragment.advContext.team.clear();
        for(CardIcon icon : cardInBattles){
            advFragment.advContext.team.add(icon.myCard);
        }
        if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
            advFragment.advContext.resetEventPossibility();
            for(CardIcon card : cardInHands){
                card.myCard.setValueOnUpdatePassiveSkill();
            }
            for(CardIcon card : cardInBattles){
                card.myCard.setValueOnUpdatePassiveSkill();
            }
            for(BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
                skill.reset();
                skill.doCheckingAndSetActiveNumber(uniqueKeyCards);
            }
            Collections.sort(GameContext.gameContext.passiveSkillList);

            passiveSkillIconLayout.removeAllViews();
            for(final BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
                if(skill.activeNumber > 0) {
                    PassiveSkillIcon icon = new PassiveSkillIcon(this, skill);
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            passiveSkillDescLayout.reload(skill);
                            passiveSkillDescLayout.setVisibility(View.VISIBLE);
                        }
                    });
                    passiveSkillIconLayout.addView(icon);
                }

                skill.active(advFragment.advContext);
            }
        }
    }
    private void initTutorial(){
        if(!GameContext.gameContext.getPlayer(this).isOldPlayer){
            helpBt.callOnClick();
            GameContext.gameContext.getPlayer(this).isOldPlayer = true;
            GameContext.gameContext.savePlayerData(this);
        }
    }
    private void updateAdvBtText() {
        if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
            startAdv.setText(getString(R.string.ui_home_continueAdv));
        }else {
            startAdv.setText(getString(R.string.ui_home_startAdv));
        }
    }
    private void updateBuyCardDesc() {
        String str = getString(R.string.ui_home_buyingCardPos);
        switch(GameContext.gameContext.adventure.level){
            case 1:
                str = str.replace("{basic}", "100").replace("{common}", "0")
                        .replace("{rare}", "0").replace("{epic}", "0")
                        .replace("{legendary}", "0");
                break;
            case 2:
                str = str.replace("{basic}", "70").replace("{common}", "30")
                        .replace("{rare}", "0").replace("{epic}", "0")
                        .replace("{legendary}", "0");
                break;
            case 3:
                str = str.replace("{basic}", "60").replace("{common}", "35")
                        .replace("{rare}", "5").replace("{epic}", "0")
                        .replace("{legendary}", "0");
                break;
            case 4:
                str = str.replace("{basic}", "50").replace("{common}", "35")
                        .replace("{rare}", "15").replace("{epic}", "0")
                        .replace("{legendary}", "0");
                break;
            case 5:
                str = str.replace("{basic}", "40").replace("{common}", "35")
                        .replace("{rare}", "23").replace("{epic}", "2")
                        .replace("{legendary}", "0");
                break;
            case 6:
                str = str.replace("{basic}", "33").replace("{common}", "30")
                        .replace("{rare}", "30").replace("{epic}", "7")
                        .replace("{legendary}", "0");
                break;
            case 7:
                str = str.replace("{basic}", "30").replace("{common}", "30")
                        .replace("{rare}", "30").replace("{epic}", "10")
                        .replace("{legendary}", "0");
                break;
            case 8:
                str = str.replace("{basic}", "24").replace("{common}", "30")
                        .replace("{rare}", "30").replace("{epic}", "15")
                        .replace("{legendary}", "1");
                break;
            case 9:
                str = str.replace("{basic}", "22").replace("{common}", "30")
                        .replace("{rare}", "25").replace("{epic}", "20")
                        .replace("{legendary}", "3");
                break;
            case 10:
                str = str.replace("{basic}", "19").replace("{common}", "25")
                        .replace("{rare}", "25").replace("{epic}", "25")
                        .replace("{legendary}", "6");
                break;

        }
        buyCardPosDesc.setText(str);
    }
    private void initStartAdv() {
        settingBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showMonsterFragment();
                startOtherActivity(MonsterValueActivity.class);
            }
        });
        startAdvBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardInBattles.size() == 0) {
                    popupBox.reset(1);
                    popupBox.title.setText(getBaseContext().getString(R.string.ui_home_noTeamCard));
                    popupBox.content.setText(getBaseContext().getString(R.string.ui_home_buyCardAndPutToTeam));
                    popupBox.centerConfirmHideBox();
                    popupBox.show();
                    return;
                }
//                if(GameContext.gameContext.adventure.level < cardInBattles.size()){
//                    Logger.toast( getString(R.string.ui_home_overPopulation),HomeActivity.this);
//                    return;
//                }
                if(GameContext.gameContext.adventure.currentRootLog != null){
                    Logger.log(tag, "rootLog.advStatus:"+GameContext.gameContext.adventure.currentRootLog.advStatus);
                }

                if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
                    popupBox.title.setText(getString(R.string.ui_home_isStartNewAdv));
                    popupBox.content.setText("");
                    popupBox.reset(2);
                    popupBox.rightConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            List<MyItem> onBodyItems = MyItem.listByOnBody(GameContext.gameContext.adventure.id);
                            if(onBodyItems != null && onBodyItems.size() > Setting.MAX_ITEM_ON_BODY){
                                popupBox.reset(1);
                                popupBox.title.setText(getBaseContext().getString(R.string.ui_home_overMaxItemNum));
                                popupBox.content.setText(getBaseContext().getString(R.string.ui_home_overMaxItemNumContent));
                                popupBox.centerConfirmHideBox();
                                popupBox.show();
                                return;
                            }

                            popupBox.hide();
                            updateAdvBtText();
                            showAdvFragment();
                        }
                    });
                    popupBox.show();
                }else {
                    showAdvFragment();
                }
            }
        });
    }


    private void initHandCardIcon() {
        for(int i=0; i<handCardLayout.size(); i++) {
            ViewTag viewTag = new ViewTag();
            viewTag.typeInt = MyCard.TYPE_IN_HAND;
            viewTag.valueInt = i;
            handCardLayout.get(i).setTag(viewTag);
        }
        List<MyCard> handCards = MyCard.listByAdvIdAndType(this,GameContext.gameContext.adventure.id, MyCard.TYPE_IN_HAND);
        for(int i=0; i<handCardLayout.size(); i++){
            FrameLayout handIcon = handCardLayout.get(i);
            MyCard cardInBattle = findCardInBattleByLocation(handCards,i);
            if(cardInBattle != null){
                final CardIcon cardIcon = new CardIcon(HomeActivity.this, cardInBattle, HomeActivity.this);
                cardIcon.setOnTouchListener(new DragDropOnTouchListener() {
                    @Override
                    public void afterOnTouch() {
                        HomeActivity.this.dragCardIcon = cardIcon;
                    }
                });
                cardIcon.setLongClick(HomeActivity.this);
                handIcon.addView(cardIcon);
                cardInHands.add(cardIcon);
            }
            DragDropOnDragListener ddl =new DragDropOnDragListener(this){

                @Override
                public CardIcon getCardIcon() {
                    return dragCardIcon;
                }
                @Override
                public void onSuccessDrop(ViewGroup oldContainer, ViewGroup newContainer, CardIcon cardIcon) {
                    ViewTag newContainerTag = (ViewTag)newContainer.getTag();
                    ViewTag oldContainerTag = (ViewTag)oldContainer.getTag();
                    if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                        if(oldContainerTag.typeInt == MyCard.TYPE_IN_TEAM){
                            Logger.toast(getString(R.string.ui_home_advProgressing), HomeActivity.this);
                            return;
                        }

                    }
                    oldContainer.removeView(cardIcon);
                    newContainer.addView(cardIcon);
                    cardIcon.setVisibility(View.VISIBLE);

                    if(newContainerTag.typeInt == MyCard.TYPE_IN_TEAM){
                        cardIcon.myCard.type = MyCard.TYPE_IN_TEAM;
                        cardIcon.myCard.locationX = newContainerTag.valueInt%8;
                        cardIcon.myCard.locationY = newContainerTag.valueInt/8;
                        GameContext.gameContext.myCardDAO.update(cardIcon.myCard);
                        if(!cardInBattles.contains(cardIcon.myCard)){
                            cardInBattles.add(cardIcon);
                            if(!existInUniqueKeyTeam(cardIcon.myCard)){
                                uniqueKeyCards.add(cardIcon.myCard);
                            }
                        }
                        if(cardInHands.contains(cardIcon.myCard)){
                            cardInHands.remove(cardIcon.myCard);
                        }
                        updatePassiveSkillIcon();
                    }else if(newContainerTag.typeInt == MyCard.TYPE_IN_HAND){
                        cardIcon.myCard.type = MyCard.TYPE_IN_HAND;
                        cardIcon.myCard.location = newContainerTag.valueInt;
                        GameContext.gameContext.myCardDAO.update(cardIcon.myCard);
                        if(cardInBattles.contains(cardIcon)){
                            cardInBattles.remove(cardIcon);
                        }
                        if(!cardInHands.contains(cardIcon.myCard)){
                            cardInHands.add(cardIcon);
                        }
                        uniqueKeyCards = new ArrayList<>();
                        List<MyCard> team = MyCard.listByAdvIdAndType(HomeActivity.this, GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
                        for(MyCard card: team){
                            if(!existInUniqueKeyTeam(card)){
                                uniqueKeyCards.add(card);
                            }
                        }
                        updatePassiveSkillIcon();
                    }
                }
            };
            handIcon.setOnDragListener(ddl);
        }
        for(CardIcon icon : cardInHands){
            checkUpgrade(icon);
        }
    }
    private void initTeamCardIcon() {
        for(int i=0; i<32; i++) {
            ViewTag viewTag = new ViewTag();
            viewTag.typeInt = MyCard.TYPE_IN_TEAM;
            viewTag.valueInt = i;
            teamCardIconContainers.get(i).setTag(viewTag);
        }
        List<MyCard> teamCards = MyCard.listByAdvIdAndType(this,GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
        for(MyCard card : teamCards){
            Logger.log(tag, "card-hp:"+card.battleHp);
        }
        for(int i=0; i<teamCardIconContainers.size(); i++){
            FrameLayout teamIconLayout = teamCardIconContainers.get(i);
            MyCard cardInBattle = findCardInBattleByTeam(teamCards, i);
            if(cardInBattle != null){
                final CardIcon cardIcon = new CardIcon(HomeActivity.this, cardInBattle, HomeActivity.this);
                cardIcon.setOnTouchListener(new DragDropOnTouchListener() {
                    @Override
                    public void afterOnTouch() {
                        HomeActivity.this.dragCardIcon = cardIcon;
                    }
                });
                cardIcon.setLongClick(HomeActivity.this);
                teamIconLayout.addView(cardIcon);
                cardInBattles.add(cardIcon);
            }
            DragDropOnDragListener ddl =new DragDropOnDragListener(this){
                @Override
                public CardIcon getCardIcon() {
                    return dragCardIcon;
                }
                @Override
                public void onSuccessDrop(ViewGroup oldContainer, ViewGroup newContainer, CardIcon cardIcon) {
                    ViewTag oldTag = (ViewTag)oldContainer.getTag();
                    ViewTag newTag = (ViewTag)newContainer.getTag();
                    if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                        if(oldTag.typeInt == MyCard.TYPE_IN_HAND){
                            Logger.toast(getString(R.string.ui_home_advProgressing), HomeActivity.this);
                            return;
                        }
                    }
                    Logger.log(tag, "typeInt:"+newTag.typeInt);
                    if(newTag.typeInt == MyCard.TYPE_IN_TEAM){
                        oldContainer.removeView(cardIcon);
                        newContainer.addView(cardIcon);
                        cardIcon.setVisibility(View.VISIBLE);
                        cardIcon.myCard.type = MyCard.TYPE_IN_TEAM;
                        cardIcon.myCard.locationX = newTag.valueInt%8;
                        cardIcon.myCard.locationY = newTag.valueInt/8;
                        GameContext.gameContext.myCardDAO.update(cardIcon.myCard);
                        if(!cardInBattles.contains(cardIcon)){
                            cardInBattles.add(cardIcon);
                            if(!existInUniqueKeyTeam(cardIcon.myCard)){
                                uniqueKeyCards.add(cardIcon.myCard);
                            }
                        }
                        if(cardInHands.contains(cardIcon)){
                            cardInHands.remove(cardIcon);
                        }
                        updatePassiveSkillIcon();
                    }else if(newTag.typeInt == MyCard.TYPE_IN_HAND){
                        cardIcon.myCard.type = MyCard.TYPE_IN_HAND;
                        cardIcon.myCard.location = newTag.valueInt;
                        GameContext.gameContext.myCardDAO.update(cardIcon.myCard);
                        if(cardInBattles.contains(cardIcon)){
                            cardInBattles.remove(cardIcon);
                        }
                        if(!cardInHands.contains(cardIcon)){
                            cardInHands.add(cardIcon);
                        }
                        uniqueKeyCards = new ArrayList<>();
                        List<MyCard> team = MyCard.listByAdvIdAndType(HomeActivity.this,GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
                        for(MyCard card: team){
                            if(!existInUniqueKeyTeam(card)){
                                uniqueKeyCards.add(card);
                            }
                        }
                        updatePassiveSkillIcon();
                    }
                }
            };
            teamIconLayout.setOnDragListener(ddl);
        }
        for(CardIcon icon : cardInBattles){
            checkUpgrade(icon);
        }

    }

    private MyCard findCardInBattleByTeam(List<MyCard> teamCards,int location){
        for(MyCard c : teamCards){
            if(((c.locationY*8)+c.locationX) == location ){
                return c;
            }
        }
        return null;
    }

    private MyCard findCardInBattleByLocation(List<MyCard> handCards, int location){
        for(MyCard c : handCards){
            if(c.location == location){
                return c;
            }
        }
        return null;
    }


    private void initUpgradeBt() {
        upgradeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameContext.gameContext.adventure.coin < Setting.UPGRADE_COIN){
                    Logger.toast(getString(R.string.ui_home_upgrade_notEnoughCoin), HomeActivity.this);
                    return;
                }
                GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin - Setting.UPGRADE_COIN;
                GameContext.gameContext.adventure.getExp(4);
                GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
                updateTopBarValue();
                Logger.toast(getString(R.string.ui_home_upgrade_gotExp), HomeActivity.this);
            }
        });
    }
    private void initMyItem() {
        myItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyItemFragment();
            }
        });
    }

    private void initTactics() {
        tacticsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTacticFragment();
            }
        });
    }
    private void initBuyingCard(){
        List<MyCard> cardForBuyings = MyCard.listByAdvIdAndType(this,GameContext.gameContext.adventure.id, MyCard.TYPE_FOR_BUY);
        List<MyCard> cards = new ArrayList<>();
        for(MyCard cardForBuying : cardForBuyings) {
            cardForBuying.setValueForBuyingCard();
            cards.add(cardForBuying);
        }
        reloadBuyingCard(cards);
        refreshBuyingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameContext.gameContext.adventure.coin < 20) {
                    Logger.toast(getString(R.string.ui_home_refresh_notEnoughCoin), HomeActivity.this);
                    return;
                }
                for(ViewGroup v : buyCardLayout){
                    v.removeAllViews();
                }
                GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin - 20;
                updateTopBarValue();
                updateBuyingCardByLevel();

            }
        });
    }

    private void updateBuyingCardByLevel(){
        Logger.log(tag, "adventure.level:"+GameContext.gameContext.adventure.level);
        switch(GameContext.gameContext.adventure.level){
            case 1:
                reloadBuyingCard(generateCardListByForBuying(100,0,0,0,0));
                break;
            case 2:
                reloadBuyingCard(generateCardListByForBuying(70,30,0,0,0));
                break;
            case 3:
                reloadBuyingCard(generateCardListByForBuying(60,35,5,0,0));
                break;
            case 4:
                reloadBuyingCard(generateCardListByForBuying(50,35,15,0,0));
                break;
            case 5:
                reloadBuyingCard(generateCardListByForBuying(40,35,23,2,0));
                break;
            case 6:
                reloadBuyingCard(generateCardListByForBuying(33,30,30,7,0));
                break;
            case 7:
                reloadBuyingCard(generateCardListByForBuying(30,30,30,10,0));
                break;
            case 8:
                reloadBuyingCard(generateCardListByForBuying(24,30,30,15,1));
                break;
            case 9:
                reloadBuyingCard(generateCardListByForBuying(22,30,25,20,3));
                break;
            case 10:
                reloadBuyingCard(generateCardListByForBuying(19,25,25,25,6));
        }
    }
    private List<MyCard> generateCardListByForBuying(int basicPos, int normalPos, int rarePos, int epicPos, int legendaryPos){

        List<MyCard> result = new ArrayList<>();
        Random random = new Random();
        int dbNumberOfBasic = GameContext.gameContext.cardDAO.countByRare(1);
        Logger.log(tag, "dbNumberOfBasic:"+dbNumberOfBasic);
        int dbNumberOfNormal = GameContext.gameContext.cardDAO.countByRare(2);
        int dbNumberOfRare = GameContext.gameContext.cardDAO.countByRare(3);
        int dbNumberOfEpic = GameContext.gameContext.cardDAO.countByRare(4);
        int dbNumberOfLegndary = GameContext.gameContext.cardDAO.countByRare(5);
        List<Card> basics = Card.getAllByRare(this,1);
        List<Card> normals = Card.getAllByRare(this,2);
        List<Card> rares = Card.getAllByRare(this,3);
        List<Card> epics = Card.getAllByRare(this,4);
        List<Card> legendarys = Card.getAllByRare(this,5);
        Logger.log(tag, "basics-size:"+basics.size());
        for(int i=0; i<6; i++){
            int whichRare = random.nextInt(100);
            Logger.log(tag, "whichRare:"+whichRare);

            MyCard cardForBuying = new MyCard();
            if(whichRare <= basicPos){
                int whichCard = random.nextInt(dbNumberOfBasic);
                cardForBuying.card = basics.get(whichCard);
            }else if(whichRare < (basicPos+normalPos)) {
                int whichCard = random.nextInt(dbNumberOfNormal);
                Logger.log(tag, "gen normals");
                cardForBuying.card = normals.get(whichCard);
                Logger.log(tag, "cardForBuying.card.rare:"+cardForBuying.card.rarity);
            }else if(whichRare < (basicPos+normalPos+rarePos)) {
                int whichCard = random.nextInt(dbNumberOfRare);
                cardForBuying.card = rares.get(whichCard);
            }else if(whichRare < (basicPos+normalPos+rarePos+epicPos)) {
                int whichCard = random.nextInt(dbNumberOfEpic);
                cardForBuying.card = epics.get(whichCard);
            }else if(whichRare < (basicPos+normalPos+rarePos+epicPos+legendaryPos)) {
                int whichCard = random.nextInt(dbNumberOfLegndary);
                cardForBuying.card = legendarys.get(whichCard);
            }
            cardForBuying.adventureId = GameContext.gameContext.adventure.id;
            cardForBuying.cardId = cardForBuying.card.id;
            cardForBuying.type = MyCard.TYPE_FOR_BUY;
            cardForBuying.setValueForBuyingCard();

            result.add(cardForBuying);
        }

        return result;
    }

    private void reloadBuyingCard(List<MyCard> cards) {
        GameContext.gameContext.myCardDAO.deleteByAdvIdAndType(GameContext.gameContext.adventure.id, MyCard.TYPE_FOR_BUY);
        for(MyCard card : cards){
            GameContext.gameContext.myCardDAO.insert(card);
        }

        for(int i=0; i<cards.size(); i++){
            final CardIcon icon = new CardIcon(this, cards.get(i), this);
            final int finalI = i;
            buyCardPriceViews.get(i).setText("$"+icon.myCard.card.price);
            icon.setLongClick(HomeActivity.this);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cardInHands.size() > handCardLayout.size()){
                        Logger.toast( getString(R.string.ui_home_handCardIsFull),HomeActivity.this);
                        return;
                    }
                    if(GameContext.gameContext.adventure.coin < icon.myCard.card.price){
                        Logger.toast( getString(R.string.ui_home_coinNotEnougth),HomeActivity.this);
                        return;
                    }
                    GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin - icon.myCard.card.price;
                    icon.myCard.type = MyCard.TYPE_IN_HAND;
                    Logger.log(tag, "cardInHands-size:"+cardInHands.size());
                    final CardIcon cardIcon = new CardIcon(HomeActivity.this, icon.myCard, HomeActivity.this);

                    cardIcon.updateStar();
                    cardIcon.setOnTouchListener(new DragDropOnTouchListener() {
                        @Override
                        public void afterOnTouch() {
                            HomeActivity.this.dragCardIcon = cardIcon;
                        }
                    });
                    cardIcon.setLongClick(HomeActivity.this);
                    for(int i=0; i<handCardLayout.size(); i++){
                        if(handCardLayout.get(i).getChildCount() == 0){
                            handCardLayout.get(i).addView(cardIcon);
                            icon.myCard.location = i;
                            GameContext.gameContext.myCardDAO.update(icon.myCard);
                            cardInHands.add(cardIcon);
                            break;
                        }
                    }
                    buyCardLayout.get(finalI).removeAllViews();
                    buyCardPriceViews.get(finalI).setText("");
                    updateTopBarValue();
                    checkUpgrade(cardIcon);
                }
            });
            buyCardLayout.get(i).addView(icon);
        }
    }

    private void checkUpgrade(CardIcon focusIcon) {
        int count = 0;
        List<CardIcon> upgradeIcons = new ArrayList<>();

        for(CardIcon icon : cardInHands){
            if(icon.myCard.card.code.equals(focusIcon.myCard.card.code) && icon.myCard.level == focusIcon.myCard.level){
                count++;
                upgradeIcons.add(icon);
            }
        }
        for(CardIcon icon : cardInBattles){
            if(icon.myCard.card.code.equals(focusIcon.myCard.card.code) && icon.myCard.level == focusIcon.myCard.level){
                count++;
                upgradeIcons.add(icon);
            }
        }
        if(count == 3){
            Logger.log(tag, "upgrade-size:"+upgradeIcons.size());
            for(CardIcon icon : upgradeIcons){
                icon.upgradeBt.setVisibility(View.VISIBLE);
            }
        }
    }
    private void initCardDesc() {
        homeCardDesc = new HomeCardDesc(this, this);
        homeCardDesc.setVisibility(View.GONE);
        thisLayout.addView(homeCardDesc);
    }
    private void initCurrentPlace() {
        final Dungeon dungeon = GameContext.gameContext.dungeonDAO.getByIndex(GameContext.gameContext.adventure.currentDungeonId);
        currentPlaceValue.setText(dungeon.name+"");
        stageNo.setText(getString(R.string.ui_home_stage).replace("{stageNo}",GameContext.gameContext.adventure.currentDungeonId+""));
        placeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] monsterKeys = dungeon.monsterIds.split(",");
                StringBuilder monsterNames = new StringBuilder();
                int hp = 0;
                int attack = 0;
                int defense = 0;
                for(String monsterKey : monsterKeys){
                    Monster monster = GameContext.gameContext.monsterDAO.getByCode(monsterKey);
                    monsterNames.append(monster.name);
                    monsterNames.append(",");
                    hp = monster.getHp();
                    attack = monster.attack;
                    defense = monster.defense;
                }
                if(prefs.getInt(MonsterValueActivity.HP,0) > 0) {
                    hp = prefs.getInt(MonsterValueActivity.HP,0);
                }
                if(prefs.getInt(MonsterValueActivity.ATTACK,0) > 0) {
                    attack = prefs.getInt(MonsterValueActivity.ATTACK,0);
                }
                if(prefs.getInt(MonsterValueActivity.DEFENSE,0) > 0) {
                    defense = prefs.getInt(MonsterValueActivity.DEFENSE,0);
                }
                String name = monsterNames.toString();
                popupBox.title.setText(dungeon.name);
                popupBox.content.setText(getString(R.string.ui_home_stageInfo).replace("{floor}", dungeon.numFloor+"")
                        .replace("{monster}", name.substring(0, name.length()-1))
                        .replace("{hp}", hp+"")
                        .replace("{attack}", attack+"")
                        .replace("{defense}", defense+"")
                        );
                popupBox.centerConfirmHideBox();
                popupBox.show();
            }
        });
//        logBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLogFragment();
//            }
//        });

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
                HighLightGuideView.builder(HomeActivity.this).addHighLightGuidView(cardForBuyingLayout,R.drawable.step1)
                        .addHighLightGuidView(cardForBuyingLayout,R.drawable.step1)
                        .setOnDismissListener(new HighLightGuideView.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                HighLightGuideView.builder(HomeActivity.this)
                                        .addHighLightGuidView(teamLayout,R.drawable.step2)
                                        .setOnDismissListener(new HighLightGuideView.OnDismissListener() {
                                            @Override
                                            public void onDismiss() {
                                                HighLightGuideView.builder(HomeActivity.this)
                                                        .addHighLightGuidView(startAdvBt,R.drawable.step3).show();
                                            }
                                        }).show();
                            }
                        }).show();
            }
        });
    }
    private void initLock() {
        iconLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconLock.setVisibility(View.GONE);
                iconUnlock.setVisibility(View.VISIBLE);
                GameContext.gameContext.adventure.lockBuyingCard = 0;
                GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            }
        });
        iconUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconUnlock.setVisibility(View.GONE);
                iconLock.setVisibility(View.VISIBLE);
                GameContext.gameContext.adventure.lockBuyingCard = 1;
                GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            }
        });
    }

    private void updateTopBarValue() {
        int totalExp = GameContext.gameContext.adventure.calTotalExpByLevel(GameContext.gameContext.adventure.level);
        levelValue.setText(GameContext.gameContext.adventure.level+"");
        expValue.setText(GameContext.gameContext.adventure.exp+"/"+totalExp);
        hpValue.setText(GameContext.gameContext.adventure.hp+"/100");
        coinValue.setText(GameContext.gameContext.adventure.coin+"");
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

    public void showLogFragment() {
        // Create new fragment and transaction
        startingPopupBox.setVisibility(View.GONE);
        if(logFragment == null){
            logFragment = new LogFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, logFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void showAdvFragment() {
        startingPopupBox.setVisibility(View.GONE);
//        if(advFragment == null){
//            advFragment = new AdvFragment();
//            advFragment.callback = this;
//        }
//        advFragment.advContext.team.clear();
//        for(CardIcon icon : cardInBattles){
//            advFragment.advContext.team.add(icon.myCard);
//        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, advFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void showMonsterFragment() {
        // Create new fragment and transaction
        if(monsterFragment == null){
            monsterFragment = new MonsterFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, monsterFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void showTacticFragment() {
        Logger.log(tag, "showTacticFragment");
        if(tacticsFragment == null){
            tacticsFragment = new TacticsFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, tacticsFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void showHelloFragment() {
        if(helloFragment == null){
            helloFragment = new HelloFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, helloFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }



    @Override
    public void doActionAfterSellCard(CardIcon icon) {
        if(GameContext.gameContext.adventure.currentRootLog == null || GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
            GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin + icon.myCard.getSellingPrice();
            GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            coinValue.setText(GameContext.gameContext.adventure.coin+"");
            ((ViewGroup)icon.getParent()).removeAllViews();
            cardInHands.remove(icon);
            cardInBattles.remove(icon);
            GameContext.gameContext.myCardDAO.delete(icon.myCard.id);
        }else {
            Logger.toast(getString(R.string.ui_home_advProgressing), this);
        }
    }

    @Override
    public void onBackPressed() {
        Logger.log(tag, "setBackPress");
        updateAdvBtText();
        if(advFragment != null && advFragment.isShowSubLog){
            advFragment.hideDetailForBack();
            advFragment.isShowSubLog = false;
        }else {
            super.onBackPressed();
        }
    }


    @Override
    public void doActionAfterLongClick(CardIcon cardIcon) {
        homeCardDesc.reloadCard(HomeActivity.this, cardIcon);
        homeCardDesc.setVisibility(View.VISIBLE);
    }

    @Override
    public void doActionAfterClickUpgrade(CardIcon cardIcon) {
        if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
            Logger.toast(getString(R.string.ui_home_advProgressing), HomeActivity.this);
        }else {
            List<CardIcon> upgradeIcons = new ArrayList<>();

            for(CardIcon icon : cardInHands){
                if(icon.myCard.card.code.equals(cardIcon.myCard.card.code) && icon.myCard.level == cardIcon.myCard.level){
                    upgradeIcons.add(icon);
                }
            }
            for(CardIcon icon : cardInBattles){
                if(icon.myCard.card.code.equals(cardIcon.myCard.card.code) && icon.myCard.level == cardIcon.myCard.level){
                    upgradeIcons.add(icon);
                }
            }
            cardIcon.myCard.level++;
            cardIcon.myCard.card.skill.level = cardIcon.myCard.level;
            cardIcon.myCard.totalHp = cardIcon.myCard.card.calHpByLevel(cardIcon.myCard.level);
            cardIcon.myCard.battleHp = cardIcon.myCard.totalHp;
            cardIcon.myCard.battleAttack = cardIcon.myCard.card.calAttackByLevel(cardIcon.myCard.level);
            GameContext.gameContext.myCardDAO.update(cardIcon.myCard);
            for(CardIcon icon : upgradeIcons){
                if(icon != cardIcon){
                    ((ViewGroup) icon.getParent()).removeView(icon);
                    GameContext.gameContext.myCardDAO.delete(icon.myCard.id);
                    cardInHands.remove(icon);
                    cardInBattles.remove(icon);
                }else {
                    icon.upgradeBt.setVisibility(View.GONE);
                    icon.updateStar();
                }
            }
            //checking again for level 2 to level 3
            checkUpgrade(cardIcon);
            Logger.toast(getString(R.string.ui_home_cardUpgradeSuccess), this);

        }
    }

    @Override
    public void onAdvFinish(boolean success) {
        if(GameContext.gameContext.adventure.lockBuyingCard == 0) {
            for(ViewGroup v : buyCardLayout){
                v.removeAllViews();
            }
            updateBuyingCardByLevel();
        }else {
            GameContext.gameContext.adventure.lockBuyingCard = 0;
            GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            iconLock.setVisibility(View.GONE);
            iconUnlock.setVisibility(View.VISIBLE);
        }
        GameContext.gameContext.adventure.currentRootLog = GameContext.gameContext.rootLogDAO.get(GameContext.gameContext.adventure.currentRootLogId);
        coinValue.setText(GameContext.gameContext.adventure.coin+"");
        updateTopBarValue();
        updateAdvBtText();
        initCurrentPlace();
        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);

//        for(CardIcon icon : cardInBattles){
//            GameContext.gameContext.myCardDAO.update(icon.myCard);
//        }
        updateBuyCardDesc();
    }

    @Override
    public void onPressBack() {
        onBackPressed();
    }

    @Override
    public void onHpIs0() {

        for(ViewGroup v : buyCardLayout){
            v.removeAllViews();
        }
        Dungeon firestDungeon =  GameContext.gameContext.dungeonDAO.getAll().get(0);
        GameContext.gameContext.adventure = new Adventure(10, firestDungeon.index, 0, 1,0,100);
        GameContext.gameContext.advDAO.insert(GameContext.gameContext.adventure);
        cardInBattles = new ArrayList<>();
        cardInHands = new ArrayList<>();
        updateTopBarValue();
        updateBuyingCardByLevel();
        for(ViewGroup v : handCardLayout){
            v.removeAllViews();
        }
        for(ViewGroup v : teamCardIconContainers){
            v.removeAllViews();
        }
    }
}

