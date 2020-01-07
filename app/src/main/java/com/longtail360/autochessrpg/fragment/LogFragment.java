package com.longtail360.autochessrpg.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.dao.log.TeamStatusDAO;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Dungeon;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
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

import java.util.ArrayList;
import java.util.List;

public class LogFragment extends BaseFragment{
    String tag = "LogFragment";
    public static String ROOT_LOG_ID = "rootLogId";
    private RootLogDAO rootLogDAO;
    private ProcessLogDAO processLogDAO;
    private TeamStatusDAO teamStatusDAO;
    private BattleRootLogDAO battleRootLogDAO;
    private BattleItemLogDAO battleItemLogDAO;

    private ScrollView processLogScroll;
    private LinearLayout processLogContainer;

    private View itemElementListLayout;
    private HeadBackButton backBt;
    private HeadBackButton itemLogBackBt;

    private View itemListLayout;
    private ViewGroup itemContainer;
    private View updateBt;


    private View teamStatusLayout;
    private ViewGroup teamStatusContainer;
    private List<ItemLogDesc> teamStatusDescList = new ArrayList<>();
    private String hpMp;
    private String level;
    private String forMakingEqm;
    private List<Card> cards;
    private HeadBackButton teamStatusBackBt;
    private View teamStatusEventLayout;
    private ImageView teamStatusEventIcon1;
    private TextView teamStatusEventTitle;
    private TextView teamStatusEventContent;

