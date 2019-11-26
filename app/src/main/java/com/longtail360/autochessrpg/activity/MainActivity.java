package com.longtail360.autochessrpg.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.CardForBuyingDAO;
import com.longtail360.autochessrpg.dao.CardInBattleDAO;
import com.longtail360.autochessrpg.dao.CardInHandDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.SkillDAO;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CardForBuying;
import com.longtail360.autochessrpg.entity.CardInBattle;
import com.longtail360.autochessrpg.entity.CardInHand;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.ItemGot;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.prefab.CardDetail;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ExternalResActivity {
    private String tag = "MainActivity";
    private boolean resetPlayer = true;
    private TextView comName;
    private View comNameLayout;
    private View backgroundLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisLayout = findViewById(R.id.thisLayout);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        comName = findViewById(R.id.com_name);
        comNameLayout = findViewById(R.id.comNameLayout);
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
        GameContext.gameContext.cardInBattleDAO = new CardInBattleDAO(this);
        GameContext.gameContext.cardInHandDAO = new CardInHandDAO(this);
        GameContext.gameContext.cardForBuyingDAO = new CardForBuyingDAO(this);
        GameContext.gameContext.dungeonDAO = new DungeonDAO(this);
        GameContext.gameContext.itemDAO = new ItemDAO(this);
        GameContext.gameContext.itemGotDAO = new ItemGotDAO(this);
        GameContext.gameContext.monsterDAO = new MonsterDAO(this);

        if(GameDBHelper.isResetDB) {
            resetDB();
        }
        Dungeon firestDungeon =  GameContext.gameContext.dungeonDAO.getAll().get(0);
        Logger.log(tag, firestDungeon.name+"");
        List<Adventure> advs =  GameContext.gameContext.advDAO.getAll();
        if(advs == null || advs.size() == 0) {
            GameContext.gameContext.adventure = new Adventure(200, firestDungeon.id, 0, 1,0,100);
            GameContext.gameContext.advDAO.insert(GameContext.gameContext.adventure);
            insertCardForBuyingOnNewAccount(GameContext.gameContext.adventure.id);
            insertItemGotOnNewAccount(GameContext.gameContext.adventure.id);
        }else {
            GameContext.gameContext.adventure = advs.get(0);
            GameContext.gameContext.cardInBattles = GameContext.gameContext.cardInBattleDAO.listByAdventureId(GameContext.gameContext.adventure.id);
            GameContext.gameContext.cardInHands = GameContext.gameContext.cardInHandDAO.listByAdventureId(GameContext.gameContext.adventure.id);
            GameContext.gameContext.cardForBuyings = GameContext.gameContext.cardForBuyingDAO.listByAdventureId(GameContext.gameContext.adventure.id);
            GameContext.gameContext.itemGots = GameContext.gameContext.itemGotDAO.listByAdventureId(GameContext.gameContext.adventure.id);
            if(GameContext.gameContext.cardInBattles != null && GameContext.gameContext.cardInBattles.size() > 0) {
                for(CardInBattle ch : GameContext.gameContext.cardInBattles){
                    Card card = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = card;
                }
            }

            if(GameContext.gameContext.cardInHands != null && GameContext.gameContext.cardInHands.size() > 0) {
                for(CardInHand ch : GameContext.gameContext.cardInHands){
                    Card card = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = card;
                }
            }

            if(GameContext.gameContext.cardForBuyings != null && GameContext.gameContext.cardForBuyings.size() > 0) {
                for(CardForBuying ch : GameContext.gameContext.cardForBuyings){
                    Card card = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = card;
                }
            }

            if(GameContext.gameContext.itemGots != null && GameContext.gameContext.itemGots.size() > 0) {
                for(ItemGot ch : GameContext.gameContext.itemGots){
                    Item item = GameContext.gameContext.itemDAO.get(ch.itemId);
                    ch.item = item;
                }
            }
        }

        Logger.log(tag, "itemCode:"+GameContext.gameContext.itemDAO.listAll().get(0).name);
    }

    private void insertItemGotOnNewAccount(long advId) {
        ItemGot item;
        item = new ItemGot();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.RED_POTION_LARGE);
        item.itemId = item.item.id;
        item.adventureId = advId;
        GameContext.gameContext.itemGotDAO.insert(item);

        item = new ItemGot();
        item.onBody = 1;
        item.item = GameContext.gameContext.itemDAO.getByItemCode(Item.RED_POTION_MIDDLE);
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
        CardForBuying cfb;
        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(0)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);

        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(1)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);

        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(2)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);

        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(3)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);

        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(4)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);

        cfb = new CardForBuying();
        cfb.adventureId = advId;
        cfb.cardId = normalCards.get(numbers.get(5)).id;
        GameContext.gameContext.cardForBuyingDAO.insert(cfb);
    }
    private void resetDB() {
        GameDBHelper.getDatabase(this).execSQL("delete from " + AdventureDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + CardDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + CardForBuyingDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + CardInBattleDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + CardInHandDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + DungeonDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + ItemDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + ItemGotDAO.TABLE_NAME);
        GameDBHelper.getDatabase(this).execSQL("delete from " + MonsterDAO.TABLE_NAME);

        Card.init(this);
        BaseSkill.init(this);
        Dungeon.init(this);
        Item.init(this);

    }
}
