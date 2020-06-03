package com.longtail360.autochessrpg.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.MonsterValueActivity;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.adventure.BattleEngine;
import com.longtail360.autochessrpg.adventure.EventEngine;
import com.longtail360.autochessrpg.adventure.TeamActionEngine;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.Setting;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.prefab.BattleLogItem;
import com.longtail360.autochessrpg.prefab.CardStatusDescItem;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.prefab.ItemLogDesc;
import com.longtail360.autochessrpg.prefab.ProcessLogItem;
import com.longtail360.autochessrpg.utils.ImageUtils;
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
import java.util.Date;
import java.util.List;

public class AdvFragment extends BaseFragment{
    String tag = "AdvFragment";
    public long selectRootLogId;
    private ScrollView processLogScroll;
    private LinearLayout processLogContainer;
    private View itemElementListLayout;
    private HeadBackButton backBt;
    public AdvContext advContext = new AdvContext();
    private EventEngine eventEngine;
    public TeamActionEngine teamActionEngine;
    private ViewGroup itemContainer;
    private BattleEngine battleEngine;
    private List<ProcessLog> processLogs;
    private String hpMp;
    private String level;
    private HeadBackButton teamStatusBackBt;
    private View teamStatusEventLayout;
    private ImageView teamStatusEventIcon1;
    private TextView teamStatusEventTitle;
    private TextView teamStatusEventContent;
    private View teamStatusLayout;
    private ViewGroup teamStatusContainer;
    private HeadBackButton itemLogBackBt;

