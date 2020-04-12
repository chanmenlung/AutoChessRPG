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
        strategyEdit = new TacticsEdit(getContext(), this, null);
        strategyList.addView(strategyEdit);
//        loadStrategyList();
        setListener();
        return view;
    }

    private void setListener() {
        strategyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() > 0)
                    getFragmentManager().popBackStack();
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
                GameContext.gameContext.player.tacticsList.remove(focusStrategy);
                strategyContainer.removeView(focusStrategyItem);
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
                GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
            }
        });

        strategyUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  GameContext.gameContext.player.tacticsList.indexOf(focusStrategy);
                if(index > 0){
                    Collections.swap( GameContext.gameContext.player.tacticsList, index, index -1);
                }
                focusStrategy.order = index -1;
                loadStrategyList();
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
                GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
            }
        });

        strategyDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index =  GameContext.gameContext.player.tacticsList.indexOf(focusStrategy);
                if(index < ( GameContext.gameContext.player.tacticsList.size()-1)){
                    Collections.swap( GameContext.gameContext.player.tacticsList, index, index +1);
                }
                focusStrategy.order = index +1;
                loadStrategyList();
                strategyItemSelectMenuLayout.setVisibility(View.INVISIBLE);
                GameContext.gameContext.playerDAO.update(GameContext.gameContext.player);
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
        Logger.log(tag, "showEditStrategy");
//        if(stgEdit == null) {
//            Logger.log(tag, "stgEdit is null");
//            stgEdit = new TacticsEdit(context, this, strategy);
//            strategyList.addView(stgEdit);
//        }
        stgEdit.reload( strategy);
        stgEdit.showView(true);
        return stgEdit;
    }
//    public void reloadNewCard(BaseCard card) {
//        this.card = card;
//        loadStrategyList();
//    }

    @Override
    public void onStart() {
        super.onStart();
        loadStrategyList();
    }
    public void loadStrategyList() {
        Logger.log(tag, "loadStrategyList");
        strategyContainer.removeAllViews();
        Collections.sort( GameContext.gameContext.player.tacticsList);
        Logger.log(tag, "card.tacticsList.size():"+ GameContext.gameContext.player.tacticsList.size());
        for(int i=0; i< GameContext.gameContext.player.tacticsList.size();i++){
            final TacticsDescItem item = new TacticsDescItem(getContext(), GameContext.gameContext.player.tacticsList.get(i), i);
            item.setText( GameContext.gameContext.player.tacticsList.get(i).concatStr(getContext()));
            item.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