    private View battleLogLayout;
    private View battleListLayout;
    private ViewGroup battleLogContainer;
    private HeadBackButton battleLogBackBt;
    private ImageView battleMonsterBigImage;
    private Dungeon dungeon;
    public boolean hadClickLogItem = false;
    private  RootLog rootLog;
    private List<ProcessLog> processLogs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.log(tag,"init-LogFragment");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.adv_log, container, false);
        initViewAndDAO(view);
        itemElementListLayout = view.findViewById(R.id.itemElementListLayout);
        itemContainer = view.findViewById(R.id.itemContainer);
        processLogScroll = view.findViewById(R.id.processLogScroll);
        initItemElementLayout(view);
        initBattleLogLayout(view);
        initUpdateBt(view);
        Logger.log(tag, "GameContext.gameContext.adventure.currentRootLogId:"+GameContext.gameContext.adventure.currentRootLogId);
        rootLog = rootLogDAO.get(GameContext.gameContext.adventure.currentRootLogId);
        if(rootLog == null){
            return view;
        }
        dungeon = GameContext.gameContext.dungeonDAO.get(rootLog.dungeonId);
        processLogs = processLogDAO.listByRootLogId(GameContext.gameContext.adventure.currentRootLogId);
        Logger.log(tag,"processLogs-size:"+processLogs.size());
        Logger.log(tag,"rootLog.progress:"+rootLog.progress);
        backBt.setText(dungeon.name);
        if(processLogs != null && processLogs.size() >0) {
            initTeamStatusLayout(view, processLogs.get(0).teamStatus.id);
            for (int i=0; i<rootLog.progress; i++) {
                final ProcessLog pLog = processLogs.get(i);
                putProcessLogToContainer(pLog);
            }
        }
        return view;
    }

    private void initUpdateBt(View view) {
        updateBt = view.findViewById(R.id.updateBt);
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rootLog.progress < processLogs.size()){
                    final ProcessLog pLog = processLogs.get(rootLog.progress);
                    putProcessLogToContainer(pLog);
                    rootLog.progress++;
                    Logger.log(tag, "progress:"+rootLog.progress);
                    Logger.log(tag, "rootLog.isHistoryLog:"+rootLog.isHistoryLog);
                    if(rootLog.progress == processLogs.size() && rootLog.isHistoryLog == 0){
                        int interest = (int)(rootLog.startingCoin * 0.1);
                        Logger.log(tag, "get interest:"+interest);
                        GameContext.gameContext.adventure.coin = GameContext.gameContext.adventure.coin + interest;
                        GameContext.gameContext.adventure.getExp(1);
                        GameContext.gameContext.adventure.currentDungeonId++;
                        if(GameContext.gameContext.adventure.currentDungeonId >5){
                            GameContext.gameContext.adventure.coin = 50;
                        }else {
                            GameContext.gameContext.adventure.coin = (int)(GameContext.gameContext.adventure.coin + 10*GameContext.gameContext.adventure.currentDungeonId);
                        }
                        GameContext.gameContext.advDAO.update(GameContext.gameContext.adventure);
                        rootLog.isHistoryLog = 1;

                    }
                    rootLogDAO.update(rootLog);
                }
            }
        });
    }

    private void putProcessLogToContainer(final ProcessLog pLog){
        ProcessLogItem item = new ProcessLogItem(getContext(), pLog, pLog.color);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hadClickLogItem = true;

                BattleRootLog battleRootLog = battleRootLogDAO.getByProcessId(pLog.id);
                if (battleRootLog == null) {
                    TeamStatus teamStatus = teamStatusDAO.get(pLog.teamStatus.id);
                    if (pLog.detail != null && !pLog.detail.isEmpty()) {
                        Logger.log(tag, "plogtitle:"+pLog.title);
                        teamStatusEventTitle.setText(pLog.title);
                        teamStatusEventContent.setText(pLog.detail);
                        teamStatusEventIcon1.setImageResource(ImageUtils.convertImageStringToInt(getContext(), pLog.icon1));
                        teamStatusEventLayout.setVisibility(View.VISIBLE);

                    } else {
                        teamStatusEventLayout.setVisibility(View.GONE);
                    }
                    teamStatusContainer.removeAllViews();
                    for (int i = 0; i < cards.size(); i++) {
                        StringBuilder content = new StringBuilder();
                        content.append(level);
                        content.append(teamStatus.getLevelArray()[i]);
                        content.append(" ");
                        content.append(hpMp);
                        content.append(teamStatus.getHpArray()[i]);
                        content.append("/");
//                        content.append(cards.get(i).getTotalHp());
                        Logger.log(tag, "content:"+content.toString());
//                        teamStatusDescList.get(i).refresh(cards.get(i).name, content.toString(), 0);
                        ItemLogDesc desc = new ItemLogDesc(getContext());
                        desc.refresh(getContext(),cards.get(i).name,content.toString(),cards.get(i).head,cards.get(i).customHead);
                        teamStatusContainer.addView(desc);
//                        teamStatusDescList.add(desc);
                    }
                    teamStatusLayout.setVisibility(View.VISIBLE);
                } else {
                    refreshBattleLog(battleRootLog);
                    battleLogLayout.setVisibility(View.VISIBLE);
                }
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

    private void initViewAndDAO(View view) {
        rootLogDAO = new RootLogDAO(getContext());
        processLogDAO = new ProcessLogDAO(getContext());
        teamStatusDAO = new TeamStatusDAO(getContext());
        battleItemLogDAO = new BattleItemLogDAO(getContext());
        battleRootLogDAO = new BattleRootLogDAO(getContext());
        processLogContainer = view.findViewById(R.id.processLogContainer);
        backBt= view.findViewById(R.id.backBt);
        processLogContainer.removeAllViews();
    }

    private void initItemElementLayout(View view) {
        itemLogBackBt= view.findViewById(R.id.itemLogBackBt);
        itemLogBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemElementListLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initBattleLogLayout(View view) {
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

    }

    private void initTeamStatusLayout(View view, long rootTeamStatusId){
        hpMp = getText(R.string.log_hp).toString();
        level = getText(R.string.log_level).toString();
        cards = new ArrayList<>();
        Logger.log(tag, "rootTeamStatusId:"+rootTeamStatusId);
        TeamStatus rootTeamStatus = teamStatusDAO.get(rootTeamStatusId);
        if(rootTeamStatus == null){
            Logger.log(tag, "rootTeamStatus is null");
        }
        Logger.log(tag, "rootTeamStatus.cardIds:"+rootTeamStatus.cardIds);
        String[] cardIds = rootTeamStatus.cardIds.split(",");
        for(String id : cardIds){
            Logger.log(tag, "id:"+id);
            Card card = GameContext.gameContext.cardDAO.getByCode(id);
            if(card == null){
                Logger.log(tag, "card is null, id:"+id);
            }else {
                cards.add(card);
            }
        }
        List<Card> dbCards = GameContext.gameContext.cardDAO.getAll();
        teamStatusLayout = view.findViewById(R.id.teamStatusLayout);
        teamStatusEventLayout = view.findViewById(R.id.teamStatusEventLayout);
        teamStatusContainer = view.findViewById(R.id.teamStatusContainer);
        teamStatusEventTitle = view.findViewById(R.id.teamStatusEventTitle);
        teamStatusEventContent = view.findViewById(R.id.teamStatusEventContent);
        teamStatusEventIcon1 = view.findViewById(R.id.teamStatusEventIcon1);
        teamStatusBackBt = view.findViewById(R.id.teamStatusBackBt);
//        for(int i=0; i<cards.size(); i++){
//            ItemLogDesc desc = new ItemLogDesc(getContext());
//            desc.refresh(getContext(),"title","content",cards.get(i).head,cards.get(i).customHead);
//            teamStatusContainer.addView(desc);
//            teamStatusDescList.add(desc);
//        }
        teamStatusBackBt.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamStatusLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void refreshBattleLog(BattleRootLog battleRootLog) {
//        BattleRootLog rootLog = battleRootLogDAO.getByProcessId(processLog.id);
        List<BattleItemLog> battleItemLogs = battleItemLogDAO.getAllByBattleRootLogId(battleRootLog.id);
        if(battleItemLogs == null && battleItemLogs.size() > 0){
            Logger.log(tag,"no battle log");
        }else {
            if(battleLogContainer.getChildCount() > 0){
                battleLogContainer.removeAllViews();
            }
            int monsterImage = ImageUtils.convertImageStringToInt(getContext(),battleRootLog.monsterImage);
//            Bitmap ball = BitmapFactory.decodeResource(getResources(), monsterImage);
//            int width = ball.getWidth();
//            int height = ball.getHeight();
//            ViewGroup.LayoutParams wrapContentLp = battleMonsterBigImage.getLayoutParams();
//            wrapContentLp.width = (int)(width * 0.55);
//            wrapContentLp.height =  (int)(height * 0.55);
            battleMonsterBigImage.setImageResource(monsterImage);
//            battleMonsterBigImage.setLayoutParams(wrapContentLp);

            battleLogContainer.addView(battleMonsterBigImage);
            for(BattleItemLog log : battleItemLogs){
                BattleLogItem logItem = new BattleLogItem(getContext(), log);
                battleLogContainer.addView(logItem);
            }
        }


    }

    public void hideDetailForBack() {
        teamStatusLayout.setVisibility(View.INVISIBLE);
        battleLogLayout.setVisibility(View.INVISIBLE);
    }
}
