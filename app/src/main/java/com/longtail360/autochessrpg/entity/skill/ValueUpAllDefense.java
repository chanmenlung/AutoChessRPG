package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpAllDefense extends BaseSkill{
    public static String KEY = "ValueUpAllDefense";
	private int upValue = 1;
    public ValueUpAllDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAllDefense);
        desc = context.getString(R.string.skill_desc_valueUpAllDefense).replace("{value}", upValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAllDefense);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAllDefense);
    }

	public ActionResult active(Context context,AdvContext advContext){	
		return valueUpTeam(context,advContext, "defense", upValue);
	}	
}
