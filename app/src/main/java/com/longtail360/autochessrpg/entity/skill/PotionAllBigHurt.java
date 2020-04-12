package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class PotionAllBigHurt extends BaseSkill{
    public static String KEY = "PotionAllBigHurt";
	private int minHurt = 12;
	private int maxHurt = 12;
    private int posGetStatus = 15;
    public PotionAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionAllBigHurt);
        desc = context.getString(R.string.skill_desc_potionAllBigHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_potionAllBigHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_potion);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtAll(context, advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.POTION_STATUS);
	}		
}
