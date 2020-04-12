package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpDefenseDeadNumber extends BaseSkill{
    public static String KEY = "ValueUpDefenseDeadNumber";
    public ValueUpDefenseDeadNumber(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_valueUpDefenseDeadNumber);
        desc = context.getString(R.string.skill_desc_valueUpDefenseDeadNumber);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpDefenseDeadNumber);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpDefenseDeadNumber);
    }

	@Override
	public ActionResult active(Context context,AdvContext advContext){
        int upValue = advContext.battleContext.deadMonsters.size()+1;
		return valueUpOne(context, advContext,mySelf, "defense", upValue);
	}	
}
