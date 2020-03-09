package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitSingleHurt extends BaseSkill{
    public static String KEY = "HitSingleHurt";
	int hurt =30;
    public HitSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitSingleHurt);
        desc = context.getString(R.string.skill_desc_hitSingleHurt).replace("{value}", hurt+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitSingleHurt);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){	
		Monster monster = advContext.battleContext.getRandomMonster(advContext);
		int realHurt = hurt - monster.defense;
		return valueDownOne(context, advContext,monster, "hp", realHurt);
	}	
}
