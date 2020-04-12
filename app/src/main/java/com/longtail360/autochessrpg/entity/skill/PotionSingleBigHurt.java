package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class PotionSingleBigHurt extends BaseSkill{
    public static String KEY = "PotionSingleBigHurt";
	private int minHurt = 24;
	private int maxHurt = 24;
	int posGetStatus = 15;
    public PotionSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionSingleBigHurt);
        desc = context.getString(R.string.skill_desc_potionSingleBigHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_potionSingleBigHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_potion);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.POTION_STATUS);
	}	
}
