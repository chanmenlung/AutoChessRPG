package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueDownOneAttack extends BaseSkill{
    public static String KEY = "ValueDownOneAttack";
    public ValueDownOneAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueDownAttack);
        desc = context.getString(R.string.skill_desc_valueDownAttack);
        battleDesc = context.getString(R.string.skill_battleDesc_valueDownAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueDownAttack);
    }
    @Override
    public String getDesc(Context context) {
        int downValue =  2+2 * level;
        return desc.replace("{value}", downValue+"");
    }
	public ActionResult active(Context context,AdvContext advContext){
        int downValue =  2+2 * level;
		return valueDownOne(context, advContext,advContext.battleContext.getRandomMonster(advContext), "attack", downValue);
	}	
}
