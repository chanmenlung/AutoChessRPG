package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.entity.tactic.action.BaseAction;
import com.longtail360.autochessrpg.entity.tactic.condition.BaseCondition;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String tag = "Player";
    @Expose
    public int crystal;
    @Expose
    public List<String> unlockCards = new ArrayList<>();
    @Expose
    public List<Tactics> tacticsList = new ArrayList<> ();
    @Expose
    public boolean isOldPlayer;



    public void concreteAction(Context context) {

        for (int i = 0; i < tacticsList.size(); i++) {
            Tactics stg = tacticsList.get(i);
            if (stg.action != null) {
                Logger.log(tag,"action.key1:"+stg.action.key);
                BaseAction action = BaseAction.create(context, stg.action.key);
                if(action.optionItems2 != null){
                    for(OptionItem item : action.optionItems2){
                        Logger.log(tag,"stg.action.selectOption2.value:"+stg.action.selectOption2.value);
                        if(item.value.equals(stg.action.selectOption2.value)){
                            stg.action.selectOption2.optionItems = item.optionItems;
                            stg.action.selectOption2.optionItemTitle = item.optionItemTitle;
                        }
                    }
                }
                action.selectOption2 = stg.action.selectOption2;
                action.selectOption3 = stg.action.selectOption3;
                action.selectOption2_1 = stg.action.selectOption2_1;
//                if (stg.action.selectTargetPara != null) {
//                    BaseTarget anewTarget = BaseTarget.create (this, stg.action.selectTargetPara.key);
//                    if (anewTarget != null) {
//                        anewTarget.selectIntPara = stg.action.selectTargetPara.selectIntPara;
//                        anewTarget.selectStringPara = stg.action.selectTargetPara.selectStringPara;
//                        anew.selectTargetPara = anewTarget;
//                    }
//                }

//                Logger.log("action.selectOption2.value:"+stg.action.selectOption2.value);
//                Logger.log("action.selectOption3:"+stg.action.selectOption3);
                action.convertParaToObject(context);
                stg.action = action;
            }
        }

    }

    public void concreteConds(Context context) {
        Logger.log(tag, tacticsList.size()+"");
        for (int i = 0; i < tacticsList.size(); i++) {
            Tactics stg = tacticsList.get(i);
            List<BaseCondition> result = new ArrayList<> ();
            if (stg.conditions != null) {
                for(int j=0; j<stg.conditions.size(); j++) {
                    BaseCondition old = stg.conditions.get(j);
                    BaseCondition anew = BaseCondition.create (context,old.key);
                    anew.selectOption2 = old.selectOption2;
                    anew.selectOption3 = old.selectOption3;
                    anew.operatorType = old.operatorType;
                    anew.negation = old.negation;
                    result.add (anew);
                }
            }
            stg.conditions = result;
        }

    }
}
