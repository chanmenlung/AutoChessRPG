package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;

public class ValueUpOneDefense extends BaseSkill{
    public static String KEY = "ValueUpDefense";
	private int upValue = 1;
    public ValueUpOneDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpDefense);
        desc = context.getString(R.string.skill_desc_valueUpDefense).replace("{value}", upValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpDefense);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpDefense);
    }
    @Override
	public ActionResult active(Context context, AdvContext advContext){
		return valueUpOne(context, advContext,mySelf,"defense", upValue);
	}	
}
