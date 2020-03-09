package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireAllBigHurt extends BaseSkill{
    public static String KEY = "FireAllBigHurt";

	private int hurt = 25;
	private int posGetStatus = 30;
	
    public FireAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_fireAllBigHurt);
        desc = context.getString(R.string.skill_desc_fireAllBigHurt).replace("{value}", hurt+"").replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireAllBigHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_fire);
    }

    @Override
    public String getDesc(Context context) {
        int levelHurt;
        if(level == 1) {
            levelHurt = 25;
        }else if(level == 2) {
            levelHurt = 40;
        }else {
            levelHurt = 60;
        }

        return context.getString(R.string.skill_desc_electricAllBigHurt).replace("{value}", levelHurt+"")
                .replace("{pos}", posGetStatus+"");
    }
	public ActionResult active(Context context, AdvContext advContext){
		return statusHurtAll(context, advContext, hurt, false, posGetStatus, MyCard.FIRE_STATUS);
	}	
}
