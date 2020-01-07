package com.longtail360.autochessrpg.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.adventure.BattleEngine;
import com.longtail360.autochessrpg.adventure.CardActionEngine;
import com.longtail360.autochessrpg.adventure.EventEngine;
import com.longtail360.autochessrpg.adventure.TeamActionEngine;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.dao.log.TeamStatusDAO;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.log.TeamStatus;
import com.longtail360.autochessrpg.prefab.BattleLogItem;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.prefab.ItemLogDesc;
import com.longtail360.autochessrpg.prefab.ProcessLogItem;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvFragment extends BaseFragment{
    String tag = "AdvFragment";
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

    private ScrollView processLogScroll;
    private LinearLayout processLogContainer;
    private View itemElementListLayout;
    private HeadBackButton backBt;
    public AdvContext advContext;
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
        advContext = new AdvContext();
        advContext.currentTeamStatus = new TeamStatus();
        advContext.team = MyCard.listByAdvIdAndType(GameContext.gameContext.adventure.id, MyCard.TYPE_IN_TEAM);
        advContext.dungeon = GameContext.gameContext.dungeonDAO.getByIndex(GameContext.gameContext.adventure.currentDungeonId);
        advContext.monsterKeys = advContext.dungeon.monsterIds.split(",");
        teamActionEngine = new TeamActionEngine(getContext(), advContext);
        eventEngine = new EventEngine(getContext(), advContext);
        battleEngine = new BattleEngine(getContext(), advContext);
        hpMp = getText(R.string.log_hp).toString();
        level = getText(R.string.log_level).toString();
        processLogContainer.removeAllViews();
        backBt.setText(advContext.dungeon.name);
        renderLogFromDb(GameContext.gameContext.adventure.currentRootLogId);
        initUpdateBt(view);
        initTakeBreakBt();
        initBackBt();
        Logger.log(tag,"numBlockPerArea:"+advContext.dungeon.numBlockPerArea);
        Logger.log(tag,"numAreaPerFloor:"+advContext.dungeon.numAreaPerFloor);
        Logger.log(tag,"numFloor:"+advContext.dungeon.numFloor);
        return view;
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
                if(advContext.rootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                    ActionResult actionResult = teamActionEngine.takeABreake();
                    ProcessLog log = new ProcessLog();
                    log.title = actionResult.title;
                    log.content = actionResult.content;
                    log.detail = actionResult.detail;
                    log.icon1 = actionResult.icon1;
                    log.icon2 = actionResult.icon2;
                    log.color = actionResult.color;
                    log.rootLog = advContext.rootLog;
                    ProcessLog.insertProcessLog(advContext, log,new Date().getTime());
                    putProcessLogToContainer(log);
                    advContext.rootLog.currentBlock++;
                    GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                }
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
                Logger.log(tag,"================================================");

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
                            int monsterNest = advContext.mRandom.nextInt(99);
                            if(monsterNest > 80){
                                updateEventRandomValue(60,10,10,20);
                                pLog = createGoToNextArea(advContext.rootLog.currentFloor+1, advContext.rootLog.currentArea+1, ProcessLog.RED, getContext().getString(R.string.adv_nextAreaMonsterNest));
                            }else {
                                pLog = createGoToNextArea(advContext.rootLog.currentFloor+1, advContext.rootLog.currentArea+1, ProcessLog.YELLOW, getContext().getString(R.string.adv_nextAreaNormal));
                            }
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
                                return;
                            }
                            advContext.rootLog.currentBlock++;
                            if(advContext.rootLog.currentFloor == (advContext.dungeon.numFloor-1)){
                                if(advContext.rootLog.currentArea == (advContext.dungeon.numAreaPerFloor-1)){
                                    if(advContext.rootLog.currentBlock == advContext.dungeon.numBlockPerArea){
                                        advContext.rootLog.isComingBack = 1;
                                        putProcessLogToContainer(finishStageAndStartBack());
                                        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
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
                                    return;
                                }
                            }
                            if(advContext.rootLog.currentBlock == advContext.dungeon.numBlockPerArea){
                                advContext.rootLog.currentBlock = 0;
                                advContext.rootLog.currentArea++;
                                GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                                return;
                            }
                            GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                            return;
                        }

                    }
                }else { //coming back
                    advContext.dungeon.numBlockPerArea = 1;
                    if(advContext.rootLog.currentFloor == 0 && advContext.rootLog.currentArea == 0 && advContext.rootLog.currentBlock ==0) {
                        if(advContext.rootLog.advStatus == RootLog.ADV_STATUS_PROGRESSING){
                            ProcessLog pLog = finishAll();
                            putProcessLogToContainer(pLog);
                        }
                        return;
                    }

                    //currentArea = 0, currentBlock = 0 indicate that all area is done, show start new floor
                    if(advContext.rootLog.currentArea == 0 && advContext.rootLog.currentBlock ==0){
                        ProcessLog pLog = backToFloor(advContext.rootLog.currentFloor);
                        putProcessLogToContainer(pLog);
                    }

                    advContext.rootLog.currentBlock--;
                    int random = advContext.mRandom.nextInt(100);
                    ActionResult actionResult;
                    if(random <= 65) {
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
                    }else {
                        actionResult =  eventEngine.findSpring();
                        ProcessLog log = new ProcessLog();
                        log.title = actionResult.title;
                        log.content = actionResult.content;
                        log.detail = actionResult.detail;
                        log.icon1 = actionResult.icon1;
                        log.icon2 = actionResult.icon2;
                        log.color = actionResult.color;
                        ProcessLog.insertProcessLog(advContext, log, new Date().getTime());
                        putProcessLogToContainer(log);

                    }
                    if(advContext.rootLog.currentArea == -1){
                        if(advContext.rootLog.currentBlock == -1){
                            advContext.rootLog.currentFloor--;
                            advContext.rootLog.currentArea = advContext.dungeon.numAreaPerFloor-1;
                            advContext.rootLog.currentBlock = advContext.dungeon.numBlockPerArea-1;
                            GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                            return;
                        }
                    }
                    if(advContext.rootLog.currentBlock == -1){
                        advContext.rootLog.currentBlock = advContext.dungeon.numBlockPerArea-1;
                        advContext.rootLog.currentArea--;
                        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                        return;
                    }
                    GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
                }

                for(MyCard ca : advContext.team){
                    GameContext.gameContext.myCardDAO.update(ca);
                }

            }
        });
    }

    private ProcessLog finishAll() {
        advContext.rootLog.advStatus = RootLog.ADV_STATUS_SUCCESS;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        GameContext.gameContext.adventure.hp = GameContext.gameContext.adventure.hp - advContext.deadCardActions.size();
        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);

        callback.onAdvFinish();

        int ints = (int)(advContext.rootLog.startingCoin * 0.1);
        advContext.rootLog.advStatus = 1;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = getContext().getResources().getString(R.string.adv_successAdv)
                .replace("{dungeon}", advContext.dungeon.name+"");
        if(GameContext.gameContext.adventure.hp == 0){
            log.content = getContext().getResources().getString(R.string.adv_hp0);
        }else {
            log.content = getContext().getResources().getString(R.string.adv_interestGain)
                    .replace("{value}", ints+"")
                    .replace("{price}", getPrice()+"'")
                    .replace("{hp}", advContext.deadCardActions.size()+"")
                    .replace("{deadNum}", advContext.deadCardActions.size()+"");
        }
        log.icon1 = "item_home";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }

    private long getPrice(){
        if(advContext.dungeon.index > 5){
            return 5;
        }else {
            return advContext.dungeon.index;
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
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.color = ProcessLog.GREEN;
        log.title = getContext().getResources().getString(R.string.adv_finishStage)
                .replace("{dungeon}", advContext.dungeon.name+"");
        log.icon1 = "item_star";
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }
    private ProcessLog logDead() {
        advContext.rootLog.advStatus = RootLog.ADV_STATUS_FAIL;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        callback.onAdvFinish();
        GameContext.gameContext.adventure.hp = GameContext.gameContext.adventure.hp - advContext.deadCardActions.size();
        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
        int ints = (int)(advContext.rootLog.startingCoin * 0.1);
        advContext.rootLog.advStatus = 2;
        GameContext.gameContext.rootLogDAO.update(advContext.rootLog);
        advContext.refreshTeamStatus();
        ProcessLog log = new ProcessLog();
        log.title = getContext().getResources().getString(R.string.adv_logDeadTitle).replace("{stage}",advContext.dungeon.name);
        if(GameContext.gameContext.adventure.hp == 0){
            log.content = getContext().getResources().getString(R.string.adv_hp0);
        }else {
            log.content = getContext().getResources().getString(R.string.adv_interestGain)
                    .replace("{value}", ints+"")
                    .replace("{startCoin}", getPrice()+"")
                    .replace("{hp}", advContext.deadCardActions.size()+"")
                    .replace("{deadNum}", advContext.deadCardActions.size()+"");
        }
        log.icon1 = "item_skull";
        log.detail = getContext().getResources().getString(R.string.adv_logDeadContent);
        ProcessLog.insertProcessLog(advContext,log,new Date().getTime());
        return log;
    }

    private ActionResult doActionIfEmptyEvent() {
        ActionResult actionResult = new ActionResult();
        actionResult.doThisAction = false;
        return  actionResult;
    }
    private ActionResult metEvent() {
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
    private void putProcessLogToContainer(final ProcessLog pLog){
        ProcessLogItem item = new ProcessLogItem(getContext(), pLog, pLog.color);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BattleRootLog battleRootLog = GameContext.gameContext.battleRootLogDAO.getByProcessId(pLog.id);
                if (battleRootLog == null) {
                    teamStatusContainer.removeAllViews();
                    TeamStatus teamStatus = GameContext.gameContext.teamStatusDAO.get(pLog.teamStatus.id);
                    if (pLog.detail != null && !pLog.detail.isEmpty()) {
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
                        StringBuilder content = new StringBuilder();
                        content.append(level);
                        content.append(teamStatus.getLevelArray()[i]);
                        content.append(" ");
                        content.append(hpMp);
                        content.append(teamStatus.getHpArray()[i]);
                        content.append("/");
                        content.append(advContext.team.get(i).getTotalHp());
                        Logger.log(tag, "content:"+content.toString());
                        ItemLogDesc desc = new ItemLogDesc(getContext());
                        desc.refresh(getContext(),advContext.team.get(i).card.name,content.toString(),advContext.team.get(i).card.head,advContext.team.get(i).card.customHead);
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
                        ItemLogDesc desc = new ItemLogDesc(getContext(), item.name, item.desc, ImageUtils.convertImageStringToInt(getContext(),item.itemCode));
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
            rootLog.dungeonId = advContext.dungeon.id;
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
            for(MyCard c : advContext.team){
                c.battleHp = c.getTotalHp();
                Logger.log(tag, "battleHp:"+c.battleHp);
                GameContext.gameContext.myCardDAO.update(c);
            }
            createStarAdvLog();

        }else {
            Logger.log(tag, "rootLog is not null:"+rootLogId);
            Logger.log(tag, "rootLog adv status:"+rootLog.advStatus);
            advContext.rootLog =rootLog;
        }

        for(MyCard c : advContext.team){
            advContext.cardActions.add(new CardActionEngine(getContext(), advContext, c));
        }

        processLogs = GameContext.gameContext.processLogDAO.listByRootLogId(GameContext.gameContext.adventure.currentRootLogId);
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
        metMonsterRandom = metMonster;
        metGoodEvent = goodEvent;
        metBadEvent = badEvent;
        metEmptyEvent = emptyEvent;
    }

    public void hideDetailForBack() {
        teamStatusLayout.setVisibility(View.INVISIBLE);
        battleLogLayout.setVisibility(View.INVISIBLE);
    }
    public interface AdvFragmentCallback {
        void onAdvFinish();
        void onPressBack();
        void onHpIs0();
    }
}
