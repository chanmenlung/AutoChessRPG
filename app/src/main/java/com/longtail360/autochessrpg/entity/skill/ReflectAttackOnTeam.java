package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class ReflectAttackOnTeam extends BaseSkill{
    public static String KEY = "ReflectAttackOnTeam";

    public ReflectAttackOnTeam(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_reflectAttackOnTeam);
        desc = context.getString(R.string.skill_desc_reflectAttackOnTeam);
        battleDesc = context.getString(R.string.skill_battleDesc_reflectAttackOnTeam);
    }

	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.getCard(context).name);
		for(MyCard ca : advContext.cards) {
			ca.reflectShield = true;
		}
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;
	}
	
//	@Override
//	public ActionResult doActionOnMonsterAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
//		ActionResult result = new ActionResult();
//		if(card.reflectShield) {
//			int realHurt = hurt - monster.defense;
//			monster.changeHp(context, advContext.battleContext, realHurt);
//			result.doThisAction = true;
//			result.title = name;
//			result.content = statusDesc.replace("{card}", mySelf.card.name).replace("monster", monster.label).replace("{value}", realHurt+"");
//		}else {
//			result.doThisAction = false;
//		}
//		return result;
//
//	}
}
