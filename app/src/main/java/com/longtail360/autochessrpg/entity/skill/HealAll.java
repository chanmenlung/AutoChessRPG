package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealAll extends BaseSkill{
    public static String KEY = "HealAll";
	private int minHeal = 8;
	private int maxHeal = 12;
    public HealAll(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_healAll);
        desc = context.getString(R.string.skill_desc_healAll).replace("{value}", minHeal+"-"+maxHeal);
        battleDesc = context.getString(R.string.skill_battleDesc_healAll);
    }
	
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		return valueUpTeam(context, advContext, "hp", getIntByRange(minHeal, maxHeal));
	}
}
