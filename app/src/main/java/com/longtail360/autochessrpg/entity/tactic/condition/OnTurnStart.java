package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;

public class OnTurnStart  extends BaseCondition{
    public static String KEY = "OnTurnStart";
    public OnTurnStart (Context context){
        this.key = KEY;
        desc = context.getString(R.string.condition_onTurnStart);
    }

    @Override
    public String concatDesc (){
        return desc;
    }

    @Override
    public boolean checking (AdvContext advContext){
        return true;
    }
}
