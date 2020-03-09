package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class TauntAndDodgeAttackUp extends BaseSkill{
    public static String KEY = "TauntAndDodgeAttackUp";
    public TauntAndDodgeAttackUp(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_tauntAndDodgeAttack);
        desc = context.getString(R.string.skill_desc_tauntAndDodgeAttack);
        battleDesc = context.getString(R.string.skill_battleDesc_tauntAndDodgeAttack);
    }
	@Override
	public String getDesc(Context context) {
		int deltaAgi = 5 * level;
		return desc.replace("{agi}", deltaAgi+"");
	}
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		int deltaAgi = 5 * level;
		mySelf.agi = mySelf.agi + deltaAgi;
		mySelf.taunt = true;
		ActionResult result = getActionResultForActive(context);
		result.content = battleDesc.replace("{value}", deltaAgi+"").replace("{mySelf}", mySelf.card.name+"");
		advContext.battleContext.addActionResultToLog(result);
		return result;
	}	

}
