package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpOneAttack extends BaseSkill{
    public static String KEY = "ValueUpOneAttack";
	private int upValue = 3;
    public ValueUpOneAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAttack);
        desc = context.getString(R.string.skill_desc_valueUpAttack).replace("{value}", upValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAttack);
    }
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){	
		return valueUpOne(context,advContext,mySelf, "attack", upValue);
	}	
}
