package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitAllBigHurt extends BaseSkill{
    public static String KEY = "HitAllBigHurt";
	private int minHurt = 15;
	private int maxHurt = 18;
    public HitAllBigHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_hitAllBigHurt);
        desc = context.getString(R.string.skill_desc_hitAllBigHurt).replace("{value}",  minHurt+"-"+maxHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitAllBigHurt);
    }
	
    @Override
	public ActionResult active(Context context,AdvContext advContext){
        return statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, 0, MyCard.NONE_STATUS);
	}	
}
