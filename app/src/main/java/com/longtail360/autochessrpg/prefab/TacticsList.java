package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.Collections;

/**
 * Created by chanmenlung on 22/10/2018.
 */

public class TacticsList {
//    private String tag = "TacticsList";
//    public Card card;
//    private ViewGroup strategyList;
//    private View newStrategyButton;
//    private View strategyBackButton;
//    private View strategyItemSelectMenuLayout;
//
//    private View editStrategyButton;
//    private View deleteStrategyButton;
//    private View strategyUpButton;
//    private View strategyDownButton;
//    private View cancelSelectStrategyButton;
//
//    private LinearLayout strategyContainer;
//    private Context mContext;
//    private TacticsEdit strategyEdit;
//    private TacticsList thisObj;
//    private Tactics focusStrategy;
//    private TacticsDescItem focusStrategyItem;
//    public TacticsList(Context context, Card card) {
//        super(context);
//        mContext = context;
//        this.card = card;
//        LayoutInflater inflater = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.tactics_list, this);
//
//        strategyList = (ViewGroup)findViewById(R.id.strategyList);
//        newStrategyButton = findViewById(R.id.newStrategyButton);
//        strategyContainer = (LinearLayout)findViewById(R.id.strategyContainer);
//        strategyBackButton = findViewById(R.id.strategyBackButton);
//        strategyItemSelectMenuLayout = findViewById(R.id.strategyItemSelectMenuLayout);
//        editStrategyButton = findViewById(R.id.editStrategyButton);
//        deleteStrategyButton = findViewById(R.id.deleteStrategyButton);
//        strategyUpButton = findViewById(R.id.strategyUpButton);
//        strategyDownButton = findViewById(R.id.strategyDownButton);
//        cancelSelectStrategyButton = findViewById(R.id.cancelSelectStrategyButton);
////        editStrategyLayout = findViewById(R.id.editStrategyLayout);
//        thisObj = this;
//        strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
//        loadStrategyList();
//        strategyList.setVisibility(INVISIBLE);
//        setListener();
//    }
//
//    public void showView(boolean show){
//        if(show){
//            strategyList.setVisibility(VISIBLE);
//        }else {
//            strategyList.setVisibility(INVISIBLE);
//        }
//    }
//    private void setListener() {
//        strategyBackButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                strategyList.setVisibility(INVISIBLE);
//            }
//        });
//        newStrategyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Tactics strategy = new Tactics(getContext());
//                strategyEdit = showEditStrategy(getContext(), thisObj, strategyEdit,  strategy);
//            }
//        });
//
//        editStrategyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                strategyEdit = showEditStrategy(getContext(), thisObj, strategyEdit, focusStrategy);
//                strategyEdit.reload(focusStrategy);
//            }
//        });
//
//        deleteStrategyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GameContext.gameContext.getPlayer(getContext()).tacticsList.remove(focusStrategy);
//                strategyContainer.removeView(focusStrategyItem);
//                strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
////                BaseActivity.savePlayerData(mContext);
//                GameContext.gameContext.savePlayerData(mContext);
//            }
//        });
//
//        strategyUpButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int index =  GameContext.gameContext.getPlayer(getContext()).tacticsList.indexOf(focusStrategy);
//                if(index > 0){
//                    Collections.swap( GameContext.gameContext.getPlayer(getContext()).tacticsList, index, index -1);
//                }
//                focusStrategy.order = index -1;
//                loadStrategyList();
//                strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
//                GameContext.gameContext.savePlayerData(mContext);
//            }
//        });
//
//        strategyDownButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int index =  GameContext.gameContext.getPlayer(getContext()).tacticsList.indexOf(focusStrategy);
//                if(index < ( GameContext.gameContext.getPlayer(getContext()).tacticsList.size()-1)){
//                    Collections.swap( GameContext.gameContext.getPlayer(getContext()).tacticsList, index, index +1);
//                }
//                focusStrategy.order = index +1;
//                loadStrategyList();
//                strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
//                GameContext.gameContext.savePlayerData(mContext);
//            }
//        });
//
//        cancelSelectStrategyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
//            }
//        });
//    }
//
//    private TacticsEdit showEditStrategy(Context context, TacticsList stgList, TacticsEdit stgEdit, Tactics strategy) {
//        if(stgEdit == null) {
//            stgEdit = new TacticsEdit(context, stgList, strategy);
//            stgList.addView(stgEdit);
//        }
//        stgEdit.reload( strategy);
//        stgEdit.showView(true);
//        return stgEdit;
//    }
////    public void reloadNewCard(BaseCard card) {
////        this.card = card;
////        loadStrategyList();
////    }
//
//
//    public void loadStrategyList() {
//        Logger.log(tag, "loadStrategyList");
//        strategyContainer.removeAllViews();
//        Collections.sort( GameContext.gameContext.getPlayer(getContext()).tacticsList);
//        Logger.log(tag, "card.tacticsList.size():"+ GameContext.gameContext.getPlayer(getContext()).tacticsList.size());
//        for(int i=0; i< GameContext.gameContext.getPlayer(getContext()).tacticsList.size();i++){
////            final int k = i;
//            final TacticsDescItem item = new TacticsDescItem(mContext, GameContext.gameContext.getPlayer(getContext()).tacticsList.get(i), i);
//            item.content.setText( GameContext.gameContext.getPlayer(getContext()).tacticsList.get(i).concatStr(getContext()));
//            item.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Logger.log("card.tacticsList.size:"+card.tacticsList.size());
//                    focusStrategy = item.strategy;
//                    focusStrategyItem = item;
//                    strategyItemSelectMenuLayout.setVisibility(VISIBLE);
//                }
//            });
//            strategyContainer.addView(item);
//        }
//    }
//    public void callBackFromSave() {
//        strategyItemSelectMenuLayout.setVisibility(INVISIBLE);
//        loadStrategyList();
//    }




}
