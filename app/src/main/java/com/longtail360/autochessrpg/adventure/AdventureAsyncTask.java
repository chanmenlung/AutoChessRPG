package com.longtail360.autochessrpg.adventure;

import android.os.AsyncTask;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.HomeActivity;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.dao.log.TeamStatusDAO;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.log.TeamStatus;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.util.Date;

public class AdventureAsyncTask extends AsyncTask<String, Integer, Long> {
    private String tag = "AdventureAsyncTask";
    private int BASE_MET_MONSTER_RANDOM = 30;
    private int BASE_MET_GOOD_EVENT_RANDOM = 25;
    private int BASE_MET_BAD_EVENT_RANDOM = 25;
    private int BASE_EMPTY_EVENT_RANDOM = 20;

    private int BACK_MET_MONSTER_RANDOM = 20;
    private int BACK_MET_GOOD_EVENT_RANDOM = 10;
    private int BACK_MET_BAD_EVENT_RANDOM = 0;
    private int BACK_EMPTY_EVENT_RANDOM = 70;

    private int metMonsterRandom = BASE_MET_MONSTER_RANDOM;
    private int metGoodEvent = BASE_MET_GOOD_EVENT_RANDOM;
    private int metBadEvent = BASE_MET_BAD_EVENT_RANDOM;
    private int metEmptyEvent = BASE_EMPTY_EVENT_RANDOM;

