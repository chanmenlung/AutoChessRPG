package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

import java.util.ArrayList;
import java.util.List;

public class ConnectMyselfAndAllMonster extends BaseSkill{
    public static String KEY = "ConnectMyselfAndAllMonster";

    public ConnectMyselfAndAllMonster(Context context) {
        code = KEY;
        cd = 4;
        name = context.getString(R.string.skill_name_connectMyselfAndAllMonster);
        desc = context.getString(R.string.skill_desc_connectMyselfAndAllMonster);
        battleDesc = context.getString(R.string.skill_battleDesc_connectMyselfAndAllMonster);
        statusDesc = context.getString(R.string.skill_statusDesc_connectMyselfAndAllMonster);
    }


	@Override
	public ActionResult active(Context context, AdvContext advContext){
		mySelf.connectMonsters.addAll(advContext.battleContext.monsters);
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{mySelf}", mySelf.getCard(context).name);
		advContext.battleContext.addActionResultToLog(actionResult);
		return actionResult;
	}

	@Override
	public ActionResult doActionOnMonsterAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		if(card != mySelf){
			return null;
		}
		List<Monster> lifeMonster = new ArrayList<>();
		for(Monster cmonster : card.connectMonsters){
			if(cmonster.getHp() > 0) {
				if(cmonster.getHp() > 0) {
					lifeMonster.add(cmonster);
				}
			}
		}
		card.connectMonsters = lifeMonster;
		if(card.connectMonsters.size() > 0) {
			ActionResult result = new ActionResult();
			result.doThisAction = true;
			String labels = advContext.battleContext.buildMonsterLabels(card.connectMonsters);
			for(Monster m : card.connectMonsters) {
				m.changeHp(context, advContext.battleContext, hurt);
			}

			result.title = context.getString(R.string.battle_connectHurt_title);
			result.content = context.getString(R.string.battle_connectHurt_content)
					.replace("{subject}", card.getCard(context).name)
					.replace("{objects}", labels)
					.replace("{value}", hurt+"");
			result.icon1 = "skill_icon_connection";
			result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;

			advContext.battleContext.addActionResultToLog(result);
			return result;
		}else {
			return null;
		}
	}
}
