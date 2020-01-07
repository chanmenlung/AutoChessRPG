package com.longtail360.autochessrpg.entity.log;

import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;

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
    public TeamStatus teamStatus;
    public BattleRootLog battleRootLog;
    public RootLog rootLog;

    public static void insertProcessLog(AdvContext advContext, ProcessLog log, long logTime){
        advContext.rootLog.progress++;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        advContext.refreshTeamStatus();

        GameContext.gameContext.teamStatusDAO.insert(advContext.currentTeamStatus);
        log.teamStatus = advContext.currentTeamStatus;
        log.rootLog = advContext.rootLog;
        log.logTime = logTime;
        log.coin = advContext.totalCoin;
        StringBuilder itemKeys = new StringBuilder();
        for(Item item : advContext.itemList){
            itemKeys.append(item.itemCode);
            itemKeys.append(",");
        }
        log.itemKeys = itemKeys.toString();
        GameContext.gameContext.processLogDAO.insert(log);
    }
}
