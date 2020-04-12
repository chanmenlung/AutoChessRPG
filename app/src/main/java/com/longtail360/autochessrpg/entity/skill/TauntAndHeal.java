package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
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
	private int heal = 40;
    public TauntAndHeal(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_tauntAndHeal);
        desc = context.getString(R.string.skill_desc_tauntAndHeal).replace("{value}", heal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_tauntAndHeal);
    }

	@Override
	public ActionResult active(Context context,AdvContext advContext){
		mySelf.taunt = true;
		Card card = mySelf.getCard(context);
		ActionResult result = advContext.battleContext.valueUpOne(mySelf,"hp", heal);
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
	
	@Override
	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
		count--;
		if(count <=0) {
			mySelf.taunt = false;
		}
		return null;
	}		
}
