package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpAllAttack extends BaseSkill{
    public static String KEY = "ValueUpAllAttack";
	private int upValue = 3;
    public ValueUpAllAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAllAttack);
        desc = context.getString(R.string.skill_desc_valueUpAllAttack).replace("{value}", upValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAllAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAllAttack);
    }
    @Override
    public String getDesc(Context context) {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){	
		return valueUpTeam(context,advContext, "attack", upValue);
	}	
}
