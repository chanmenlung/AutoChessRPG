package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;

public class ValueDownAllAttack extends BaseSkill{
    public static String KEY = "ValueDownAllAttack";
	private int downValue = 3;
    public ValueDownAllAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueDownAttackAll);
        desc = context.getString(R.string.skill_desc_valueDownAttackAll).replace("{value}", downValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownAttackAll);
    }
	
	public ActionResult active(Context context, AdvContext advContext){
		return valueDownAllMonster(context, advContext, "attack", downValue);
	}	
}
