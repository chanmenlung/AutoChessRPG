package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueDownOneAttack extends BaseSkill{
    public static String KEY = "ValueDownOneAttack";
	private int downValue = 5;
    public ValueDownOneAttack(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_valueDownAttack);
        desc = context.getString(R.string.skill_desc_valueDownAttack).replace("{value}", downValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueDownAttack);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return valueDownOne(context, advContext,advContext.battleContext.getRandomMonster(advContext), "attack", downValue);
	}	
}
