package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class IceSingleHurt extends BaseSkill{
    public static String KEY = "IceSingleHurt";
	private int minHurt = 24;
	private int maxHurt = 28;
    private int posGetStatus = 30;
    public IceSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceSingleHurt);
        desc = context.getString(R.string.skill_desc_iceSingleHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_iceSingleHurt);
    }
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ICE_STATUS);
	}	
}
