package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;

public class ValueDownAllDefense extends BaseSkill{
    public static String KEY = "ValueDownAllDefense";
	private int downValue = 1;
    public ValueDownAllDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueDownDefenseAll);
        desc = context.getString(R.string.skill_desc_valueDownDefenseAll).replace("{value}", downValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownDefenseAll);
        statusDesc = context.getString(R.string.skill_statusDesc_valueDownDefenseAll);
    }

	public ActionResult active(Context context, AdvContext advContext){
		return valueDownAllMonster(context, advContext, "defense", downValue);
	}	
}
