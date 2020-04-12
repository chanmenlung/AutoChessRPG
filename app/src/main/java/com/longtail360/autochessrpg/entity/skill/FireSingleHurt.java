package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class FireSingleHurt extends BaseSkill{
    public static String KEY = "FireSingleHurt";

	private int minHurt = 24;
	private int maxHurt = 28;
	private int posGetStatus = 30;
	
    public FireSingleHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_fireSingleHurt);
        desc = context.getString(R.string.skill_desc_fireSingleHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireSingleHurt);
    }
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.FIRE_STATUS);
	}	
}
