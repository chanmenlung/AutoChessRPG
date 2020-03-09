package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
public class TauntAndDefenseUpAndNotDead extends BaseSkill{ //no use
	private int MAX_COUNT = 3;
    public static String KEY = "TauntAndDefenseUpAndNotDead";
	private int deltaDefense = 1;
	private int count = 0;
    public TauntAndDefenseUpAndNotDead(Context context) {
        code = KEY;
        cd = 6;
        name = context.getString(R.string.skill_name_tauntAndDefenseUpAndNotDead);
        desc = context.getString(R.string.skill_desc_tauntAndDefenseUpAndNotDead);
        battleDesc = context.getString(R.string.skill_battleDesc_tauntAndDefenseUpAndNotDead);
        statusDesc = context.getString(R.string.skill_statusDesc_tauntAndDefenseUpAndNotDead);
    }
	@Override
	public String getDesc(Context context) {
		return null;
	}
	@Override	
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{card}", mySelf.card.name);
		count = MAX_COUNT;
		mySelf.taunt = true;
		mySelf.battleDefense = mySelf.battleDefense + deltaDefense;
		return actionResult;
	}	
	
	@Override
	public ActionResult doActionOnMonsterAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		if(count > 0) {
			ActionResult result = new ActionResult();
			result.doThisAction = true;
			result.title = name;
			result.content = statusDesc.replace("{card}", mySelf.card.name);
		}
		return null;
	}	

	@Override
	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
		count--;
		if(count <=0) {
			mySelf.taunt = false;
			mySelf.battleDefense = mySelf.battleDefense - deltaDefense;
		}
		return null;
	}		
}
