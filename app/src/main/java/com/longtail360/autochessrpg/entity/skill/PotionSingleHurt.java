package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class PotionSingleHurt extends BaseSkill{
    public static String KEY = "PotionSingleHurt";
	private int minHurt = 20;
	private int maxHurt = 20;
	int posGetStatus = 30;
    public PotionSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionSingleHurt);
        desc = context.getString(R.string.skill_desc_potionSingleHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_potionSingleHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_potion);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.POTION_STATUS);
	}	
}
