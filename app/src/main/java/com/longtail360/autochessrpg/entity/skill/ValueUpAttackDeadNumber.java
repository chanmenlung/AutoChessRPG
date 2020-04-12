package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpAttackDeadNumber extends BaseSkill{
    public static String KEY = "ValueUpAttackDeadNumber";
    private int deltaValue = 1;
    public ValueUpAttackDeadNumber(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAttackDeadNumber);
        desc = context.getString(R.string.skill_desc_valueUpAttackDeadNumber).replace("{value}", deltaValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAttackDeadNumber);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAttackDeadNumber);
    }

    @Override
    public ActionResult active(Context context,AdvContext advContext){
        int upValue = advContext.battleContext.deadMonsters.size()+deltaValue;
        return valueUpOne(context,advContext, mySelf,"attack", upValue);
    }
}
