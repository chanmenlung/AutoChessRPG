package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;
import com.longtail360.autochessrpg.entity.tactic.action.BaseAction;

public class OnBattleStart extends BaseCondition{
    public static String KEY = "OnBattleStart";
    public OnBattleStart (Context context){
        this.key = KEY;
        desc = context.getString(R.string.condition_onBattleStart);
    }

    @Override
    public String concatDesc (){
        return desc;
    }

    @Override
    public boolean checking (AdvContext advContext){
        return false;
    }
}
