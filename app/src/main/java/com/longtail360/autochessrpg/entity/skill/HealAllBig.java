package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealAllBig extends BaseSkill{
    public static String KEY = "HealAllBig";
	private int minHeal = 10;
	private int maxHeal = 15;
    public HealAllBig(Context context) {
        code = KEY;
		cd = 5;
        name = context.getString(R.string.skill_name_healAllBig);
        desc = context.getString(R.string.skill_desc_healAllBig).replace("{value}",  minHeal+"-"+maxHeal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_healAllBig);
        statusDesc = context.getString(R.string.skill_statusDesc_healAllBig);
    }
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult result = valueUpTeam(context,advContext, "hp", getIntByRange(minHeal, maxHeal));
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}	
}
