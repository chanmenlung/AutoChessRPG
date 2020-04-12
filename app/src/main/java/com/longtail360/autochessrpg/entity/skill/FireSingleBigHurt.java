package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireSingleBigHurt extends BaseSkill{
    public static String KEY = "FireSingleBigHurt";

	private int minHurt = 25;
	private int maxHurt = 30;
	private int posGetStatus = 15;
	
    public FireSingleBigHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_fireSingleBigHurt);
        desc = context.getString(R.string.skill_desc_fireSingleBigHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireSingleBigHurt);
    }
	
	@Override
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.FIRE_STATUS);
	}	
}
