package com.longtail360.autochessrpg.prefab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.entity.tactic.condition.AllManHp;
import com.longtail360.autochessrpg.entity.tactic.condition.BaseCondition;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class HelloPrefab extends FrameLayout {
    private String tag = "HelloPrefab";
    private View editStrategyLayout;
    private ViewGroup conditionContainer;
    private View cancelNewStrategyButton;
    private View newCondButton;
    private View saveStrategyButton;
    private ViewGroup actionLayoutContainer;
    private Tactics strategy;
    private List<CheckBoxPrefab> conditionUis = new ArrayList<>();
    private ActionUi actionUi;
    public HelloPrefab(Context context, TacticsEdit.CallBack callBack, Tactics strategy) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tactics_edit, this);
        editStrategyLayout = findViewById(R.id.editStrategyLayout);
        saveStrategyButton = findViewById(R.id.saveStrategyButton);
        newCondButton = findViewById(R.id.newCondButton);
        actionLayoutContainer = (ViewGroup)findViewById(R.id.actionLayoutContainer);
        conditionContainer = (ViewGroup)findViewById(R.id.conditionContainer);
        if(strategy == null) {
            strategy = new Tactics(context);
        }
        this.strategy = strategy;
        showView(false);
        actionUi = new ActionUi(getContext(), strategy.action);
        actionLayoutContainer.addView(actionUi);
        reload(strategy);
        setListener();


    }

    private void setListener() {
        saveStrategyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                strategy.conditions.clear();
                for(CheckBoxPrefab conditionUi : conditionUis) {
                    strategy.conditions.add(conditionUi.selectedCondition);
                }
                if(!GameContext.gameContext.getPlayer(getContext()).tacticsList.contains(strategy)){
                    GameContext.gameContext.getPlayer(getContext()).tacticsList.add(strategy);
                }
                strategy.action = actionUi.selectedAction;
                strategy.action.convertParaToObject(getContext());
                showView(false);
                GameContext.gameContext.savePlayerData(getContext());
                Logger.log(tag, "tactics-size:"+GameContext.gameContext.getPlayer(getContext()).tacticsList.size());
            }
        });

        newCondButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseCondition condition = BaseCondition.create(getContext(), AllManHp.KEY);
                CheckBoxPrefab condUi = new CheckBoxPrefab(getContext(), conditionContainer,null, strategy,condition);
                condUi.showOperator(true);
                condUi.showDelete(true);
                strategy.conditions.add(condition);
                conditionUis.add(condUi);
                conditionContainer.addView(condUi);
            }
        });
    }

    public void showView(boolean show) {
        if(show) {
            Logger.log(tag, "show-editStrategyLayout");
            editStrategyLayout.setVisibility(VISIBLE);
        }else {
            Logger.log(tag, "hide-editStrategyLayout");
            editStrategyLayout.setVisibility(INVISIBLE);
        }
    }

    public void reload(Tactics strategy) {
        this.strategy = strategy;
        conditionUis.clear();
        conditionContainer.removeAllViews();
        if(strategy.conditions.size() > 0) {
            CheckBoxPrefab condUi0 = new CheckBoxPrefab(getContext(),conditionContainer, null, strategy,strategy.conditions.get(0));
            condUi0.showOperator(false);
            condUi0.showDelete(false);
            conditionContainer.addView(condUi0);
            conditionUis.add(condUi0);
            for(int i=1; i<strategy.conditions.size(); i++) {
                CheckBoxPrefab condUi = new CheckBoxPrefab(getContext(), conditionContainer, null, strategy,strategy.conditions.get(i));
                condUi.showOperator(true);
                conditionContainer.addView(condUi);
                conditionUis.add(condUi);
            }
        }
        actionUi.reload(strategy.action);
    }

    public void callBackFromDeleteCond(ConditionUi conditionUi) {
        this.conditionUis.remove(conditionUi);
    }

    public interface CallBack {
        void save();
    }
}
