package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ZeroHurtOnMyself extends BaseSkill{
    public static String KEY = "ZeroHurtOnMyself";
    public ZeroHurtOnMyself(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_zeroHurtOnMyself);
        desc = context.getString(R.string.skill_desc_zeroHurtOnMyself);
        battleDesc = context.getString(R.string.skill_battleDesc_zeroHurtOnMyself);
        statusDesc = context.getString(R.string.skill_statusDesc_zeroHurtOnMyself);
    }
	public ActionResult active(Context context,AdvContext advContext){	
		ActionResult result = getActionResultForActive(context);
		result.content = battleDesc.replace ("{mySelf}", mySelf.getCard(context).name);
		result.doThisAction = true;	
		mySelf.divineShield = true;
		advContext.battleContext.addActionResultToLog(result);
		return result;
	}
	
}
