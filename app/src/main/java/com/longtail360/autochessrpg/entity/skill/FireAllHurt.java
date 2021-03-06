package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireAllHurt extends BaseSkill{
    public static String KEY = "FireAllHurt";

	private int minHurt = 8;
	private int maxHurt = 12;
	private int posGetStatus = 30;
	
    public FireAllHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_fireAllHurt);
        desc = context.getString(R.string.skill_desc_fireAllHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllHurt);
    }
	
	@Override
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtAll(context,advContext,  getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.FIRE_STATUS);
	}


}
