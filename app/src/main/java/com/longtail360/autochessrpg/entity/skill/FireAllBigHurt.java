package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireAllBigHurt extends BaseSkill{
    public static String KEY = "FireAllBigHurt";

	private int minHurt = 10;
	private int maxHurt = 15;
	private int posGetStatus = 15;
	
    public FireAllBigHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_fireAllBigHurt);
        desc = context.getString(R.string.skill_desc_fireAllBigHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllBigHurt);
    }
	
	@Override
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtAll(context, advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.FIRE_STATUS);
	}	
}
