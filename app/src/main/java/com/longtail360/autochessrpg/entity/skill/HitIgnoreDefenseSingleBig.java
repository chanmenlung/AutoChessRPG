package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitIgnoreDefenseSingleBig extends BaseSkill{
    public static String KEY = "HitIgnoreDefenseSingleBig";
	private int minHurt = 15;
	private int maxHurt = 25;
    public HitIgnoreDefenseSingleBig(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitIgnoreDefenseSingleBig);
        desc = context.getString(R.string.skill_desc_hitIgnoreDefenseSingleBig).replace("{value}", minHurt+"-"+maxHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitIgnoreDefenseSingleBig);
        statusDesc = context.getString(R.string.skill_statusDesc_hitIgnoreDefenseSingleBig);
    }

    @Override
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context, advContext,  getIntByRange(minHurt, maxHurt), true, 0,MyCard.NONE_STATUS);
	}	
}
