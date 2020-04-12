package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class IceAllBigHurt extends BaseSkill{
    public static String KEY = "IceAllBigHurt";
	private int minHurt = 10;
	private int maxHurt = 15;
    private int posGetStatus = 15;
    public IceAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceAllBigHurt);
        desc = context.getString(R.string.skill_desc_iceAllBigHurt).replace("{value}", minHurt+"-"+maxHurt).replace("{pos}", posGetStatus+"");
        battleDesc = context.getString(R.string.skill_battleDesc_iceAllBigHurt);
    }
	
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context,advContext, getIntByRange(minHurt, maxHurt), false,posGetStatus, MyCard.ICE_STATUS);
	}	
}
