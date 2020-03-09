package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class ReflectAttackOnMyself extends BaseSkill{
    public static String KEY = "ReflectAttackOnMyself";
	private boolean isActive;
    public ReflectAttackOnMyself(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_reflectAttackOnMyself);
        desc = context.getString(R.string.skill_desc_reflectAttackOnMyself);
        battleDesc = context.getString(R.string.skill_battleDesc_reflectAttackOnMyself);
    }

    @Override
    public String getDesc(Context context) {
        return desc;
    }
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.card.name);
		mySelf.reflectShield = true;
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;
	}
}
