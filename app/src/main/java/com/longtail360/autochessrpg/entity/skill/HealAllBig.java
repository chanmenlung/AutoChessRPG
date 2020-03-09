package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealAllBig extends BaseSkill{
    public static String KEY = "HealAllBig";
	private int heal = 30;
    public HealAllBig(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healAllBig);
        desc = context.getString(R.string.skill_desc_healAllBig).replace("{value}", heal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_healAllBig).replace("{value}", heal+"");
        statusDesc = context.getString(R.string.skill_statusDesc_healAllBig);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult result = valueUpTeam(context,advContext, "hp", heal);
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}	
}
