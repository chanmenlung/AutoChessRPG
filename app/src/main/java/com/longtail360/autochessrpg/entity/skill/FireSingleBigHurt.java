package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;

public class FireSingleBigHurt extends BaseSkill{
    public static String KEY = "FireSingleBigHurt";

	private int hurt = 30;
	private int fireHurtValue = 2;
	private int posGetStatus = 30;
	
    public FireSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_fireSingleBigHurt);
        desc = context.getString(R.string.skill_desc_fireSingleBigHurt).replace("{value}", hurt+"").replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_fireSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireSingleBigHurt).replace("{value}", fireHurtValue+"");
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
		return statusHurtSingle(context,advContext, hurt, false, posGetStatus, MyCard.FIRE_STATUS);
	}	
}
