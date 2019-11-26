package com.longtail360.autochessrpg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.prefab.TacticsDescItem;
import com.longtail360.autochessrpg.prefab.TacticsEdit;
import com.longtail360.autochessrpg.prefab.TacticsList;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.Collections;

public class TacticsFragment  extends BaseFragment implements TacticsEdit.CallBack{
    private String tag = "TacticsList";
    private ViewGroup strategyList;
    private View newStrategyButton;
    private View strategyBackButton;
    private View strategyItemSelectMenuLayout;

    private View editStrategyButton;
    private View deleteStrategyButton;
    private View strategyUpButton;
    private View strategyDownButton;
    private View cancelSelectStrategyButton;

    private LinearLayout strategyContainer;
    private Context mContext;
    private TacticsEdit strategyEdit;
    private Tactics focusStrategy;
    private TacticsDescItem focusStrategyItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tactics_list, container, false);

        strategyList = view.findViewById(R.id.strategyList);
        newStrategyButton = view.findViewById(R.id.newStrategyButton);
        strategyContainer = view.findViewById(R.id.strategyContainer);
        strategyBackButton = view.findViewById(R.id.strategyBackButton);
        strategyItemSelectMenuLayout = view.findViewById(R.id.strategyItemSelectMenuLayout);
        editStrategyButton = view.findViewById(R.id.editStrategyButton);
        deleteStrategyButton = view.findViewById(R.id.deleteStrategyButton);
        strategyUpButton = view.findViewById(R.id.strategyUpButton);
        strategyDownButton = view.findViewById(R.id.strategyDownButton);
        cancelSelectStrategyButton = view.findViewById(R.id.cancelSelectStrategyButton);
//        editStrategyLayout = findViewById(R.id.editStrategyLayout);
        strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
        loadStrategyList();
        strategyList.setVisibility(View.INVISIBLE);
        setListener();
        return view;
    }

    public void showView(boolean show){
        if(show){
            strategyList.setVisibility(View.VISIBLE);
        }else {
            strategyList.setVisibility(View.INVISIBLE);
        }
    }
    private void setListener() {
        strategyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strategyList.setVisibility(View.INVISIBLE);
            }
        });
        newStrategyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tactics strategy = new Tactics(getContext());
                strategyEdit = showEditStrategy(getContext(),  strategyEdit,  strategy);
            }
        });

        editStrategyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strategyEdit = showEditStrategy(getContext(), strategyEdit, focusStrategy);
                strategyEdit.reload( focusStrategy);
            }
        });

        deleteStrategyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameContext.gameContext.getPlayer(getContext()).tacticsList.remove(focusStrategy);
                strategyContainer.removeView(focusStrategyItem);
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
//                BaseActivity.savePlayerData(mContext);
                GameContext.gameContext.savePlayerData(mContext);
            }
        });

        strategyUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  GameContext.gameContext.getPlayer(getContext()).tacticsList.indexOf(focusStrategy);
                if(index > 0){
                    Collections.swap( GameContext.gameContext.getPlayer(getContext()).tacticsList, index, index -1);
                }
                focusStrategy.order = index -1;
                loadStrategyList();
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
                GameContext.gameContext.savePlayerData(mContext);
            }
        });

        strategyDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  GameContext.gameContext.getPlayer(getContext()).tacticsList.indexOf(focusStrategy);
                if(index < ( GameContext.gameContext.getPlayer(getContext()).tacticsList.size()-1)){
                    Collections.swap( GameContext.gameContext.getPlayer(getContext()).tacticsList, index, index +1);
                }
                focusStrategy.order = index +1;
                loadStrategyList();
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
                GameContext.gameContext.savePlayerData(mContext);
            }
        });

        cancelSelectStrategyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private TacticsEdit showEditStrategy(Context context, TacticsEdit stgEdit, Tactics strategy) {
        if(stgEdit == null) {
            stgEdit = new TacticsEdit(context, this, strategy);
            strategyList.addView(stgEdit);
        }
        stgEdit.reload( strategy);
        stgEdit.showView(true);
        return stgEdit;
    }
//    public void reloadNewCard(BaseCard card) {
//        this.card = card;
//        loadStrategyList();
//    }


    public void loadStrategyList() {
        Logger.log(tag, "loadStrategyList");
        strategyContainer.removeAllViews();
        Collections.sort( GameContext.gameContext.getPlayer(getContext()).tacticsList);
        Logger.log(tag, "card.tacticsList.size():"+ GameContext.gameContext.getPlayer(getContext()).tacticsList.size());
        for(int i=0; i< GameContext.gameContext.getPlayer(getContext()).tacticsList.size();i++){
//            final int k = i;
            final TacticsDescItem item = new TacticsDescItem(mContext, GameContext.gameContext.getPlayer(getContext()).tacticsList.get(i), i);
            item.content.setText( GameContext.gameContext.getPlayer(getContext()).tacticsList.get(i).concatStr(getContext()));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Logger.log("card.tacticsList.size:"+card.tacticsList.size());
                    focusStrategy = item.strategy;
                    focusStrategyItem = item;
                    strategyItemSelectMenuLayout.setVisibility(View.VISIBLE);
                }
            });
            strategyContainer.addView(item);
        }
    }

    @Override
    public void save() {
        strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
        loadStrategyList();
    }
}