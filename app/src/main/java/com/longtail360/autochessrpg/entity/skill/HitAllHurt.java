package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitAllHurt extends BaseSkill{
    public static String KEY = "HitAllHurt";
	private int minHurt = 12;
	private int maxHurt = 16;
    public HitAllHurt(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_hitAllHurt);
        desc = context.getString(R.string.skill_desc_hitAllHurt).replace("{value}",  minHurt+"-"+maxHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllHurt);
    }
    @Override
	public ActionResult active(Context context,AdvContext advContext){
        return statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, 0, MyCard.NONE_STATUS);
	}	
}
