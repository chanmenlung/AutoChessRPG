package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class PotionAllHurt extends BaseSkill{
    public static String KEY = "PotionAllHurt";
	private int minHurt = 10;
	private int maxHurt = 10;
	private int posGetStatus = 30;
    public PotionAllHurt(Context context) {
        code = KEY;
        cd = 2;
        name = context.getString(R.string.skill_name_potionAllHurt);
        desc = context.getString(R.string.skill_desc_potionAllHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_potionAllHurt);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.POTION_STATUS);
	}

}
