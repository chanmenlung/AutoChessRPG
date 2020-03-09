package com.longtail360.autochessrpg.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.MyCardDAO;
import com.longtail360.autochessrpg.dao.SkillDAO;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        doResetPlayer();
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

    private void doResetPlayer() {
        if(resetPlayer){
            deleteFile(Setting.PLAYER_FILE_NAME);
        }
    }


    @Override
    public void doActionAfterGrantReadExternalRes(){
        createGameFolder();
        initPopupBox();
        init();
    }

    private void createGameFolder() {
        if(isSdcardMounted()) {
            File comFolder = new File(Environment.getExternalStorageDirectory()+Setting.COM_FOLDER);
            try{
                if(!comFolder.exists()){
                    if(comFolder.mkdir()) {
                        Logger.log(tag, "comFolder created");
                    } else {
                        Logger.log(tag,"comFolder is not created");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            File gameFolder = new File(Environment.getExternalStorageDirectory()+Setting.GAME_FOLDER);
            try{
                if(!gameFolder.exists()){
                    if(gameFolder.mkdir()) {
                        Logger.log(tag,"gameFolder created");
                    } else {
                        Logger.log(tag,"gameFolder is not created");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    private void init() {
        GameContext.gameContext = new GameContext(this);
        GameContext.gameContext.advDAO = new AdventureDAO(this);
        GameContext.gameContext.cardDAO = new CardDAO(this);
        GameContext.gameContext.skillDAO = new SkillDAO(this);
        GameContext.gameContext.dungeonDAO = new DungeonDAO(this);
        GameContext.gameContext.itemDAO = new ItemDAO(this);
        GameContext.gameContext.itemGotDAO = new ItemGotDAO(this);
        GameContext.gameContext.monsterDAO = new MonsterDAO(this);
        GameContext.gameContext.myCardDAO = new MyCardDAO(this);
        GameContext.gameContext.rootLogDAO = new RootLogDAO(this);
        GameContext.gameContext.processLogDAO = new ProcessLogDAO(this);
        GameContext.gameContext.battleRootLogDAO = new BattleRootLogDAO(this);
        GameContext.gameContext.battleItemLogDAO = new BattleItemLogDAO(this);

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
                    if(GameDBHelper.isResetDB) {
                        Logger.log(tag, "set db");
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

                        Card.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(20);
                            }
                        });
                        BaseSkill.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(40);
                            }
                        });
                        Dungeon.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(60);
                            }
                        });
                        Item.init(MainActivity.this);
                        mHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(80);
                            }
                        });
                        Monster.init(MainActivity.this);
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

        GameContext.gameContext.passiveSkillList = BasePassiveSkill.listAll(MainActivity.this);

        Dungeon firestDungeon =  GameContext.gameContext.dungeonDAO.getAll().get(0);
        Logger.log(tag, firestDungeon.name+"");
        List<Adventure> advs =  GameContext.gameContext.advDAO.getAll();
        if(advs == null || advs.size() == 0) {
            GameContext.gameContext.adventure = new Adventure(10, firestDungeon.index, 0, 1,0,100);
            GameContext.gameContext.advDAO.insert(GameContext.gameContext.adventure);
            insertCardForBuyingOnNewAccount(GameContext.gameContext.adventure.id);
//            insertItemGotOnNewAccount(GameContext.gameContext.adventure.id);
        }else {
            GameContext.gameContext.adventure = advs.get(0);

            Logger.log(tag, "do concrete tactics");
            GameContext.gameContext.getPlayer(MainActivity.this).concreteConds(MainActivity.this);
            GameContext.gameContext.getPlayer(MainActivity.this).concreteAction(MainActivity.this);
        }

        Logger.log(tag, "itemCode:"+GameContext.gameContext.itemDAO.listAll().get(0).name);
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
