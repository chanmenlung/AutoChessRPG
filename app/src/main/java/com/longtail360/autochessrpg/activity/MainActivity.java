package com.longtail360.autochessrpg.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.CardInBattleDAO;
import com.longtail360.autochessrpg.dao.CardInHandDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CardForBuying;
import com.longtail360.autochessrpg.entity.CardInBattle;
import com.longtail360.autochessrpg.entity.CardInHand;
import com.longtail360.autochessrpg.entity.GameContext;

import java.util.List;

public class MainActivity extends BaseActivity {
    private boolean isResetDB = false;
    private View comNameLayout;
    private View comName;
    private View continueGameBt;
    private View startNewGameBt;
    private View illustrationBt;
    private View itemBt;
    private View otherBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comName= findViewById(R.id.com_name);
        comNameLayout = findViewById(R.id.com_name_layout);
        continueGameBt = findViewById(R.id.continueGameBt);
        startNewGameBt = findViewById(R.id.startNewGameBt);
        illustrationBt = findViewById(R.id.illustrationBt);
        itemBt = findViewById(R.id.itemBt);
        otherBt = findViewById(R.id.otherBt);
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(2000);
        comName.startAnimation(alphaAnimation);
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
            }
        });

        continueGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOtherActivity(HomeActivity.class);
            }
        });
        startNewGameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBox.reset(2);
                popupBox.title.setText(getString(R.string.ui_mainPage_isConfirmRestartGame));
                popupBox.rightConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startOtherActivity(HomeActivity.class);
                    }
                });
            }
        });
        illustrationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        itemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        otherBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void init() {
        GameContext.gameContext.advContextDAO = new AdventureDAO(this);
        GameContext.gameContext.cardDAO = new CardDAO(this);
        GameContext.gameContext.cardInBattleDAO = new CardInBattleDAO(this);
        GameContext.gameContext.cardInHandDAO = new CardInHandDAO(this);
        GameContext.gameContext.dungeonDAO = new DungeonDAO(this);
        GameContext.gameContext.itemDAO = new ItemDAO(this);
        GameContext.gameContext.itemGotDAO = new ItemGotDAO(this);
        GameContext.gameContext.monsterDAO = new MonsterDAO(this);

        if(isResetDB) {
            resetDB();
        }

        List<Adventure> advContexts =  GameContext.gameContext.advContextDAO.getAll();
        if(advContexts == null || advContexts.size() == 0) {
            GameContext.gameContext.advContextDAO.insert(new Adventure(200, 0, 0));
        }else {
            GameContext.gameContext.adventureContext = advContexts.get(0);
            GameContext.gameContext.cardInBattles = GameContext.gameContext.cardInBattleDAO.listByAdventureId(GameContext.gameContext.adventureContext.id);
            GameContext.gameContext.cardInHands = GameContext.gameContext.cardInHandDAO.listByAdventureId(GameContext.gameContext.adventureContext.id);
            GameContext.gameContext.cardForBuyings = GameContext.gameContext.cardForBuyingDAO.listByAdventureId(GameContext.gameContext.adventureContext.id);

            if(GameContext.gameContext.cardInBattles != null && GameContext.gameContext.cardInBattles.size() > 0) {
                for(CardInBattle ch : GameContext.gameContext.cardInBattles){
                    Card chess = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = chess;
                }
            }

            if(GameContext.gameContext.cardInHands != null && GameContext.gameContext.cardInHands.size() > 0) {
                for(CardInHand ch : GameContext.gameContext.cardInHands){
                    Card chess = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = chess;
                }
            }

            if(GameContext.gameContext.cardForBuyings != null && GameContext.gameContext.cardForBuyings.size() > 0) {
                for(CardForBuying ch : GameContext.gameContext.cardForBuyings){
                    Card chess = GameContext.gameContext.cardDAO.get(ch.cardId);
                    ch.card = chess;
                }
            }
        }



    }

    private void resetDB() {
        Card chess;
        chess = new Card(1, "race", "job", "skillCode", "name", null,
                "c1head", "customHead", "c1", "customImage", "customSkillName","customSkillBattleDesc", 4,
                200, 400, 800,
                100,200, 300,
                5,10,20,
                2,4,6);
        GameContext.gameContext.cardDAO.insert(chess);
    }
}
