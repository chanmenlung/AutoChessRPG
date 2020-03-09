package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpAttackTurnNumber extends BaseSkill{
    public static String KEY = "ValueUpAttackTurnNumber";

    public ValueUpAttackTurnNumber(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAttackTurnNumber);
        desc = context.getString(R.string.skill_desc_valueUpAttackTurnNumber);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAttackTurnNumber);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAttackTurnNumber);
    }
    @Override
    public String getDesc(Context context) {
        return null;
    }
    public ActionResult active(Context context,AdvContext advContext){
        int deltaAttack = 2;
        if(advContext.battleContext.turnNumber >= 5) {
            deltaAttack = 3;
        }

        return valueUpOne( context,advContext,mySelf, "attack", deltaAttack);
    }
}
