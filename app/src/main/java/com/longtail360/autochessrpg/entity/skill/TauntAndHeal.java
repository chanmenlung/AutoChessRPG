package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class TauntAndHeal extends BaseSkill{
    public static String KEY = "TauntAndHeal";
    int count;
    public TauntAndHeal(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_tauntAndHeal);
        desc = context.getString(R.string.skill_desc_tauntAndHeal);
        battleDesc = context.getString(R.string.skill_battleDesc_tauntAndHeal);
        statusDesc = context.getString(R.string.skill_statusDesc_tauntAndHeal);
    }
	@Override
	public String getDesc(Context context) {
    	int deltaHp = 10 + 20*level;
		return desc.replace("{value}", deltaHp+"");
	}
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		int deltaHp = 10 + 20*level;
		mySelf.taunt = true;
		ActionResult result = advContext.battleContext.valueUpOne(mySelf,"hp", deltaHp);
		result.doThisAction = true;
		result.title = context.getString(R.string.battle_cardActiveSkill)
				.replace("{mySelf}", mySelf.card.name)
				.replace("{skill}", name);
		result.content = battleDesc.replace("{card}", mySelf.card.name).replace("{value}", result.finalDeltaValue+"");
		result.icon1 = mySelf.card.id+"";
		result.icon1Type = RootLog.ICON1_TYPE_CARD;
		advContext.battleContext.addActionResultToLog(result);
		return result;
	}	
	
	@Override
	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
		count--;
		if(count <=0) {
			mySelf.taunt = false;
		}
		return null;
	}		
}
