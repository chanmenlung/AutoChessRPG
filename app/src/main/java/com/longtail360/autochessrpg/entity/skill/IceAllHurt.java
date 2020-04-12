package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class IceAllHurt extends BaseSkill{
    public static String KEY = "IceAllHurt";
	private int minHurt = 8;
	private int maxHurt = 12;
	int posGetStatus = 30;
    public IceAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceAllHurt);
        desc = context.getString(R.string.skill_desc_iceAllHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_iceAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceAllHurt);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ICE_STATUS);
	}	
}
