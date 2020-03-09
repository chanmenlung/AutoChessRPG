package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireAllHurt extends BaseSkill{
    public static String KEY = "FireAllHurt";

	private int hurt = 20;
	private int fireHurtValue = 2;
	private int posGetStatus = 99;
	
    public FireAllHurt(Context context) {
        code = KEY;
        cd = 2;
        name = context.getString(R.string.skill_name_fireAllHurt);
        desc = context.getString(R.string.skill_desc_fireAllHurt).replace("{value}", hurt+"").replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireAllHurt).replace("{value}", fireHurtValue+"");
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
		return statusHurtAll(context,advContext, hurt, false, posGetStatus, MyCard.FIRE_STATUS);
	}


}
