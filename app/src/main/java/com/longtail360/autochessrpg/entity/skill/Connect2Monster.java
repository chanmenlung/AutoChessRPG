package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

import java.util.List;

public class Connect2Monster extends BaseSkill{
    public static String KEY = "Connect2Monster";

    public Connect2Monster(Context context) {
        code = KEY;
        cd = 4;
        name = context.getString(R.string.skill_name_connect2Monster);
        desc = context.getString(R.string.skill_desc_connect2Monster);
        battleDesc = context.getString(R.string.skill_battleDesc_connect2Monster);
        statusDesc = context.getString(R.string.skill_statusDesc_connect2Monster);
    }

	@Override
	public String getDesc(Context context) {
		return desc;
	}
	
	public ActionResult active(Context context, AdvContext advContext){
		List<Monster> monsters = advContext.battleContext.getRandomMonster(advContext,2);
		Monster m1 = monsters.get(0);
		Monster m2 = monsters.get(1);
		m1.connectMonsters.add(m2);
		m2.connectMonsters.add(m1);
		
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.card.name).replace("{monsterA}", m1.label).replace("{monsterB}", m2.label);
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;
	}
	
	@Override
	public ActionResult doActionOnCardAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		ActionResult result = new ActionResult();
		result.doThisAction = true;
		
		String labels = advContext.battleContext.buildMonsterLabels(monster.connectMonsters);
		if(monster.connectMonsters.size() > 0) {
			for(Monster ma : monster.connectMonsters) {
				ma.changeHp(context, advContext.battleContext,hurt);
			}
		}
		result.title = name;
		result.content = statusDesc.replace("{monster}", monster.label).replace("{other}", labels).replace("{value}", hurt+"");
		return result;
	}	
}
