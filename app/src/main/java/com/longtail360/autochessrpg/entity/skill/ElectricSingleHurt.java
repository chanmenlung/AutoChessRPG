package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class ElectricSingleHurt extends BaseSkill{
    public static String KEY = "ElectricSingleHurt";
	
	private int minHurt = 24;
	private int maxHurt = 28;
	private int posGetStatus = 30;
	
    public ElectricSingleHurt(Context context) {
        code = KEY;
        cd = 2;
        name = context.getString(R.string.skill_name_electricSingleHurt);
        desc = context.getString(R.string.skill_desc_electricSingleHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");;
        battleDesc = context.getString(R.string.skill_battleDesc_electricSingleHurt);
    }
	
	@Override
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ELECTRICITY_STATUS);
	}	
}
