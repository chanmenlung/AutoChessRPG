package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

public class ConnectAllMonster extends BaseSkill{
    public static String KEY = "ConnectAllMonster";

    public ConnectAllMonster(Context context) {
        code = KEY;
        cd = 4;
        name = context.getString(R.string.skill_name_connectAllMonster);
        desc = context.getString(R.string.skill_desc_connectAllMonster);
        battleDesc = context.getString(R.string.skill_battleDesc_connectAllMonster);
        statusDesc = context.getString(R.string.skill_statusDesc_connectAllMonster);
    }

	@Override
	public String getDesc(Context context) {
		return desc;
	}

	public ActionResult active(Context context, AdvContext advContext){
		for(Monster sub : advContext.battleContext.monsters) {
			for(Monster obj : advContext.battleContext.monsters) {
				if(obj != sub){
					if(!sub.connectMonsters.contains(obj)) {
						sub.connectMonsters.add(obj);
					}
				}
			}
		}
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.card.name);
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;		
	}
	
//	@Override
//	public ActionResult doActionOnCardAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
//		ActionResult result = new ActionResult();
//		result.doThisAction = true;
//
//		String labels = advContext.battleContext.buildMonsterLabels(monster.connectMonsters);
//		if(monster.connectMonsters.size() > 0) {
//			for(Monster ma : monster.connectMonsters) {
//				ma.changeHp(context, advContext.battleContext, hurt);
//			}
//		}
//		result.title = name;
//		result.content = statusDesc.replace("{monster}", monster.label).replace("{other}", labels).replace("{value}", hurt+"");
//		return result;
//	}
}