    public AdvContext advContext;
    private EventEngine eventEngine;
    public TeamActionEngine teamActionEngine;
    private BattleEngine battleEngine;
    AdvEngine advEngine;
    public HomeActivity homeActivity;
    private long currentTime;
    private boolean dead = false;
    @Override
    protected Long doInBackground(String... urls) {
        currentTime = new Date().getTime();
        init();
        createStarAdvLog();
        try {
            processAdv();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(dead){
            logDead();
            return 1l;
        }
        finishStageAndStartBack();
        processBack();
        finishAll();
        return 1l;
    }

    private void init() {
        advContext = new AdvContext();
        advContext.currentTeamStatus = new TeamStatus();
        advContext.team = MyCard.listByAdvIdAndType(GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
        advContext.dungeon = GameContext.gameContext.dungeonDAO.getByIndex(GameContext.gameContext.adventure.currentDungeonId);
        RootLog rootLog = new RootLog();
        rootLog.dungeonId = advContext.dungeon.id;
        rootLog.startingTime = new Date().getTime();
        rootLog.isHistoryLog = 0;
        rootLog.progress = 1;
        rootLog.startingCoin = GameContext.gameContext.adventure.coin;
        advContext.rootLog =rootLog;
        GameContext.gameContext.rootLogDAO.insert(rootLog);
        teamActionEngine = new TeamActionEngine(homeActivity, advContext);
        eventEngine = new EventEngine(homeActivity, advContext);
        GameContext.gameContext.adventure.currentRootLogId = rootLog.id;
        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
        for(MyCard c : advContext.team){
            advContext.cardActions.add(new CardActionEngine(homeActivity, advContext, c));
        }
        advContext.monsterKeys = advContext.dungeon.monsterIds.split(",");
        battleEngine = new BattleEngine(homeActivity, advContext);


    }

    public void processAdv() throws JSONException {
        Logger.log(tag, "advContext.dungeon.numFloor:"+advContext.dungeon.numFloor);
        Logger.log(tag, "advContext.dungeon.numAreaPerFloor:"+advContext.dungeon.numAreaPerFloor);
        for(int i=0; i<advContext.dungeon.numFloor; i++){
            createStartFloor(i+1);
            for(int j=0; j<advContext.dungeon.numAreaPerFloor; j++){
                if(j==0){
                    createGoToArea0(i+1);
                }else {
                    int monsterNest = advContext.mRandom.nextInt(99);
                    if(monsterNest > 80){
                        updateEventRandomValue(60,10,10,20);
                        createGoToNextArea(i+1, j+1, ProcessLog.RED, homeActivity.getString(R.string.adv_nextAreaMonsterNest));
                    }else {
                        createGoToNextArea(i+1, j+1, ProcessLog.YELLOW, homeActivity.getString(R.string.adv_nextAreaNormal));
                    }
                }
                Logger.log(tag, "advContext.dungeon.numBlockPerArea:"+advContext.dungeon.numBlockPerArea);
                for(int k=0; k<advContext.dungeon.numBlockPerArea; k++){
                    ActionResult actionResult = metEvent();
                    Logger.log(tag, "actionResult-doThisAction:"+actionResult.doThisAction);
                    if(!actionResult.doThisAction){ //if empty event
                        actionResult = doActionIfEmptyEvent();
                    }

                    if(actionResult != null && actionResult.doThisAction){
                        ProcessLog log = new ProcessLog();
                        log.title = actionResult.title;
                        log.content = actionResult.content;
                        log.detail = actionResult.detail;
                        log.icon1 = actionResult.icon1;
                        log.icon2 = actionResult.icon2;
                        log.color = actionResult.color;
                        log.rootLog = advContext.rootLog;
                        ProcessLog.insertProcessLog(advContext, log,currentTime);
                        if(actionResult.isBattle){
                            battleEngine.createAnBattle(log,actionResult.monsterKey, actionResult.monsterName, actionResult.numOfMonster);
                        }
                    }else {
//                        actionResult = eventEngine.createEpmtyEvent();
                        if(actionResult.doThisAction){
                            Logger.log(tag, "log-event");
                            ProcessLog log = new ProcessLog();
                            log.title = actionResult.title;
                            log.content = actionResult.content;
                            log.detail = actionResult.detail;
                            log.icon1 = actionResult.icon1;
                            log.icon2 = actionResult.icon2;
                            log.color = actionResult.color;
                            log.rootLog = advContext.rootLog;
                            ProcessLog.insertProcessLog(advContext, log,currentTime);
                        }
                    }
                    if(teamActionEngine.checkAllDead()){
                        dead = true;
                        return;
                    }

                }
                resetEventRandomValue();
            }
            publishProgress(i*100/advContext.dungeon.numFloor);
        }
    }

    private void processBack() {
        metMonsterRandom = BACK_MET_MONSTER_RANDOM;
        metGoodEvent = BACK_MET_GOOD_EVENT_RANDOM;
        metBadEvent = BACK_MET_BAD_EVENT_RANDOM;
        metEmptyEvent = BACK_EMPTY_EVENT_RANDOM;
        for(int i=advContext.dungeon.numFloor; i>0; i--){
            int numOfArea =advContext.dungeon.numAreaPerFloor;
            for(int j=0; j<numOfArea; j++){
                int numOfBlock = advContext.dungeon.numBlockPerArea;
                advContext.refreshTeamStatus();
                for(int k=0; k<numOfBlock; k++){
                    advContext.refreshTeamStatus();

                    int randomWhichEvent = advContext.mRandom.nextInt(metMonsterRandom+metGoodEvent+metBadEvent+metEmptyEvent);
                    int metMonsterRandomMin = (int)(metMonsterRandom*0.75); //since this is back adv, met monster pos is lower, so multi by 0.75
                    if(randomWhichEvent < metMonsterRandomMin){
                        ActionResult actionResult = eventEngine.metMonsterEvent();
                        ProcessLog log = new ProcessLog();
                        log.title = actionResult.title;
                        log.content = actionResult.content;
                        log.detail = actionResult.detail;
                        log.icon1 = actionResult.icon1;
                        log.icon2 = actionResult.icon2;
                        log.color = actionResult.color;
                        ProcessLog.insertProcessLog(advContext, log,currentTime);
                        battleEngine.createAnBattle(log,actionResult.monsterKey, actionResult.monsterName, actionResult.numOfMonster);
                    }
//                    else if(randomWhichEvent < metMonsterRandom+metGoodEvent){ //epmpty event
//                        ActionResult actionResult =  eventEngine.metGoodEvent();
//                        ProcessLog log = new ProcessLog();
//                        log.title = actionResult.title;
//                        log.content = actionResult.content;
//                        log.detail = actionResult.detail;
//                        log.icon1 = actionResult.icon1;
//                        log.icon2 = actionResult.icon2;
//                        log.color = actionResult.color;
//                        log.rootLog = advContext.rootLog;
//                        ProcessLog.insertProcessLog(advContext, log,currentTime);
//                    }
                }
            }
            if(i != 1){
                backToFloor(i-1);
            }
        }
    }

    private void finishAll() {
        int ints = (int)(advContext.rootLog.startingCoin * 0.1);
        advContext.rootLog.advStatus = 1;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = homeActivity.getResources().getString(R.string.adv_successAdv)
                .replace("{dungeon}", advContext.dungeon.name+"");
        log.content = homeActivity.getResources().getString(R.string.adv_interestGain)
                .replace("{value}", ints+"")
                .replace("{startCoin}", advContext.rootLog.startingCoin+"'");
        log.icon1 = "item_home";
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private void backToFloor(int floor){
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_backToFloor)
                .replace("{floor}", floor+"");
        log.icon1 = "item_stone2";
        log.detail = homeActivity.getResources().getString(R.string.adv_backToFloorDetail);
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private ActionResult doActionIfEmptyEvent() throws JSONException {
        ActionResult actionResult = new ActionResult();
        actionResult.doThisAction = false;
        return  actionResult;
    }

    private void createStarAdvLog(){
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_start);
        log.icon1 = "item_wing";
        log.detail = homeActivity.getResources().getString(R.string.adv_startContent)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", advContext.dungeon.numFloor+"");
        ProcessLog.insertProcessLog(advContext,log, currentTime);

    }

    private void createGoToArea0(int floor) {
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_startFloorArea0Title).replace("{floor}", floor+"");
        log.content = homeActivity.getResources().getString(R.string.adv_startFloorArea0Content)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", floor+"");
        log.icon1 = "item_boots";
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private void createGoToNextArea(int floor, int area,int color, String eventDetail) {
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_nextArea).replace("{floor}", floor+"");
        log.content = homeActivity.getResources().getString(R.string.adv_nextAreaContent)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", floor+"")
                .replace("{area}", area+"");
        log.detail = eventDetail;
        log.icon1 = "item_boots";
        log.color = color;
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private void createStartFloor(int floor) {
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_startFloor).replace("{floor}", floor+"");
        log.icon1 = "item_stone2";
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }


    private void updateEventRandomValue(int metMonster, int goodEvent, int badEvent, int emptyEvent) {
        metMonsterRandom = metMonster;
        metGoodEvent = goodEvent;
        metBadEvent = badEvent;
        metEmptyEvent = emptyEvent;
    }

    private ActionResult metEvent() throws JSONException {
        ActionResult actionResult = new ActionResult();
        int randomWhichEvent = advContext.mRandom.nextInt(metMonsterRandom+metGoodEvent+metBadEvent+metEmptyEvent);
        if(randomWhichEvent < metMonsterRandom){ //met monster
            actionResult =  eventEngine.metMonsterEvent();
            actionResult.doThisAction = true;
        }else if(randomWhichEvent < metMonsterRandom+metGoodEvent){ //met good event
            actionResult =  eventEngine.metGoodEvent();
            actionResult.doThisAction = true;
        }else if(randomWhichEvent < metMonsterRandom+metGoodEvent+metBadEvent){ //met bad event
            actionResult =  eventEngine.metBadEvent();
            actionResult.doThisAction = true;
        }else {
            actionResult.doThisAction = false;
        }
//        actionResult.title = actionResult.title + randomWhichEvent+"";
        return actionResult;

    }

    private void finishStageAndStartBack() {
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = homeActivity.getResources().getString(R.string.adv_finishStage)
                .replace("{dungeon}", advContext.dungeon.name+"");
        log.icon1 = "item_star";
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private void logDead() {
        int ints = (int)(advContext.rootLog.startingCoin * 0.1);

        advContext.rootLog.advStatus = 2;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.title = homeActivity.getResources().getString(R.string.adv_logDeadTitle).replace("{stage}",advContext.dungeon.name);
        log.content = homeActivity.getResources().getString(R.string.adv_interestGain)
                .replace("{value}", ints+"")
                .replace("{startCoin}", advContext.rootLog.startingCoin+"");
        log.icon1 = "item_skull";
        log.detail = homeActivity.getResources().getString(R.string.adv_logDeadContent);
        ProcessLog.insertProcessLog(advContext,log,currentTime);
    }

    private void resetEventRandomValue() {
        metMonsterRandom = BASE_MET_MONSTER_RANDOM;
        metGoodEvent = BASE_MET_GOOD_EVENT_RANDOM;
        metBadEvent = BASE_MET_BAD_EVENT_RANDOM;
        metEmptyEvent = BASE_EMPTY_EVENT_RANDOM;
    }


    @Override
    protected void onPostExecute(Long result) {
        homeActivity.showLogFragment();
    }
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        homeActivity.progressBar.setProgress(values[0]);
    }

}
