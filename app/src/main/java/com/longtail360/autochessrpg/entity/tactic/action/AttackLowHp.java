package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;


public class AttackLowHp extends BaseAction{
    public static String KEY = "AttackLowHp";
    public static int deltaAttack = 5;
    public AttackLowHp (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_attackLowHp);
    }


    public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        advContext.battleContext.attackLowHp = true;
        return null;
    }
}
