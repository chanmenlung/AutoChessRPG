package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ZeroHurtOnTeam extends BaseSkill{
    public static String KEY = "ZeroHurtOnTeam";

    public ZeroHurtOnTeam(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_zeroHurtOnTeam);
        desc = context.getString(R.string.skill_desc_zeroHurtOnTeam);
        battleDesc = context.getString(R.string.skill_battleDesc_zeroHurtOnTeam);
        statusDesc = context.getString(R.string.skill_statusDesc_zeroHurtOnTeam);
    }
    @Override
    public String getDesc(Context context) {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){	
		ActionResult result = getActionResultForActive(context);
		result.content = battleDesc.replace ("{card}", mySelf.card.name);
		result.doThisAction = true;	
		for(MyCard ca : advContext.cards) {
			ca.divineShield = true;
		}
		return result;
	}	
}
