package com.longtail360.autochessrpg.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.SnapshotsClient;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.CustomCardDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.MyCardDAO;
import com.longtail360.autochessrpg.dao.PlayerDAO;
import com.longtail360.autochessrpg.dao.SkillDAO;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.utils.GooglePlayHandler;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ExternalResActivity {
    private String tag = "MainActivity";
    private boolean resetPlayer = false;
    private TextView comName;
    private View comNameLayout;
    private View backgroundLayout;
    private ProgressBar progressBar;
    private Handler mHandler = new Handler();
    private TextView initMessage;
    //ui_mainPage_tapToStartGame
    //ui_mainPage_doingInitial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameContext.gameContext = null;
        thisLayout = findViewById(R.id.thisLayout);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        initMessage = findViewById(R.id.initMessage);
        comName = findViewById(R.id.com_name);
        comNameLayout = findViewById(R.id.comNameLayout);
        progressBar = findViewById(R.id.progressBar);
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(2000);
        comName.startAnimation(alphaAnimation);
        GameContext.gameContext = new GameContext();

        alphaAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                comNameLayout.setVisibility(View.INVISIBLE);

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {  //if no permission, ask for grant permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ExternalResActivity.READ_WRITE_EXTERNAL_FILE);
                }else {
                    doActionAfterGrantReadExternalRes();
                }
            }
        });
    }


    @Override
    public void doActionAfterGrantReadExternalRes(){
//        createGameFolder();
        initPopupBox();
        init();
    }

    private void init(){
        GameContext.gameContext.advDAO = new AdventureDAO(MainActivity.this);
        GameContext.gameContext.cardDAO = new CardDAO(MainActivity.this);
        GameContext.gameContext.skillDAO = new SkillDAO(MainActivity.this);
        GameContext.gameContext.dungeonDAO = new DungeonDAO(MainActivity.this);
        GameContext.gameContext.itemDAO = new ItemDAO(MainActivity.this);
        GameContext.gameContext.itemGotDAO = new ItemGotDAO(MainActivity.this);
        GameContext.gameContext.monsterDAO = new MonsterDAO(MainActivity.this);
        GameContext.gameContext.myCardDAO = new MyCardDAO(MainActivity.this);
        GameContext.gameContext.rootLogDAO = new RootLogDAO(MainActivity.this);
        GameContext.gameContext.processLogDAO = new ProcessLogDAO(MainActivity.this);
        GameContext.gameContext.battleRootLogDAO = new BattleRootLogDAO(MainActivity.this);
        GameContext.gameContext.battleItemLogDAO = new BattleItemLogDAO(MainActivity.this);
        GameContext.gameContext.playerDAO = new PlayerDAO(MainActivity.this);
        GameContext.gameContext.customCardDAO = new CustomCardDAO(MainActivity.this);

        if(GameDBHelper.isResetDB) {
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + PlayerDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + CustomCardDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + AdventureDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + CardDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + DungeonDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + ItemDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + ItemGotDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + MonsterDAO.TABLE_NAME);

            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + BattleRootLogDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + BattleItemLogDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + ProcessLogDAO.TABLE_NAME);
            GameDBHelper.getDatabase(MainActivity.this).execSQL("delete from " + RootLogDAO.TABLE_NAME);
        }
        int count = GameContext.gameContext.cardDAO.getCount();
        boolean dbIsNull;
        if(count < 1){
            dbIsNull = true;
        }else {
            dbIsNull = false;
        }
        if (dbIsNull) {
            new Thread(new Runnable() {
                public void run() {
                        Logger.log(tag, "set db");

                        Player.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(10);
                            }
                        });
                        Card.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(20);
                            }
                        });
                        CustomCard.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(30);
                            }
                        });
                        Dungeon.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(40);
                            }
                        });
                        Item.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(60);
                            }
                        });

                        Monster.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(80);
                            }
                        });
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(100);
                                progressBar.setVisibility(View.GONE);
                                initMessage.setText(R.string.ui_mainPage_tapToStartGame);
                                initListener();
                                readData();
                            }
                        });
                    }
            }).start();

        }else {
            progressBar.setVisibility(View.GONE);
            initMessage.setText(R.string.ui_mainPage_tapToStartGame);
            initListener();
            readData();
        }
    }


    private void readData() {
        GameContext.gameContext.player = GameContext.gameContext.playerDAO.listAll().get(0);
        GameContext.gameContext.passiveSkillList = BasePassiveSkill.listAll(MainActivity.this);

        Dungeon firestDungeon =  GameContext.gameContext.dungeonDAO.getAll().get(0);
        Logger.log(tag, firestDungeon.name+"");
        Logger.log(tag, "tacticsJson:"+GameContext.gameContext.player.tacticsJson);
        Logger.log(tag, "is_old_player:"+GameContext.gameContext.player.isOldPlayer);
        List<Adventure> advs =  GameContext.gameContext.advDAO.getAll();
        Logger.log(tag, "advs.size():"+advs.size());
        if(advs == null || advs.size() == 0) {
            GameContext.gameContext.adventure = new Adventure(10, firestDungeon.index, 0, 1,0,100);
            GameContext.gameContext.advDAO.insert(GameContext.gameContext.adventure);
            insertCardForBuyingOnNewAccount(GameContext.gameContext.adventure.id);
//            insertItemGotOnNewAccount(GameContext.gameContext.adventure.id);
        }else {
            GameContext.gameContext.adventure = advs.get(0);

            Logger.log(tag, "do concrete tactics");
            GameContext.gameContext.player.concreteConds(MainActivity.this);
            GameContext.gameContext.player.concreteAction(MainActivity.this);
        }

    }
    private void initListener() {
        View leaveGame = findViewById(R.id.leaveGame);
        leaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.reset(2);
                popupBox.title.setText(getString(R.string.ui_mainPage_isLeaveGame));
                popupBox.content.setText("");
                popupBox.rightConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        System.exit(1);
                    }
                });
                popupBox.show();
            }
        });
        View aboutBt = findViewById(R.id.aboutBt);
        aboutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(AboutActivity.class);
            }
        });
        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(HomeActivity.class);
            }
        });
    }

    private void insertItemGotOnNewAccount(long advId) {
        MyItem item;
        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_HP_UP);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new MyItem();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_CD_DOWN);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);
    }
    private void insertCardForBuyingOnNewAccount(long advId) {
        List<Card> normalCards = GameContext.gameContext.cardDAO.getAllByRare(1);
        List<Integer> numbers = new ArrayList<>();
        for(int i=0;i<6;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        MyCard cfb;
        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(0)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(1)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(2)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(3)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(4)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);

        cfb = new MyCard();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(5)).id;
        cfb.type = MyCard.TYPE_FOR_BUY;
        GameContext.gameContext.myCardDAO.insert(cfb);
    }
}
