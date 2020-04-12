package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitPercentage extends BaseSkill{
    public static String KEY = "HitPercentage";
	private int percentage =30;
    public HitPercentage(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitPercentage);
        desc = context.getString(R.string.skill_desc_hitPercentage).replace("{value}", percentage+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitPercentage);
        statusDesc = context.getString(R.string.skill_statusDesc_hitPercentage);
    }
    @Override
	public ActionResult active(Context context,AdvContext advContext){
		Monster monster = advContext.battleContext.getRandomMonster(advContext);
		int hurt = monster.getHp() * percentage / 100;
		return statusHurtSingle(context, advContext, hurt,false,0,MyCard.NONE_STATUS);
	}		
}
