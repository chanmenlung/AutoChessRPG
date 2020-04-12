package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class ElectricAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";
	private int minHurt = 10;
	private int maxHurt = 15;
	private int posGetStatus = 15;
    public ElectricAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricAllBigHurt);
        desc = context.getString(R.string.skill_desc_electricAllBigHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_electricAllBigHurt);
    }

    @Override
	public ActionResult active(Context context, AdvContext advContext){
		ActionResult result = statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ELECTRICITY_STATUS);
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}
}
