package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class TauntAndDefenseUp extends BaseSkill{
    public static String KEY = "TauntAndDefenseUp";
	private int count = 3;
	private int deltaDefense = 2;
    public TauntAndDefenseUp(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_tauntAndDefenseUp);
        desc = context.getString(R.string.skill_desc_tauntAndDefenseUp).replace("{value}", deltaDefense+"");
        battleDesc = context.getString(R.string.skill_battleDesc_tauntAndDefenseUp);
        statusDesc = context.getString(R.string.skill_statusDesc_tauntAndDefenseUp);
    }

	public ActionResult active(Context context,AdvContext advContext){
		mySelf.taunt = true;
		deltaDefense = level;
		Card card = mySelf.getCard(context);
		ActionResult result = advContext.battleContext.valueUpOne(mySelf,"defense", deltaDefense);
		result.doThisAction = true;
		result.title = context.getString(R.string.battle_cardActiveSkill)
				.replace("{mySelf}", card.name)
				.replace("{skill}", name);
		result.content = battleDesc.replace("{card}", card.name).replace("{value}", result.finalDeltaValue+"");
		result.icon1 = card.id+"";
		result.icon1Type = RootLog.ICON1_TYPE_CARD;
		advContext.battleContext.addActionResultToLog(result);
		return result;
	}	
	
//	@Override
///	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
//		count--;
//		if(count <=0) {
//			mySelf.taunt = false;
//			mySelf.battleDefense = mySelf.battleDefense - deltaDefense;
//		}
//		return null;
//	}		
}
