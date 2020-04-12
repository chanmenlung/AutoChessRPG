package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpDefenseTurnNumber extends BaseSkill{
    public static String KEY = "ValueUpDefenseTurnNumber";
	private int upValue = 1;
    public ValueUpDefenseTurnNumber(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpDefenseTurnNumber);
        desc = context.getString(R.string.skill_desc_valueUpDefenseTurnNumber).replace("{value}", upValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpDefenseTurnNumber);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpDefenseTurnNumber);
    }
    @Override
	public ActionResult active(Context context,AdvContext advContext){
        int deltaDefense = 1;
        if(advContext.battleContext.turnNumber >= 5) {
            deltaDefense = 2;
        }

        return valueUpOne( context,advContext,mySelf, "defense", deltaDefense);
	}	
}
