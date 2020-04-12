package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class IceSingleBigHurt extends BaseSkill{
    public static String KEY = "IceSingleBigHurt";
	private int minHurt = 25;
	private int maxHurt = 30;
    private int posGetStatus = 20;
    public IceSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceSingleBigHurt);
        desc = context.getString(R.string.skill_desc_iceSingleBigHurt).replace("{value}",  minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_iceSingleBigHurt);
    }

	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false, posGetStatus, MyCard.ICE_STATUS);
	}	
}
