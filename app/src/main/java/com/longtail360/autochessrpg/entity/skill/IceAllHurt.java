package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class IceAllHurt extends BaseSkill{
    public static String KEY = "IceAllHurt";
	int hurt = 10;
	int posGetStatus = 20;
    public IceAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceAllHurt);
        desc = context.getString(R.string.skill_desc_iceAllHurt).replace("{value}", hurt+"").replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_iceAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceAllHurt);
        getStatusDesc = context.getString(R.string.skill_getStatus_ice);
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
		return statusHurtAll(context,advContext, hurt, false, posGetStatus, MyCard.ICE_STATUS);
	}	
}
