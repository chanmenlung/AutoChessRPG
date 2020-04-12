package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealSingleBig extends BaseSkill{
    public static String KEY = "HealSingleBig";
	private int minHeal = 28;
	private int maxHeal = 32;
    public HealSingleBig(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_healSingle);
        desc = context.getString(R.string.skill_desc_healSingle).replace("{value}",  minHeal+"-"+maxHeal);
        battleDesc = context.getString(R.string.skill_battleDesc_healSingle);
    }
    @Override
	public ActionResult active(Context context,AdvContext advContext){
        MyCard target = mySelf.findLowestHp(advContext);
		return valueUpOne(context,advContext, target,"hp", getIntByRange(minHeal, maxHeal));
	}	
}
