package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueDownOneDefense extends BaseSkill{
    public static String KEY = "ValueDownOneDefense";
	private int downValue = 5;
    public ValueDownOneDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueDownDefense);
        desc = context.getString(R.string.skill_desc_valueDownDefense).replace("{value}", downValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownDefense);
        statusDesc = context.getString(R.string.skill_statusDesc_valueDownDefense);
    }

	public ActionResult active(Context context,AdvContext advContext){
        int downValue =  level+1;
		return valueDownOne(context, advContext,advContext.battleContext.getRandomMonster(advContext), "defense", downValue);
	}	
}
