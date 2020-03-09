package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class PotionSingleHurt extends BaseSkill{
    public static String KEY = "PotionSingleHurt";
	int hurt = 10;
	int posGetStatus = 10;
    public PotionSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionSingleHurt);
        desc = context.getString(R.string.skill_desc_potionSingleHurt).replace("{value}", hurt+"").replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_potionSingleHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_potion);
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
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, hurt, false, posGetStatus, MyCard.POTION_STATUS);
	}	
}
