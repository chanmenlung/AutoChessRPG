package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitSingleBigHurt extends BaseSkill{
    public static String KEY = "HitSingleBigHurt";
	private int minHurt = 30;
	private int maxHurt = 40;
    public HitSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitSingleBigHurt);
        desc = context.getString(R.string.skill_desc_hitSingleBigHurt).replace("{value}",  minHurt+"-"+maxHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitSingleBigHurt);
    }

    @Override
	public ActionResult active(Context context,AdvContext advContext){
		Monster monster = advContext.battleContext.getRandomMonster(advContext);
		int realHurt = getIntByRange(minHurt, maxHurt) - monster.defense;
		return valueDownOne(context, advContext, monster, "hp", realHurt);
	}	
}