    private View battleLogLayout;
    private View battleListLayout;
    private ViewGroup battleLogContainer;
    private HeadBackButton battleLogBackBt;
    private ImageView battleMonsterBigImage;
    private View updateBt;
    private View takeABreakBt;
    public AdvFragmentCallback callback;
    public boolean isShowSubLog;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_log, container, false);
        processLogContainer = view.findViewById(R.id.processLogContainer);
        processLogScroll = view.findViewById(R.id.processLogScroll);
        itemContainer = view.findViewById(R.id.itemContainer);
        backBt= view.findViewById(R.id.backBt);
        itemElementListLayout = view.findViewById(R.id.itemElementListLayout);
        teamStatusLayout = view.findViewById(R.id.teamStatusLayout);
        teamStatusEventLayout = view.findViewById(R.id.teamStatusEventLayout);
        teamStatusContainer = view.findViewById(R.id.teamStatusContainer);
        teamStatusEventTitle = view.findViewById(R.id.teamStatusEventTitle);
        teamStatusEventContent = view.findViewById(R.id.teamStatusEventContent);
        teamStatusEventIcon1 = view.findViewById(R.id.teamStatusEventIcon1);
        takeABreakBt = view.findViewById(R.id.takeABreakBt);
        teamStatusBackBt = view.findViewById(R.id.teamStatusBackBt);
        teamStatusBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamStatusLayout.setVisibility(View.INVISIBLE);
            }
        });
        itemLogBackBt= view.findViewById(R.id.itemLogBackBt);
        itemLogBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemElementListLayout.setVisibility(View.INVISIBLE);
            }
        });
        battleLogLayout = view.findViewById(R.id.battleLogLayout);
        battleListLayout = view.findViewById(R.id.battleListLayout);
        battleLogContainer = view.findViewById(R.id.battleLogContainer);
        battleLogBackBt = view.findViewById(R.id.battleLogBackBt);
        battleMonsterBigImage = view.findViewById(R.id.battleMonsterBigImage);
        battleLogLayout.setVisibility(View.GONE);
        battleLogBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                battleLogLayout.setVisibility(View.INVISIBLE);
            }
        });
        advContext.dungeon = GameContext.gameContext.dungeonDAO.getByIndex(GameContext.gameContext.adventure.currentDungeonId);
        advContext.monsterKeys = advContext.dungeon.monsterIds.split(",");
        advContext.advId = GameContext.gameContext.adventure.id;
        teamActionEngine = new TeamActionEngine(getContext(), advContext);
        eventEngine = new EventEngine(getContext(), advContext);
        battleEngine = new BattleEngine(getContext(), advContext);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        hpMp = getText(R.string.log_hp).toString();
        level = getText(R.string.log_level).toString();
        processLogContainer.removeAllViews();
        backBt.setText(advContext.dungeon.name);
        renderLogFromDb(selectRootLogId);
        initUpdateBt(view);
        initTakeBreakBt();
        initBackBt();
        httpGetMonsterValue();
        Logger.log(tag,"numBlockPerArea:"+advContext.dungeon.numBlockPerArea);
        Logger.log(tag,"numAreaPerFloor:"+advContext.dungeon.numAreaPerFloor);
        Logger.log(tag,"numFloor:"+advContext.dungeon.numFloor);
        Logger.log(tag,"advContext.team.size():"+advContext.team.size());
        loadDbTeamStatus();
        return view;
    }

    private void loadDbTeamStatus() {
        for(MyCard myCard : advContext.team){
            Logger.log(tag,"myCard-relife:"+myCard.relife);
        }
    }

    private void httpGetMonsterValue() {
        String urlStr = Setting.STAGE_VALUE_URL + GameContext.gameContext.adventure.currentDungeonId;
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final HttpURLConnection finalUrlConnection = urlConnection;
        new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream in = new BufferedInputStream(finalUrlConnection.getInputStream());
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    for (String line; (line = r.readLine()) != null; ) {
                        total.append(line).append('\n');
                        Logger.log(tag,  line);
                    }
                    JsonObject jsonObject = new JsonParser().parse(total.toString()).getAsJsonObject();
                    Logger.log(tag,  "monsterValue:"+jsonObject.get("meetMon").getAsInt()+"");
                    editor.putInt(MonsterValueActivity.MEET_MONSTER, jsonObject.get("meetMon").getAsInt());
                    editor.putInt(MonsterValueActivity.GOOD_EVENT, jsonObject.get("goodBox").getAsInt());
                    editor.putInt(MonsterValueActivity.BAD_EVENT, jsonObject.get("badBox").getAsInt());
                    editor.putInt(MonsterValueActivity.HP, jsonObject.get("hpRange").getAsInt());
                    editor.putInt(MonsterValueActivity.ATTACK, jsonObject.get("attackRange").getAsInt());
                    editor.putInt(MonsterValueActivity.DEFENSE, jsonObject.get("defenseRange").getAsInt());
                    editor.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    finalUrlConnection.disconnect();
                }
            }
        }).start();
    }

    private void initBackBt() {
        backBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onPressBack();
            }
        });
    }

    private void initTakeBreakBt(){
        takeABreakBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(advContext.rootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
//                    ActionResult actionResult = teamActionEngine.takeABreake();
//                    ProcessLog log = new ProcessLog();
//                    log.title = actionResult.title;
//                    log.content = actionResult.content;
//                    log.detail = actionResult.detail;
//                    log.icon1 = actionResult.icon1;
//                    log.icon2 = actionResult.icon2;
//                    log.color = actionResult.color;
//                    log.rootLog = advContext.rootLog;
//                    ProcessLog.insertProcessLog(advContext, log,new Date().getTime());
//                    putProcessLogToContainer(log);
//                    advContext.rootLog.currentBlock++;
//                    GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
//                }
                itemElementListLayout.setVisibility(View.VISIBLE);
                itemContainer.removeAllViews();
                List<MyItem> onBodyItems = MyItem.listByOnBody(GameContext.gameContext.adventure.id);
                for(MyItem myItem : onBodyItems){
                    if(myItem.item != null) {
                        ItemLogDesc desc = new ItemLogDesc(getContext(), myItem.item.name, myItem.item.desc, ImageUtils.convertImageStringToInt(getContext(),myItem.item.imageName));
                        itemContainer.addView(desc);
                    }
                }
                isShowSubLog = true;
            }
        });
    }
    private void initUpdateBt(View view) {

        updateBt = view.findViewById(R.id.updateBt);
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(advContext.rootLog.advStatus == RootLog.ADV_STATUS_FAIL){
                    Logger.log(tag, "adv fail");
                    return;
                }
                Logger.log(tag,"progress:"+advContext.rootLog.currentFloor+","+advContext.rootLog.currentArea+","+advContext.rootLog.currentBlock);
                Logger.log(tag,"=====================start================");
                if(teamActionEngine.checkAllDead()){
                    Logger.log(tag,"advContext.team.size()ccc:"+advContext.team.size());
                    advContext.rootLog.advStatus = RootLog.ADV_STATUS_FAIL;
                    GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                    putProcessLogToContainer(logDead());
                    updateMyCardToDB();
                    return;
                }
                if(advContext.rootLog.isComingBack == 0){ //progressing
                    //currentArea = 0, currentBlock = 0 indicate that all area is done, show start new floor
                    if(advContext.rootLog.currentArea == 0 && advContext.rootLog.currentBlock == 0){
                        ProcessLog pLog = createStartFloor(advContext.rootLog.currentFloor+1);
                        putProcessLogToContainer(pLog);
                        pLog = createGoToArea0(advContext.rootLog.currentFloor+1);
                        putProcessLogToContainer(pLog);
                    }

                    if(advContext.rootLog.currentBlock == 0) {//currentBlock = 0 indicate that all block is done, show go to next area
                        if(advContext.rootLog.currentArea != 0){
                            ProcessLog pLog = null;
//                            int monsterNest = advContext.mRandom.nextInt(99);
//                            if(monsterNest > 80){
//                                updateEventRandomValue(60,advContext.metGoodEvent,advContext.metBadEvent,20);
//                                pLog = createGoToNextArea(advContext.rootLog.currentFloor+1, advContext.rootLog.currentArea+1, ProcessLog.RED, getContext().getString(R.string.adv_nextAreaMonsterNest));
//                            }else {
//                                pLog = createGoToNextArea(advContext.rootLog.currentFloor+1, advContext.rootLog.currentArea+1, ProcessLog.YELLOW, getContext().getString(R.string.adv_nextAreaNormal));
//                            }
                            pLog = createGoToNextArea(advContext.rootLog.currentFloor+1, advContext.rootLog.currentArea+1, ProcessLog.YELLOW, getContext().getString(R.string.adv_nextAreaNormal));
                            putProcessLogToContainer(pLog);

                        }
                    }
                    int limit =20;
                    int count = 0;
                    while(true) {
                        count++;
                        if(count == limit){
                            Logger.log(tag, "over while loop");
                            break;
                        }
                        ActionResult actionResult = metEvent();
                        if(!actionResult.doThisAction){ //if no anything happen (empty event)
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
                            ProcessLog.insertProcessLog(advContext, log,new Date().getTime());
                            if(actionResult.isBattle){
                                battleEngine.createAnBattle(log,actionResult.monsterKey, actionResult.monsterName, actionResult.numOfMonster);
                            }
                            putProcessLogToContainer(log);
                            if(teamActionEngine.checkAllDead()){
                                advContext.rootLog.advStatus = RootLog.ADV_STATUS_FAIL;
                                GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                                putProcessLogToContainer(logDead());
                                updateMyCardToDB();
                                return;
                            }

                            advContext.rootLog.currentBlock++;
                            if(advContext.rootLog.currentFloor == (advContext.dungeon.numFloor-1)){
                                if(advContext.rootLog.currentArea == (advContext.dungeon.numAreaPerFloor-1)){
                                    if(advContext.rootLog.currentBlock == advContext.dungeon.numBlockPerArea){
                                        advContext.rootLog.isComingBack = 1;
                                        putProcessLogToContainer(finishStageAndStartBack());
                                        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                                        updateMyCardToDB();
                                        return;
                                    }
                                }
                            }
                            if(advContext.rootLog.currentArea == (advContext.dungeon.numAreaPerFloor-1)){
                                if(advContext.rootLog.currentBlock == advContext.dungeon.numBlockPerArea){
                                    advContext.rootLog.currentFloor++;
                                    advContext.rootLog.currentArea = 0;
                                    advContext.rootLog.currentBlock = 0;
                                    GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                                    updateMyCardToDB();
                                    return;
                                }
                            }
                            if(advContext.rootLog.currentBlock == advContext.dungeon.numBlockPerArea){
                                advContext.rootLog.currentBlock = 0;
                                advContext.rootLog.currentArea++;
                                GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                                updateMyCardToDB();
                                return;
                            }
                            GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                            updateMyCardToDB();
                            return;
                        }

                    }
                }else { //coming back
                    int limit =20;
                    int count = 0;
                    while(true) {
                        count++;
                        if(count == limit){
                            Logger.log(tag, "over while loop");
                            break;
                        }
                        if(advContext.rootLog.currentFloor == 0 && advContext.rootLog.currentArea == 0 && advContext.rootLog.currentBlock ==0) {
                            if(advContext.rootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                                ProcessLog pLog = finishAll();
                                putProcessLogToContainer(pLog);
                            }
                            updateMyCardToDB();
                            return;
                        }

                        //currentArea = 0, currentBlock = 0 indicate that all area is done, show start new floor
                        if(advContext.rootLog.currentArea == 0 && advContext.rootLog.currentBlock ==0){
                            ProcessLog pLog = backToFloor(advContext.rootLog.currentFloor);
                            putProcessLogToContainer(pLog);
                        }

                        advContext.rootLog.currentBlock--;
                        if(advContext.rootLog.currentArea == -1){
                            if(advContext.rootLog.currentBlock == -1){
                                advContext.rootLog.currentFloor--;
                                advContext.rootLog.currentArea = advContext.dungeon.numAreaPerFloor-1;
                                advContext.rootLog.currentBlock = advContext.dungeon.numBlockPerArea-1;
                                GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
//                                updateMyCardToDB();
//                                return;
                            }
                        }
                        if(advContext.rootLog.currentBlock == -1){
                            advContext.rootLog.currentBlock = advContext.dungeon.numBlockPerArea-1;
                            advContext.rootLog.currentArea--;
                            GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
//                            updateMyCardToDB();
//                            return;
                        }
                        int random = advContext.mRandom.nextInt(100);
                        ActionResult actionResult;
                        if(random <= 10) {
                            actionResult = eventEngine.metMonsterEvent();
                            ProcessLog log = new ProcessLog();
                            log.title = actionResult.title;
                            log.content = actionResult.content;
                            log.detail = actionResult.detail;
                            log.icon1 = actionResult.icon1;
                            log.icon2 = actionResult.icon2;
                            log.color = actionResult.color;
                            ProcessLog.insertProcessLog(advContext, log, new Date().getTime());
                            battleEngine.createAnBattle(log, actionResult.monsterKey, actionResult.monsterName, actionResult.numOfMonster);
                            putProcessLogToContainer(log);
                            updateMyCardToDB();
                            return;
                        }
                        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                    }
                }

                for(MyCard ca : advContext.team){
                    GameContext.gameContext.myCardDAO.update(ca);
                }

                Logger.log(tag,"=====================end================");

            }
        });
    }

    private void updateMyCardToDB() {
        for(MyCard ca : advContext.team){
            GameContext.gameContext.myCardDAO.update(ca);
        }
    }

    private ProcessLog finishAll() {
        int ints = (int)(advContext.rootLog.startingCoin * 0.1);
//        GameContext.gameContext.adventure.hp = GameContext.gameContext.adventure.hp - advContext.deadCards.size();
        GameContext.gameContext.adventure.currentDungeonId++;
        int coin = getPrice()+ints;
        if(GameContext.gameContext.adventure.hp > 0) {
            GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin+coin;
            GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            advContext.rootLog.advStatus = RootLog.ADV_STATUS_SUCCESS;
            GameContext.gameContext.adventure.getExp(1);
            GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        }


//        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = getContext().getResources().getString(R.string.adv_successAdv)
                .replace("{dungeon}", advContext.dungeon.name+"");
        if(GameContext.gameContext.adventure.hp == 0){
            log.content = getContext().getResources().getString(R.string.adv_hp0);
        }else {
            int deadSize = advContext.deadCards.size();
            if(deadSize > 0){
                log.content = getContext().getResources().getString(R.string.adv_interestGainRelife)
                        .replace("{price}", coin+"")
                        .replace("{hp}", advContext.deadCards.size()+"");
            }else {
                log.content = getContext().getResources().getString(R.string.adv_interestGain)
                        .replace("{price}", (getPrice()+ints)+"")
                        .replace("{hp}", advContext.deadCards.size()+"");
            }
        }
        log.icon1 = "item_home";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        for(MyCard myCard : advContext.team){
            myCard.setValueOnAdvFinish();
            GameContext.gameContext.myCardDAO.update(myCard);
        }
        if(GameContext.gameContext.adventure.hp <= 0){
            callback.onHpIs0();
        }else {
            callback.onAdvFinish(true);
        }
        return log;
    }

    private int getPrice(){
        if(GameContext.gameContext.adventure.currentDungeonId > 5){
            return 50;
        }else {
            return GameContext.gameContext.adventure.currentDungeonId*10;
        }
    }
    private ProcessLog backToFloor(int floor){
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_backToFloor)
                .replace("{floor}", floor+"");
        log.icon1 = "item_stone2";
        log.detail = getContext().getResources().getString(R.string.adv_backToFloorDetail);
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }

    private ProcessLog finishStageAndStartBack() {
//        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = getContext().getResources().getString(R.string.adv_finishStage)
                .replace("{dungeon}", advContext.dungeon.name+"");
        log.icon1 = "item_star";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }
    private ProcessLog logDead() {
        int notFinishProgress;
        if(advContext.rootLog.isComingBack == 0) {
            int totalBlock = advContext.dungeon.numFloor * advContext.dungeon.numAreaPerFloor * advContext.dungeon.numBlockPerArea;
            int finishBlock = advContext.rootLog.currentFloor*advContext.dungeon.numAreaPerFloor * advContext.dungeon.numBlockPerArea
                    +advContext.rootLog.currentArea*advContext.dungeon.numAreaPerFloor + advContext.rootLog.currentBlock;
            notFinishProgress = 100 - finishBlock * 70/totalBlock;
        }else {
            notFinishProgress = 100 - (70 + advContext.rootLog.currentFloor * 30/advContext.dungeon.numFloor);
        }


        int ints = (int)(advContext.rootLog.startingCoin * 0.1);//calculate interest
        int coin = getPrice()+ints;
        advContext.rootLog.advStatus = RootLog.ADV_STATUS_FAIL;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);

        GameContext.gameContext.adventure.hp = GameContext.gameContext.adventure.hp - (notFinishProgress/10+1);
        GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin+coin;
        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
        advContext.rootLog.advStatus = 2;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
