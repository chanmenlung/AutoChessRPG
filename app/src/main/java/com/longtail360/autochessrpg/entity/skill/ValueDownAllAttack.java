package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;

public class ValueDownAllAttack extends BaseSkill{
    public static String KEY = "ValueDownAllAttack";
    public ValueDownAllAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueDownAttackAll);
        desc = context.getString(R.string.skill_desc_valueDownAttackAll);
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownAttackAll);
        statusDesc = context.getString(R.string.skill_statusDesc_valueDownAttackAll);
    }
    @Override
    public String getDesc(Context context) {
        int downValue =  2 * level;
        return desc.replace("{value}", downValue+"");
    }
	public ActionResult active(Context context, AdvContext advContext){
        int downValue =  2 * level;
		return valueDownAllMonster(context, advContext, "attack", downValue);
	}	
}
