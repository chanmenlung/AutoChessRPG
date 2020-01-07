package com.longtail360.autochessrpg.adventure;

import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.dao.log.TeamStatusDAO;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.log.TeamStatus;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvContext {
    private String tag = "AdvContext";
    public int totalCoin;
    public Random mRandom = new Random();
    public List<Tactics> tacticsList;
    public Dungeon dungeon;
    public List<Item> itemList = new ArrayList<>();
    public RootLog rootLog;
    public TeamStatus currentTeamStatus;
    public List<MyCard> team;//include life and dead card
    public List<CardActionEngine> cardActions = new ArrayList<>();
    public List<CardActionEngine> deadCardActions= new ArrayList<>();
    public BattleContext battleContext;
    public String[] monsterKeys;
    public long advId;

    public AdvContext() {
    }
    public void refreshTeamStatus() {
        StringBuilder cardIds = new StringBuilder();
        StringBuilder cardHps = new StringBuilder();
        StringBuilder cardLvs = new StringBuilder();
        for(MyCard c : team) {
            cardIds.append(c.card.code);
            cardIds.append(",");
            cardHps.append(c.battleHp);
            cardHps.append(",");
            cardLvs.append(c.level);
            cardLvs.append(",");
        }
        currentTeamStatus.cardIds = cardIds.toString();
        currentTeamStatus.hps = cardHps.toString();
        currentTeamStatus.levels = cardLvs.toString();
        Logger.log(tag, "currentTeamStatus.hps:"+currentTeamStatus.hps);
    }
}