//        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_logDeadTitle).replace("{stage}",advContext.dungeon.name);
        if(GameContext.gameContext.adventure.hp == 0){
            log.content = getContext().getResources().getString(R.string.adv_hp0);
        }else {
            int deadSize = advContext.deadCards.size();
            if(deadSize > 0) {
                log.content = getContext().getResources().getString(R.string.adv_failAdvRelife)
                        .replace("{price}", coin+"")
                        .replace("{hp}", (notFinishProgress/10+1)+"")
                        .replace("{progress}", notFinishProgress+"");
            }else {
                log.content = getContext().getResources().getString(R.string.adv_failAdv)
                        .replace("{price}", coin+"");
            }
        }
        log.icon1 = "item_skull";
        log.detail = getContext().getResources().getString(R.string.adv_logDeadContent);
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        Logger.log(tag, "advContext.team-size:"+advContext.team.size());
        for(MyCard myCard : advContext.team){
            myCard.setValueOnAdvFinish();
            GameContext.gameContext.myCardDAO.update(myCard);
        }
        if(GameContext.gameContext.adventure.hp <= 0) {
            callback.onHpIs0();
        }else {
            callback.onAdvFinish(false);
        }

        return log;
    }

    private ActionResult doActionIfEmptyEvent() {
        ActionResult actionResult = new ActionResult();
        actionResult.doThisAction = false;
        return  actionResult;
    }
    private ActionResult metEvent() {
        ActionResult actionResult = new ActionResult();

        int randomWhichEvent = advContext.mRandom.nextInt(100);
        if(randomWhichEvent < advContext.metMonsterRandom){ //met monster
            actionResult =  eventEngine.metMonsterEvent();
            actionResult.doThisAction = true;
        }else if(randomWhichEvent < advContext.metMonsterRandom+advContext.metGoodEvent){ //met good event
            actionResult =  eventEngine.metGoodEvent();
            actionResult.doThisAction = true;
        }else if(randomWhichEvent < advContext.metMonsterRandom+advContext.metGoodEvent+advContext.metBadEvent){ //met bad event
            actionResult =  eventEngine.metBadEvent();
            actionResult.doThisAction = true;
        }else {
            actionResult.doThisAction = false;
        }
//        actionResult.title = actionResult.title + randomWhichEvent+"";
        return actionResult;

    }
    private void putProcessLogToContainer(final ProcessLog pLog){
        ProcessLogItem item = new ProcessLogItem(getContext(), pLog, pLog.color);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BattleRootLog battleRootLog = GameContext.gameContext.battleRootLogDAO.getByProcessId(pLog.id);
                if (battleRootLog == null) {
                    teamStatusContainer.removeAllViews();

                    if (pLog.detail != null && !pLog.detail.isEmpty()) {
                        Logger.log(tag, "pLog.id:"+pLog.id);
                        Logger.log(tag, "plogtitle:"+pLog.title);
                        Logger.log(tag, "pLogDetail:"+pLog.detail);
                        teamStatusEventTitle.setText(pLog.title);
                        teamStatusEventContent.setText(pLog.detail);
                        teamStatusEventIcon1.setImageResource(ImageUtils.convertImageStringToInt(getContext(), pLog.icon1));
                        teamStatusEventLayout.setVisibility(View.VISIBLE);

                    } else {
                        teamStatusEventLayout.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < advContext.team.size(); i++) {
                        Card card = advContext.team.get(i).getCard(getContext());

                        StringBuilder content = new StringBuilder();
                        content.append(level);
                        content.append(pLog.getLevelArray()[i]);
                        content.append(" ");
                        content.append(hpMp);
                        content.append(pLog.getHpArray()[i]);
                        content.append("/");
                        content.append(advContext.team.get(i).totalHp);
                        CardStatusDescItem desc = new CardStatusDescItem(getContext(), card, content.toString());
                        teamStatusContainer.addView(desc);
                    }
                    teamStatusLayout.setVisibility(View.VISIBLE);
                } else {
                    refreshBattleLog(battleRootLog);
                    battleLogLayout.setVisibility(View.VISIBLE);
                }
                isShowSubLog = true;
            }
        });
        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemElementListLayout.setVisibility(View.VISIBLE);
                itemContainer.removeAllViews();
                for(String itemKey : pLog.itemKeys.split(",")){
                    Item item = GameContext.gameContext.itemDAO.getByItemCode(itemKey);
                    if(item != null) {
                        ItemLogDesc desc = new ItemLogDesc(getContext(), item.name, item.desc, ImageUtils.convertImageStringToInt(getContext(),item.imageName));
                        itemContainer.addView(desc);
                    }
                }
                isShowSubLog = true;
                return true;
            }
        });
        processLogContainer.addView(item);
        processLogScroll.post(new Runnable() {
            @Override
            public void run() {
                processLogScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private ProcessLog createStartFloor(int floor) {
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_startFloor).replace("{floor}", floor+"");
        log.icon1 = "item_stone2";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }

    private void refreshBattleLog(BattleRootLog battleRootLog) {
        List<BattleItemLog> battleItemLogs = GameContext.gameContext.battleItemLogDAO.getAllByBattleRootLogId(battleRootLog.id);
        if(battleItemLogs == null && battleItemLogs.size() > 0){
            Logger.log(tag,"no battle log");
        }else {
            if(battleLogContainer.getChildCount() > 0){
                battleLogContainer.removeAllViews();
            }
            int monsterImage = ImageUtils.convertImageStringToInt(getContext(),battleRootLog.monsterImage);
            battleMonsterBigImage.setImageResource(monsterImage);
            battleLogContainer.addView(battleMonsterBigImage);
            for(BattleItemLog log : battleItemLogs){
                BattleLogItem logItem = new BattleLogItem(getContext(), log);
                battleLogContainer.addView(logItem);
            }
        }


    }

    private void renderLogFromDb(long rootLogId){
        RootLog rootLog = GameContext.gameContext.rootLogDAO.get(rootLogId);
        if(rootLog == null || rootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){ //create new adv
            Logger.log(tag, "rootLog is null:"+rootLogId);
            rootLog = new RootLog();
            rootLog.dungeonId = advContext.dungeon.index;
            rootLog.advStatus = RootLog.ADV_STATUS_PROGRESSING;
            rootLog.startingTime = new Date().getTime();
            rootLog.isHistoryLog = 0;
            rootLog.progress = 0;
            rootLog.currentFloor = 0;
            rootLog.currentArea = 0;
            rootLog.currentBlock = 0;
            rootLog.startingCoin = GameContext.gameContext.adventure.coin;
            GameContext.gameContext.rootLogDAO.insert(rootLog);
            GameContext.gameContext.adventure.currentRootLogId = rootLog.id;
            GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
            advContext.rootLog = rootLog;

            GameContext.gameContext.adventure.currentRootLog = rootLog;
//            for(MyCard c : advContext.team){
//                c.battleHp = c.totalHp;
//                Logger.log(tag, "battleHp:"+c.battleHp);
//                GameContext.gameContext.myCardDAO.update(c);
//            }
            createStarAdvLog();

        }else {
            Logger.log(tag, "rootLog is not null:"+rootLogId);
            Logger.log(tag, "rootLog adv status:"+rootLog.advStatus);
            advContext.rootLog =rootLog;
            Logger.log(tag, "advContext.rootLog.currentFloor:"+advContext.rootLog.currentFloor);
            Logger.log(tag, "advContext.rootLog.currentArea:"+advContext.rootLog.currentArea);
            Logger.log(tag, "advContext.rootLog.currentBlock:"+advContext.rootLog.currentBlock);
        }

        advContext.cards = new ArrayList<>();
        advContext.deadCards = new ArrayList<>();
        for(MyCard card : advContext.team) {
            if(card.battleHp < 1) {
                advContext.deadCards.add(card);
            }else {
                advContext.cards.add(card);
            }
        }

        advContext.currentItemList= MyItem.listByOnBody(GameContext.gameContext.adventure.id);

        processLogs = GameContext.gameContext.processLogDAO.listByRootLogId(rootLogId);
        if(processLogs != null && processLogs.size() >0) {
            for (int i=0; i<rootLog.progress; i++) {
                final ProcessLog pLog = processLogs.get(i);
                putProcessLogToContainer(pLog);
            }
        }else {
            Logger.log(tag, "processLog-size is 0");
        }


    }


    private ProcessLog createStarAdvLog(){
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_start);
        log.icon1 = "item_wing";
        log.detail = getContext().getResources().getString(R.string.adv_startContent)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", advContext.dungeon.numFloor+"");
        ProcessLog.insertProcessLog(advContext,log, new Date().getTime());
        for(MyCard myCard : advContext.team){
            myCard.cd = myCard.getInitCd();
            updateMyCardToDB();
        }
        return log;
    }


    private ProcessLog createGoToArea0(int floor) {
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_startFloorArea0Title).replace("{floor}", floor+"");
        log.content = getContext().getResources().getString(R.string.adv_startFloorArea0Content)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", floor+"");
        log.icon1 = "item_boots";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }
    private ProcessLog createGoToNextArea(int floor, int area,int color, String eventDetail) {
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_nextArea).replace("{floor}", floor+"");
        log.content = getContext().getResources().getString(R.string.adv_nextAreaContent)
                .replace("{maze}", advContext.dungeon.name+"")
                .replace("{floor}", floor+"")
                .replace("{area}", area+"");
        log.detail = eventDetail;
        log.icon1 = "item_boots";
        log.color = color;
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }
    private void updateEventRandomValue(int metMonster, int goodEvent, int badEvent, int emptyEvent) {
        advContext.metMonsterRandom = metMonster;
        advContext.metGoodEvent = goodEvent;
        advContext.metBadEvent = badEvent;
        advContext.metEmptyEvent = emptyEvent;
    }

    public void hideDetailForBack() {
        teamStatusLayout.setVisibility(View.INVISIBLE);
        battleLogLayout.setVisibility(View.INVISIBLE);
    }
    public interface AdvFragmentCallback {
        void onAdvFinish(boolean success);
        void onPressBack();
        void onHpIs0();
    }
}
