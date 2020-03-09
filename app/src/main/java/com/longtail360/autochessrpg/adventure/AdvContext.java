package com.longtail360.autochessrpg.adventure;

import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvContext {
    private String tag = "AdvContext";
    public int BASE_MET_MONSTER_RANDOM = 20;
    public int BASE_MET_GOOD_EVENT_RANDOM = 0;//18
    public int BASE_MET_BAD_EVENT_RANDOM = 35;//17
    public int BASE_EMPTY_EVENT_RANDOM = 45;

    public int BACK_MET_MONSTER_RANDOM = 20;
    public int BACK_MET_GOOD_EVENT_RANDOM = 10;
    public int BACK_MET_BAD_EVENT_RANDOM = 0;
    public int BACK_EMPTY_EVENT_RANDOM = 70;

    public int metMonsterRandom = BASE_MET_MONSTER_RANDOM;
    public int metGoodEvent = BASE_MET_GOOD_EVENT_RANDOM;
    public int metBadEvent = BASE_MET_BAD_EVENT_RANDOM;
    public int metEmptyEvent = BASE_EMPTY_EVENT_RANDOM;

    public int totalCoin;
    public Random mRandom = new Random();
    public Dungeon dungeon;
    public List<MyItem> currentItemList = new ArrayList<>();
    public RootLog rootLog;
//    public TeamStatus currentTeamStatus;
    public List<MyCard> team = new ArrayList<>();//include life and dead card
    public List<MyCard> cards = new ArrayList<>();
    public List<MyCard> deadCards = new ArrayList<>();
    public BattleContext battleContext;
    public String[] monsterKeys;
    public long advId;

    public AdvContext() {
    }

    public void resetEventPossibility() {
        metMonsterRandom = BASE_MET_MONSTER_RANDOM;
        metGoodEvent = BASE_MET_GOOD_EVENT_RANDOM;
        metBadEvent = BASE_MET_BAD_EVENT_RANDOM;
        metEmptyEvent = BASE_EMPTY_EVENT_RANDOM;
    }
//    public void refreshTeamStatus() {
//        StringBuilder cardIds = new StringBuilder();
//        StringBuilder cardHps = new StringBuilder();
//        StringBuilder cardLvs = new StringBuilder();
//        for(MyCard c : team) {
//            cardIds.append(c.card.code);
//            cardIds.append(",");
//            cardHps.append(c.battleHp);
//            cardHps.append(",");
//            cardLvs.append(c.level);
//            cardLvs.append(",");
//        }
//        currentTeamStatus.cardIds = cardIds.toString();
//        currentTeamStatus.hps = cardHps.toString();
//        currentTeamStatus.levels = cardLvs.toString();
//        Logger.log(tag, "currentTeamStatus.hps:"+currentTeamStatus.hps);
//    }
}
