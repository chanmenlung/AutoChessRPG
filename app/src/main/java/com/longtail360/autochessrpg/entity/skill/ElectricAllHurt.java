package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class ElectricAllHurt  extends BaseSkill{
    public static String KEY = "ElectricAllHurt";
	private int minHurt = 8;
	private int maxHurt = 12;
	private int posGetStatus = 30;
	
    public ElectricAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricAllHurt);
        desc = context.getString(R.string.skill_desc_electricAllHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_electricAllHurt);
    }
	
	@Override
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtAll(context,advContext,  getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ELECTRICITY_STATUS);
	}	
}
