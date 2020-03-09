package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class RandomAttackOfMonster extends BaseSkill{
    public static String KEY = "RandomAttackOfMonster";
	private boolean isActive = false;
    public RandomAttackOfMonster(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_randomAttackOfMonster);
        desc = context.getString(R.string.skill_desc_randomAttackOfMonster);
        battleDesc = context.getString(R.string.skill_battleDesc_randomAttackOfMonster);
        statusDesc = context.getString(R.string.skill_statusDesc_randomAttackOfMonster);
    }

	@Override
	public String getDesc(Context context) {
		return desc;
	}

	@Override
	public ActionResult active(Context context,AdvContext advContext){
    	int hurt = advContext.battleContext.monsters.get(0).attack;
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.card.name)
								.replace("{monster}", advContext.battleContext.buildMonsterLabels(advContext.battleContext.monsters))
								.replace("{value}", hurt+"");
		for(Monster ma : advContext.battleContext.monsters) {
			ma.skipAttack = true;
		}
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;
	}


}
