package com.longtail360.autochessrpg.entity.log;

import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.utils.Logger;

public class ProcessLog {
    public static int RED = 1;
    public static int GREEN = 2;
    public static int BLUE = 3;
    public static int YELLOW = 4;
    public long id;
    public long logTime;
    public String title;
    public String content;
    public String detail;
    public String icon1;
    public String icon2;
    public int color; //1=red,2=green,3=blue,4=yellow
    public int coin;
    public String itemKeys;
    public String hps;
    public String levels;
    public BattleRootLog battleRootLog;
    public RootLog rootLog;

    private String[] cardIdsArray;
    private String[] levelsArray;
    private String[] hpsArray;

    public static void insertProcessLog(AdvContext advContext, ProcessLog log, long logTime){
        advContext.rootLog.progress++;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
//        advContext.refreshTeamStatus();

//        GameContext.gameContext.teamStatusDAO.insert(advContext.currentTeamStatus);
        log.rootLog = advContext.rootLog;
        log.logTime = logTime;
        log.coin = advContext.totalCoin;
        refreshTeamStatus(advContext, log);
        StringBuilder itemKeys = new StringBuilder();
        for(MyItem item : advContext.currentItemList){
            itemKeys.append(item.item.itemCode);
            itemKeys.append(",");
        }
        log.itemKeys = itemKeys.toString();
        GameContext.gameContext.processLogDAO.insert(log);
        for(MyCard myCard : advContext.team){
            GameContext.gameContext.myCardDAO.update(myCard);
        }
    }
    public String[] getLevelArray() {
        if(levelsArray == null){
            levelsArray = levels.split(",");
        }
        return levelsArray;
    }

    public String[] getHpArray() {
        if(hpsArray == null){
            hpsArray = hps.split(",");
        }
        return hpsArray;
    }
    public static void refreshTeamStatus(AdvContext advContext,ProcessLog log) {
//        StringBuilder cardIds = new StringBuilder();
        StringBuilder cardHps = new StringBuilder();
        StringBuilder cardLvs = new StringBuilder();
        for(MyCard c : advContext.team) {
//            cardIds.append(c.card.code);
//            cardIds.append(",");
            cardHps.append(c.battleHp);
            cardHps.append(",");
            cardLvs.append(c.level);
            cardLvs.append(",");
        }
        log.hps = cardHps.toString();
        log.levels = cardLvs.toString();
    }
}
