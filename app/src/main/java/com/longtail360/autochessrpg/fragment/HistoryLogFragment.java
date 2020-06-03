package com.longtail360.autochessrpg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.prefab.BattleLogItem;
import com.longtail360.autochessrpg.prefab.CardStatusDescItem;
import com.longtail360.autochessrpg.prefab.HeadBackButton;
import com.longtail360.autochessrpg.prefab.ItemLogDesc;
import com.longtail360.autochessrpg.prefab.ProcessLogItem;
import com.longtail360.autochessrpg.utils.ImageUtils;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class HistoryLogFragment extends BaseFragment{
    private String tag = "HistoryLogFragment";
    public static int selectedRootLogId;
    private ImageView teamStatusEventIcon1;
    private TextView teamStatusEventTitle;
    private TextView teamStatusEventContent;
    private View teamStatusLayout;
    private ViewGroup teamStatusContainer;
    private HeadBackButton teamStatusBackBt;
    private View teamStatusEventLayout;
    private ViewGroup itemContainer;

    private ScrollView processLogScroll;
    private LinearLayout processLogContainer;
    private View itemElementListLayout;
    private View battleLogLayout;
    private String level;
    private String hpMp;
    private ViewGroup battleLogContainer;
    private ImageView battleMonsterBigImage;
    List<MyCard> team = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_log, container, false);

        itemElementListLayout = view.findViewById(R.id.itemElementListLayout);
        teamStatusLayout = view.findViewById(R.id.teamStatusLayout);
        teamStatusEventLayout = view.findViewById(R.id.teamStatusEventLayout);
        teamStatusContainer = view.findViewById(R.id.teamStatusContainer);
        teamStatusEventTitle = view.findViewById(R.id.teamStatusEventTitle);
        teamStatusEventContent = view.findViewById(R.id.teamStatusEventContent);
        teamStatusEventIcon1 = view.findViewById(R.id.teamStatusEventIcon1);
        teamStatusBackBt = view.findViewById(R.id.teamStatusBackBt);
        battleLogContainer = view.findViewById(R.id.battleLogContainer);
        battleMonsterBigImage = view.findViewById(R.id.battleMonsterBigImage);
        RootLog rootLog = GameContext.gameContext.rootLogDAO.get(selectedRootLogId);
        List<ProcessLog> processLogs = GameContext.gameContext.processLogDAO.listByRootLogId(selectedRootLogId);
        if(processLogs != null && processLogs.size() >0) {
            for (int i=0; i<rootLog.progress; i++) {
                final ProcessLog pLog = processLogs.get(i);
//                putProcessLogToContainer(pLog);
            }
        }else {
            Logger.log(tag, "processLog-size is 0");
        }
        return view;
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
                        teamStatusEventTitle.setText(pLog.title);
                        teamStatusEventContent.setText(pLog.detail);
                        teamStatusEventIcon1.setImageResource(ImageUtils.convertImageStringToInt(getContext(), pLog.icon1));
                        teamStatusEventLayout.setVisibility(View.VISIBLE);

                    } else {
                        teamStatusEventLayout.setVisibility(View.GONE);
                    }

                    for (int i=0; i<pLog.getLevelArray().length; i++) {
                        StringBuilder content = new StringBuilder();
                        content.append(level);
                        content.append(pLog.getLevelArray()[i]);
                        content.append(" ");
                        content.append(hpMp);
                        content.append(pLog.getHpArray()[i]);
                        content.append("/");
                        content.append(team.get(i).totalHp);
//                        CardStatusDescItem desc = new CardStatusDescItem(getContext(), card, content.toString());
//                        teamStatusContainer.addView(desc);
                    }
                    teamStatusLayout.setVisibility(View.VISIBLE);
                } else {
                    refreshBattleLog(battleRootLog);
                    battleLogLayout.setVisibility(View.VISIBLE);
                }
//                isShowSubLog = true;
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
//                isShowSubLog = true;
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

}
