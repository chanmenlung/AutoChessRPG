package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitAllHurt extends BaseSkill{
    public static String KEY = "HitAllHurt";
	private int hurt = 40;
    public HitAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitAllHurt);
        desc = context.getString(R.string.skill_desc_hitAllHurt).replace("{value}", hurt+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllHurt).replace("{value}", hurt+"");
        statusDesc = context.getString(R.string.skill_statusDesc_hitAllHurt);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
		int value = hurt-advContext.battleContext.monsters.get(0).defense;
		return valueDownAllMonster(context, advContext, "hp", value <=0 ?1:value);
	}	
}
